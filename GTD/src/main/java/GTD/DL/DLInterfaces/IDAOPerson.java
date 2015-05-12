package GTD.DL.DLInterfaces;

import GTD.DL.DLEntity.Person;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Interface pro správu Osob v databázi.
 *
 * @author slama
 * @version 2.0
 */
public interface IDAOPerson extends IDAOGeneric<Person> {

    List<Person> getAll();

    Person get(int id);

    /**
     * Vrátí uživatele na základě uživatelského jména
     *
     * @param username
     * @return
     */
    Person getOsoba(String username);

    /**
     * Informace jestli je login uz v systemu registrovan
     *
     * @param login
     * @return
     */
    boolean existPerson(String login);

    /**
     * Vrátí uživatele na základě uživatelského jména a hesla
     *
     * @param login
     * @param password
     * @return
     */
    Person getOsobaByLoginPass(String login, String password) throws UnsupportedEncodingException,
            NoSuchAlgorithmException;

    Person getOsobaByLoginPassHash(String login, String passwordHash);

}
