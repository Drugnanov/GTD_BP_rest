package GTD.BL.BLInterfaces;

import GTD.DL.DLEntity.Person;
import java.util.List;

/**
 * Interface defines the way BL and PL communicates concerning persons
 *
 * @author GTD team
 * @version 2.0
 * @created 19-10-2014 12:30:53
 */
public interface IPersonController {

    /**
     * Vytvoří nového uživatele.
     *
     * @return boolean
     *
     * @param person
     */
    boolean addPerson(Person person);

    /**
     * Deaktivuje uživatele (na jeho účet se nepůjde přihlásit).
     *
     * @return boolean
     *
     * @param person
     */
    boolean deactivatePerson(Person person);

    /**
     * Vrátí všechny uživatele
     *
     * @return boolean
     */
    List getAllUsers();

    /**
     * Vrátí přihlášeného uživatele.
     *
     * @return
     */
    Person getLoggedPerson();

    /**
     * Vrátí uživatele podle ID.
     *
     * @return
     *
     * @param id
     */
    Person getPerson(int id);

    /**
     * Zkusí přihlásit uživatele s danými přihlašovacími údaji.
     *
     * @return boolean
     *
     * @param username
     * @param password
     */
    boolean loginPerson(String username, String password);

    /**
     * Odhlásí aktuálního uživatele.
     */
    void logout();

    /**
     * Odešle GUI pokyn k obnovení.
     */
    void refresh();

}
