/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.restapi;

import GTD.BL.BLAktivity.exceptions.InvalidEntityException;
import GTD.DL.DLDAO.exceptions.*;
import GTD.restapi.util.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

/**
 * @author slama
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final int LEVEL_TRACE = 1;
    private static final int LEVEL_DEBUG = 2;
    private static final int LEVEL_INFO = 3;
    private static final int LEVEL_WARNING = 4;
    private static final int LEVEL_ERROR = 5;

    protected Logger logger = LoggerFactory.getLogger(TaskRestController.class);
    private MessageSource messageSource;

    public MessageSource getMessageSource() {
        return messageSource;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleBadCredentialsException(BadCredentialsException ce, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.UNAUTHORIZED, ce, wr);
    }

    @ExceptionHandler(MaintenenceException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleMaintenenceException(MaintenenceException ce, WebRequest wr) {
        logException(LEVEL_WARNING, HttpStatus.SERVICE_UNAVAILABLE, ce, wr);
    }

    @ExceptionHandler(GtdApiException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleGtdApiException(GtdApiException ce, WebRequest wr) {
        logException(LEVEL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, ce, wr);
    }

    @ExceptionHandler(GtdFacebookTaskAlreadyReportedException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public void handleGtdFacebookException(GtdFacebookTaskAlreadyReportedException ce, WebRequest wr) {
        logException(LEVEL_WARNING, HttpStatus.ALREADY_REPORTED, ce, wr);
    }

    @ExceptionHandler(GtdGoogleTaskAlreadyReportedException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public void handleGtdGoogleTaskAlreadyReportedException(GtdGoogleTaskAlreadyReportedException ce, WebRequest wr) {
        logException(LEVEL_WARNING, HttpStatus.ALREADY_REPORTED, ce, wr);
    }

    @ExceptionHandler(GtdPublishInvalidTokenException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public void handleGtdPublishInvalidTokenException(GtdPublishInvalidTokenException ce, WebRequest wr) {
        logException(LEVEL_WARNING, HttpStatus.PRECONDITION_FAILED, ce, wr);
    }

    @ExceptionHandler(BadLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleBadLoginException(BadLoginException ce, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.UNAUTHORIZED, ce, wr);
    }

    @ExceptionHandler(BadPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleBadPasswordException(BadPasswordException ce, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.UNAUTHORIZED, ce, wr);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handlePermissionDeniedException(PermissionDeniedException ce, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.UNAUTHORIZED, ce, wr);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleAuthenticationException(AuthenticationException ce, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.UNAUTHORIZED, ce, wr);
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleIOException(IOException ce, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.BAD_REQUEST, ce, wr);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleIllegalArgumentException(IOException ce, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.BAD_REQUEST, ce, wr);
    }


    @ExceptionHandler(ConstraintException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleConstraintException(ConstraintException ce, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.BAD_REQUEST, ce, wr);
    }

    @ExceptionHandler(InvalidEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleInvalidEntityException(InvalidEntityException iee, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.BAD_REQUEST, iee, wr);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleItemNotFoundException(ItemNotFoundException infe, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.NOT_FOUND, infe, wr);
    }

    @ExceptionHandler(DAOServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleDAOServerException(DAOServerException dse, WebRequest wr) {
        logException(LEVEL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, dse, wr);
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleSecurityException(SecurityException se, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.FORBIDDEN, se, wr);
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleJsonProcessingException(JsonProcessingException jpe, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.BAD_REQUEST, jpe, wr);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleJsonProcessingException(NullPointerException npe, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.BAD_REQUEST, npe, wr);
    }

    @ExceptionHandler(ResourceExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleResourceExistsException(ResourceExistsException npe, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.BAD_REQUEST, npe, wr);
    }

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleObjectAlreadyExistsException(ObjectAlreadyExistsException npe, WebRequest wr) {
        logException(LEVEL_INFO, HttpStatus.CONFLICT, npe, wr);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleThrowable(Throwable ce, WebRequest wr) {
        logException(LEVEL_WARNING, HttpStatus.INTERNAL_SERVER_ERROR, ce, wr);
    }

    /**
     * Logs exception and request description
     *
     * @param level
     * @param e
     * @param wr
     */
    private void logException(int level, HttpStatus status, Throwable e, WebRequest wr) {
        String message = getMessageSource().getMessage("restApi.exceptions.general", null, null);
        Integer statusValue = status.value();
        String statusReasonPhrase = status.getReasonPhrase();
        String exceptionClass = e.getClass().getSimpleName();
        String exceptionMessage = e.getMessage();
        String exceptionDescription = wr.getDescription(true);
        switch (level) {
            case LEVEL_TRACE:
                logger.trace(message, exceptionClass, exceptionMessage, exceptionDescription, statusValue,
                        statusReasonPhrase);
                break;
            case LEVEL_DEBUG:
                logger.debug(message, exceptionClass, exceptionMessage, exceptionDescription, statusValue,
                        statusReasonPhrase);
                break;
            case LEVEL_INFO:
                logger.info(message, exceptionClass, exceptionMessage, exceptionDescription, statusValue,
                        statusReasonPhrase);
                break;
            case LEVEL_WARNING:
                logger.warn(message, exceptionClass, exceptionMessage, exceptionDescription, statusValue,
                        statusReasonPhrase);
                break;
            case LEVEL_ERROR:
                logger.error(message, exceptionClass, exceptionMessage, exceptionDescription, statusValue,
                        statusReasonPhrase);
                break;
            default:
                logger.error("Unknown loggging level: " + level);
        }
    }
}
