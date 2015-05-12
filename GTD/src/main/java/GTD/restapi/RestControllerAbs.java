package GTD.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by Drugnanov on 18.1.2015.
 */
public abstract class RestControllerAbs {

    @Autowired
    private MessageSource messageSource;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void logRequest(ServletWebRequest wr) {
        logger.debug(getMessageSource().getMessage("restApi.request.accepted", null, null), wr.getHttpMethod(),
                wr.getDescription(true));
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }
}
