package GTD.restapi.util.security;

import GTD.BL.BLOsoby.PersonAdmin;
import GTD.DL.DLDAO.exceptions.PermissionDeniedException;
import GTD.DL.DLEntity.Person;
import GTD.DL.DLEntity.PersonToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Drugnanov on 14.12.2014.
 */
@Component
public class TokenUtilsImp implements TokenUtils {

    @Autowired
    private PersonAdmin personAdmin;

    @Override
    public String getToken(Person person) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        PersonToken securityToken = null;
        for (PersonToken personToken : person.getTokens()) {
            if (!personToken.getDisabled()) {
                securityToken = personToken;
                break;
            }
        }
        if (securityToken == null && person.getRightGenerateToken()) {
            securityToken = personAdmin.createPersonToken(person);
        }
        if (securityToken == null) {
            throw new PermissionDeniedException("No permission for user:" + person.toString());
        }
        return securityToken.getSecurityToken();
    }

    @Override
    public String getToken(Person person, Long expiration) throws UnsupportedEncodingException {
        throw new UnsupportedEncodingException("Token with expiration is not implemented yet.");
//    return "XXX" + person.getName() + "_" + expiration + "XXX";
    }

    @Override
    public boolean validate(String pToken) {
        boolean isValidate = false;
        Person person = getUserFromToken(pToken);
        if (person != null) {
            isValidate = true;
        }
        return isValidate;
    }

    @Override
    public Person getUserFromToken(String pToken) throws PermissionDeniedException {
//    Token token = new Token(pToken);
        Person person = personAdmin.getPersonByToken(pToken);
        return person;
    }
}

//class Token {
//
//  String full;
//  String login;
//  Long expiration = 0L;
//
//  Token(String tokenFull) {
//    //ToDo more checks
//    full = tokenFull;
//    String tokenLoginInfo = tokenFull.replace("XXX", "");
//    if (tokenLoginInfo.contains("_")) {
//      String[] contains = tokenLoginInfo.split("_");
//      login = contains[0];
//      expiration = Long.parseLong(contains[1]);
//    }
//    else
//      login = tokenLoginInfo;
//  }
//}
