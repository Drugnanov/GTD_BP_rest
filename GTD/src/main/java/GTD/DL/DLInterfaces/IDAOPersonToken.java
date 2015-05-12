package GTD.DL.DLInterfaces;

import GTD.DL.DLEntity.PersonToken;

import java.util.List;

/**
 * Created by Drugnanov on 4.1.2015.
 */
public interface IDAOPersonToken extends IDAOGeneric<PersonToken> {

    List<PersonToken> getAll();

    PersonToken get(int id);

    List<PersonToken> getPersonForToken(String token);
}
