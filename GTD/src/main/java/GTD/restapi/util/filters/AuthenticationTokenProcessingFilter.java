package GTD.restapi.util.filters;

import GTD.DL.DLDAO.exceptions.PermissionDeniedException;
import GTD.DL.DLEntity.Person;
import GTD.restapi.util.security.CustomAuthenticationEntryPoint;
import GTD.restapi.util.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    @Autowired
    private TokenUtils tokenUtils;

    private AuthenticationManager authManager;
    private CustomAuthenticationEntryPoint customAuthEP;

    @Autowired
    public AuthenticationTokenProcessingFilter(AuthenticationManager authManager,
            CustomAuthenticationEntryPoint customAuthEP) {
        this.authManager = authManager;
        this.customAuthEP = customAuthEP;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException, PermissionDeniedException {
//    @SuppressWarnings("unchecked")
//    Map<String, String[]> parms = request.getParameterMap();
        String token = ((HttpServletRequest) request).getHeader("token");
        if (token != null && !token.isEmpty()) {
            // validate the token
            try {
                if (tokenUtils.validate(token)) {
                    // determine the user based on the (already validated) token
                    Person person = tokenUtils.getUserFromToken(token);
                    // build an Authentication object with the user's info
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(person.getUsername(), person.getPassword());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            (HttpServletRequest) request));
                    // set the authentication into the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authManager.authenticate(authentication));
                }
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();
                customAuthEP.commence((HttpServletRequest) request, (HttpServletResponse) response, e);
            } catch (Throwable e) {
                SecurityContextHolder.clearContext();
                customAuthEP.commence((HttpServletRequest) request, (HttpServletResponse) response,
                        new BadCredentialsException("BadCredentialsException:" + e.getMessage(), e));
            }
        }
        // continue thru the filter chain
        chain.doFilter(request, response);
    }
}
