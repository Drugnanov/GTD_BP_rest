package GTD.restapi;

import GTD.BL.BLOsoby.PersonAdmin;
import GTD.DL.DLEntity.Person;
import GTD.restapi.model.PersonAuth;
import GTD.restapi.util.exceptions.BadLoginException;
import GTD.restapi.util.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Drugnanov on 14.12.2014.
 */
@RestController
@RequestMapping("/api/v1/authenticate")
public class AutentificationController extends RestControllerAbs {

//  protected Logger logger = LoggerFactory.getLogger(TaskRestController.class);
//
//  public MessageSource getMessageSource() {
//    return messageSource;
//  }
//
//  private MessageSource messageSource;
    public void setTokenUtils(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private PersonAdmin personAdmin;

//  @Autowired
//  public void setMessageSource(MessageSource messageSource) {
//    this.messageSource = messageSource;
//  }
    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PersonAuth get(@PathVariable String userName, ServletWebRequest wr)
            throws IOException, BadLoginException, NoSuchAlgorithmException {
        logRequest(wr);
        String password = "";
        password = wr.getHeader("password");
        if (password == null) {
            throw new IOException("No password included!");
        }
        if (!personAdmin.loginOsoba(userName, password)) {
            throw new BadLoginException("Invalid login data username: " + userName + " and password:" + password);
        }
        Person person = personAdmin.getOsoba(userName);
        String token = tokenUtils.getToken(person);
        PersonAuth personAuth = new PersonAuth();
        personAuth.setUserName(person.getUsername());
        personAuth.setToken(token);
        return personAuth;
    }

//  private void logRequest(ServletWebRequest wr) {
//    logger.debug(getMessageSource().getMessage("restApi.request.accepted", null, null), wr.getHttpMethod(),
//        wr.getDescription(true));
//  }
}
