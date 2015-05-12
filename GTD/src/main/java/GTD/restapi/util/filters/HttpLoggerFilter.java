package GTD.restapi.util.filters;

import java.io.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;

import org.apache.commons.io.output.TeeOutputStream;

/**
 * Created by Drugnanov on 11.12.2014.
 */
public class HttpLoggerFilter implements Filter {

    private static final int BUFFER_SIZE = 1024;

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpLoggerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            Map<String, String> requestMap = this.getTypesafeRequestMap(httpServletRequest);
            BufferedRequestWrapper bufferedReqest = new BufferedRequestWrapper(httpServletRequest);
            BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(httpServletResponse);

            final StringBuilder logMessage = new StringBuilder("REST Request - ")
                    .append("[HTTP METHOD:")
                    .append(notNull(httpServletRequest.getMethod()))
                    .append("] [PATH INFO:")
                    .append(notNull(httpServletRequest.getPathInfo()))
                    .append("] [REQUEST PARAMETERS:")
                    .append(notNull(requestMap))
                    .append("] [REQUEST BODY:")
                    .append(notNull(bufferedReqest.getRequestBody()))
                    .append("] [REMOTE ADDRESS:")
                    .append(notNull(httpServletRequest.getRemoteAddr()))
                    .append("]");

            chain.doFilter(bufferedReqest, bufferedResponse);
            String responseString = "" + ((HttpServletResponse) response).getStatus() + " ";
            if (!bufferedResponse.isNull()) {
                try {
                    String body = bufferedResponse.getContent();
                    responseString += body;
                } catch (Throwable e) {
                    responseString += "Error";
                }
            }
            logMessage.append(" [RESPONSE:").append(responseString).append("]");
            LOGGER.debug(logMessage.toString());
        } catch (Throwable a) {
            LOGGER.error(a.getMessage());
        }
    }

    private Object notNull(Object object) {
        if (object == null) {
            return "";
        } else {
            return object;
        }
    }

    private Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {
        Map<String, String> typesafeRequestMap = new HashMap<String, String>();
        Enumeration<?> requestParamNames = request.getParameterNames();
        while (requestParamNames.hasMoreElements()) {
            String requestParamName = (String) requestParamNames.nextElement();
            String requestParamValue = request.getParameter(requestParamName);
            typesafeRequestMap.put(requestParamName, requestParamValue);
        }
        return typesafeRequestMap;
    }

    @Override
    public void destroy() {
    }

    private static final class BufferedRequestWrapper extends HttpServletRequestWrapper {

        private ByteArrayInputStream bais = null;
        private ByteArrayOutputStream baos = null;
        private BufferedServletInputStream bsis = null;
        private byte[] buffer = null;

        public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
            super(req);
            // Read InputStream and store its content in a buffer.
            InputStream is = req.getInputStream();
            this.baos = new ByteArrayOutputStream();
            byte[] buf = new byte[BUFFER_SIZE];
            int letti;
            while ((letti = is.read(buf)) > 0) {
                this.baos.write(buf, 0, letti);
            }
            this.buffer = this.baos.toByteArray();
        }

        @Override
        public ServletInputStream getInputStream() {
            this.bais = new ByteArrayInputStream(this.buffer);
            this.bsis = new BufferedServletInputStream(this.bais);
            return this.bsis;
        }

        String getRequestBody() throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getInputStream(), "UTF-8"));
            String line = null;
            StringBuilder inputBuffer = new StringBuilder();
            do {
                line = reader.readLine();
                if (null != line) {
                    inputBuffer.append(line.trim());
                }
            } while (line != null);
            reader.close();
            return inputBuffer.toString().trim();
        }
    }

    private static final class BufferedServletInputStream extends ServletInputStream {

        private ByteArrayInputStream bais;

        public BufferedServletInputStream(ByteArrayInputStream bais) {
            this.bais = bais;
        }

        @Override
        public int available() {
            return this.bais.available();
        }

        @Override
        public int read() {
            return this.bais.read();
        }

        @Override
        public int read(byte[] buf, int off, int len) {
            return this.bais.read(buf, off, len);
        }
    }

    public class TeeServletOutputStream extends ServletOutputStream {

        private final TeeOutputStream targetStream;

        public TeeServletOutputStream(OutputStream one, OutputStream two) {
            targetStream = new TeeOutputStream(one, two);
        }

        @Override
        public void write(int arg0) throws IOException {
            this.targetStream.write(arg0);
        }

        public void flush() throws IOException {
            super.flush();
            this.targetStream.flush();
        }

        public void close() throws IOException {
            super.close();
            this.targetStream.close();
        }
    }

    public class BufferedResponseWrapper extends HttpServletResponseWrapper {

        private HttpServletResponse original;

        private TeeServletOutputStream teeStream;

        private PrintWriter teeWriter;

        private ByteArrayOutputStream bos;

        public BufferedResponseWrapper(HttpServletResponse response) {
            super(response);
            original = response;
        }

        public boolean isNull() {
            return original == null;
        }

        public String getContent() throws IOException {
            return bos.toString("UTF-8");
        }

        @Override
        public PrintWriter getWriter() throws IOException {

            if (this.teeWriter == null) {
                this.teeWriter = new PrintWriter(new OutputStreamWriter(getOutputStream(), "UTF-8"));
            }
            return this.teeWriter;
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {

            if (teeStream == null) {
                bos = new ByteArrayOutputStream();
                teeStream = new TeeServletOutputStream(original.getOutputStream(), bos);
            }
            return teeStream;
        }

        @Override
        public void flushBuffer() throws IOException {
            if (teeStream != null) {
                teeStream.flush();
                System.err.println("teeStream flush");
            }
            if (this.teeWriter != null) {
                this.teeWriter.flush();
                System.err.println("teeWriter flush");
            }
        }
    }

//  public class BufferedResponseWrapper implements HttpServletResponse {
//
//    HttpServletResponse original;
//    TeeServletOutputStream tee;
//    ByteArrayOutputStream bos;
//
//    public BufferedResponseWrapper(HttpServletResponse response) {
//      original = response;
//    }
//
//    public String getContent() {
//      return bos.toString();
//    }
//
//    public PrintWriter getWriter() throws IOException {
//      return original.getWriter();
//    }
//
//    public ServletOutputStream getOutputStream() throws IOException {
//      if (tee == null) {
//        bos = new ByteArrayOutputStream();
//        tee = new TeeServletOutputStream(original.getOutputStream(), bos);
//      }
//      return tee;
//    }
//
//
//  }
}
