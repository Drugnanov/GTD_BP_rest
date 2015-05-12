package GTD.restapi.util.security;

import GTD.BL.BLOsoby.PersonAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Created by Drugnanov on 14.12.2014.
 */
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private PersonAdmin personAdmin;

    @Override
    public Authentication authenticate(Authentication a) {

        try {
            if (!personAdmin.loginOsobaWithHash(a.getName(), a.getCredentials().toString())) {
                throw new BadCredentialsException("Bad credentials");
            }
        } catch (Throwable e) {
            throw new BadCredentialsException(
                    "Something is wrong: name " + a.getName() + "; pass:" + a.getCredentials().toString(), e);
        }

        return new UsernamePasswordAuthenticationToken(a.getPrincipal(), a.getCredentials(),
                personAdmin.getGrantsByUser(a.getName()));
    }
}
