package GTD.restapi.util.security;

import GTD.DL.DLEntity.Person;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface TokenUtils {

    String getToken(Person person) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    String getToken(Person person, Long expiration) throws UnsupportedEncodingException;

    boolean validate(String token);

    Person getUserFromToken(String token);
}
