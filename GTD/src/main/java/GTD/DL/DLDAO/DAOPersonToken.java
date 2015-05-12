package GTD.DL.DLDAO;

import GTD.DL.DLEntity.PersonToken;
import GTD.DL.DLInterfaces.IDAOPersonToken;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by Drugnanov on 4.1.2015.
 */
@Service
public class DAOPersonToken extends DAOGeneric<PersonToken> implements IDAOPersonToken {

    /**
     * Konstruktor úkolu
     */
    public DAOPersonToken() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PersonToken> getAll() {
        return (List<PersonToken>) this.getAll(PersonToken.class);
    }

    @Override
    public PersonToken get(int id) {
        return (PersonToken) this.get(PersonToken.class, id);
    }

    /**
     * Returns PersonTokens with requester token
     *
     * @return List<PersonToken>
     *
     * @param token
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<PersonToken> getPersonForToken(String token) {
        Session session = null;
        Transaction tx = null;
        List<PersonToken> personTokens = null;
        try {
            session = this.openSession();
            tx = session.beginTransaction();
      //toDo all by
            // //PersonToken.class.getAnnotation(Table.class).toString()
            Query query = session.createQuery(
                    "from " + PersonToken.class.getName() + " a "
                    + "where a.securityToken = :token"
            );
            query.setParameter("token", token);
            personTokens = (List<PersonToken>) query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return personTokens;
    }

}
