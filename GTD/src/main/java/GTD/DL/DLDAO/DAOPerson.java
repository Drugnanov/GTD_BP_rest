package GTD.DL.DLDAO;

import GTD.DL.DLDAO.exceptions.BadPasswordException;
import GTD.DL.DLDAO.exceptions.ItemNotFoundException;
import GTD.DL.DLDAO.util.HashConverter;
import GTD.DL.DLEntity.Person;
import GTD.DL.DLInterfaces.IDAOPerson;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

/**
 * Trída zapouzdruje metody pro ukládání a nacítání osob z databáze.
 *
 * @author slama
 * @version 2.0
 */
@Component
public class DAOPerson extends DAOGeneric<Person> implements IDAOPerson {

    /**
     * Konstruktor osoby
     */
    public DAOPerson() {

    }

    /**
     * Vráti osoby dle zadaného loginu
     *
     * @param login
     * @return osoba
     */
    @Override
    @SuppressWarnings("unchecked")
    public Person getOsoba(String login) {
        Session session = null;
        Transaction tx = null;
        List<Person> persons = null;
        try {
            session = this.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(
                    "from " + Person.class.getName() + " p "
                    + "where p.username = :login"
            );
            query.setParameter("login", login);
            persons = (List<Person>) query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        if (persons == null || persons.isEmpty()) {
            throw new ItemNotFoundException("User '" + login
                    + "' not found");
        }
        return persons.get(0);
    }

    @Override
    public boolean existPerson(String login) {
        try {
            Person person = getOsoba(login);
            return person != null;
        } catch (ItemNotFoundException ex) {
            return false;
        }
    }

    @Override
    public Person getOsobaByLoginPass(String login, String password) throws UnsupportedEncodingException,
            NoSuchAlgorithmException {
        Person person = getOsoba(login);
        if (!HashConverter.md5(password).equals(person.getPassword())) {
            throw new BadPasswordException("User '" + login + "' with wrong password: " + password);
        }
        return person;
    }

    @Override
    public Person getOsobaByLoginPassHash(String login, String passwordHash) {
        Person person = getOsoba(login);
        if (!passwordHash.equals(person.getPassword())) {
            throw new BadPasswordException("User '" + login + "' with wrong password: " + passwordHash);
        }
        return person;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Person> getAll() {
        return (List<Person>) this.getAll(Person.class);
    }

    @Override
    public Person get(int id) {
        return (Person) this.get(Person.class, id);
    }
}
