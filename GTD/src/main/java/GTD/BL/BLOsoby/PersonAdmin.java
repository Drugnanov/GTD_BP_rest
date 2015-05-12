package GTD.BL.BLOsoby;

import GTD.BL.BLAktivity.ProjectAdmin;
import GTD.BL.BLAktivity.exceptions.InvalidEntityException;
import GTD.BL.BLAktivity.TaskAdmin;
import GTD.BL.BLFiltry.ContextAdmin;
import GTD.DL.DLDAO.exceptions.ConstraintException;
import GTD.DL.DLDAO.exceptions.ItemNotFoundException;
import GTD.DL.DLDAO.exceptions.PermissionDeniedException;
import GTD.DL.DLDAO.util.HashConverter;
import GTD.DL.DLEntity.*;
import GTD.DL.DLInterfaces.IDAOPerson;
import GTD.DL.DLInterfaces.IDAOPersonToken;
import GTD.DL.DLInterfaces.IDAOState;
import GTD.restapi.util.exceptions.ResourceExistsException;
import GTD.restapi.util.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Třída zapouzdřuje metody pro ukládání a načítání osob z databáze.
 *
 * @author slama
 * @version 2.0
 * @created 19-10-2014 12:30:54
 */
@Component
public class PersonAdmin {

    @Autowired
    private IDAOPerson daoOsoba;
    @Autowired
    private IDAOState daoStav;
    @Autowired
    private IDAOPersonToken daoPersonToken;
    @Autowired
    private TaskAdmin taskAdmin;
    @Autowired
    private ProjectAdmin projectAdmin;
    @Autowired
    private ContextAdmin contextAdmin;
    @Autowired
    private TokenUtils tokenUtils;

    public PersonAdmin() {
    }

    public PersonAdmin(IDAOPerson daoOsoba, IDAOState daoStav, IDAOPersonToken daoPersonToken, TaskAdmin taskAdmin,
            ProjectAdmin projectAdmin, ContextAdmin contextAdmin, TokenUtils tokenUtils) {
        this.daoOsoba = daoOsoba;
        this.daoStav = daoStav;
        this.daoPersonToken = daoPersonToken;
        this.taskAdmin = taskAdmin;
        this.projectAdmin = projectAdmin;
        this.contextAdmin = contextAdmin;
        this.tokenUtils = tokenUtils;
    }

    /**
     * Uloží security token.
     *
     * @param personToken
     * @return
     */
    public void addPersonToken(PersonToken personToken) {
        if (!personToken.checkValidate()) {
            throw new InvalidEntityException("Invalid token data:" + personToken.toString());
        }
        daoPersonToken.create(personToken);
    }

    /**
     * Create and save a new security token.
     *
     * @param osoba
     * @return
     */
    public PersonToken createPersonToken(Person osoba) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        PersonToken personToken = PersonToken.createPersonToken(osoba);
        addPersonToken(personToken);
        return personToken;
    }

    /**
     * Vytvorí nového uživatele.
     *
     * @param person
     * @return
     */
    public Person addOsoba(Person person)
            throws UnsupportedEncodingException, NoSuchAlgorithmException, ResourceExistsException {
        if (!isValid(person)) {
            throw new InvalidEntityException("Invalid person data.");
        }
        if (daoOsoba.existPerson(person.getUsername())) {
            throw new ResourceExistsException("Cannot add person due to existing login:" + person.getUsername());
        }
        person.setPassword(HashConverter.md5(person.getPassword()));
        daoOsoba.create(person);
        person = daoOsoba.get(person.getId());
        tokenUtils.getToken(person);
        return daoOsoba.get(person.getId());
    }

    /**
     * Deaktivuje uživatele (na jeho účet se nepůjde přihlásit).
     *
     * @param osoba
     * @return
     */
    public void deactivateOsoba(Person osoba) {
        // if logged-in user has role ROLE_ADMIN
        PersonState deactivated = daoStav.getOsobaNeaktivni();
        osoba.setState(deactivated);
        daoOsoba.update(osoba);
    }

    /**
     * Vrátí všechny uživatele
     *
     * @param user
     * @return
     */
    public List<Person> getAllUsers(Person user) {
        if (!isAdmin(user)) {
            throw new SecurityException("You have no right to see all persons. Your login: "
                    + user.getUsername() + " isAdmin:" + isAdmin(user));
        }
        List<Person> persons = daoOsoba.getAll();
        return persons;
    }

    private boolean isValid(Person person) {
        return person.checkLengths();
    }

    private boolean isAdmin(Person person) {
        return "slamamic".equals(person.getUsername());
    }

    private boolean hasRightForPerson(Person person, Person user) {
        return (person.getUsername().equals(user.getUsername()) || isAdmin(user));
    }

    public Person getOsoba(int personId, Person user) {
        Person person = daoOsoba.get(personId);
        if (!hasRightForPerson(person, user)) {
            throw new SecurityException("You have no right to see person details. person: "
                    + person.getUsername() + " your login: " + user.getUsername() + " isAdmin:" + isAdmin(user));
        }
        return person;
    }

    public void deletePerson(Person personToDelete, Person user) {
        if (!hasRightForPerson(personToDelete, user)) {
            throw new SecurityException("You have no right to delete this person: "
                    + personToDelete.getUsername() + " your login: "
                    + user.getUsername() + " isAdmin:" + isAdmin(user));
        }
        daoOsoba.delete(personToDelete);
    }

    /**
     * Vrátí uživatele.
     *
     * @param id
     * @return
     */
    public Person getOsoba(int id) {
        return daoOsoba.get(id);
    }

    public Person getOsoba(String login) {
        return daoOsoba.getOsoba(login);
//    PersonState state = daoStav.getOsobaAktivni();
//    return new Person("Michal", "Slama", "slamamic", state);
    }

    /**
     * Vrátí prihlaseneho uživatele.
     *
     * @return
     */
    public Person getOsobaPrihlasena() {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO steklsim bude getOsobaPrihlasena() k necemu?
    }

    /**
     * Zkontroluje, jestli už neexistuje osoba s daným uživ. jménem.
     *
     * @param login login
     */
    private boolean checkNewLogin(String login) {
        return (daoOsoba.getOsoba(login) == null);
    }

    /**
     * Zkontroluje přihlašovací údaje.
     *
     * @param login
     * @param heslo heslo
     */
    private boolean checkPrihlaseni(String login, String heslo) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO steklsim bude autentizace v PersonAdminu? (podle me by nemela)
    }

    /**
     * Zkusí přihlásit uživatele pomocí zadaných přihlašovacích údajů.
     *
     * @param login
     * @param password
     * @return
     */
    public boolean loginOsoba(String login, String password)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Person person = daoOsoba.getOsobaByLoginPass(login, password);
        return person != null;
    }

    public boolean loginOsobaWithHash(String login, String passwordHash) {
        Person person = daoOsoba.getOsobaByLoginPassHash(login, passwordHash);
        return person != null;
    }

    /**
     * Odhlásí aktuálního uživatele.
     */
    public void logout() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Person getPersonByToken(String token) throws PermissionDeniedException {
        List<Person> persons = new ArrayList<Person>();
        List<PersonToken> personTokens = daoPersonToken.getPersonForToken(token);
        for (PersonToken personToken : personTokens) {
            if (personToken.getDisabled()) {
                continue;
            }
            persons.add(personToken.getPerson());
        }
        if (persons.size() < 1) {
            throw new PermissionDeniedException("No person for the token:" + token);
        }
        if (persons.size() > 1) {
            StringBuilder personsString = new StringBuilder();
            for (Person person : persons) {
                personsString.append(person.toString());
                personsString.append(System.getProperty("line.separator"));
            }
            throw new PermissionDeniedException(
                    "More than 1 person for the token: " + token + "; persons:" + personsString);
        }
        return persons.get(0);
    }

    /**
     * Uloží zmeny profilu uživatele.
     *
     *
     * @param person
     * @param user
     * @return
     */
    public void updateOsoba(Person person, Person user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (!isValid(person)) {
            throw new InvalidEntityException("Invalid person data");
        }
        if (!hasRightForPerson(person, user)) {
            throw new SecurityException("You have no right to setUpdateAttr this person: "
                    + person.getUsername() + " your login: " + user.getUsername() + " isAdmin:" + isAdmin(user));
        }
        Person personNew = daoOsoba.get(person.getId());
        populatePerson(personNew, person);
        daoOsoba.update(personNew);
    }

    /**
     * Replaces dummy properties from API with their real counterparts from database (where it's needed)
     *
     * @param personNew actual person from DB
     * @param person new person from user
     */
    private void populatePerson(Person personNew, Person person)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (person.getState() != null) {
            try {
                PersonState state = daoStav.getPersonState(person.getState().getCode());
                personNew.setState(state);
            } catch (ItemNotFoundException infe) {
                throw new ConstraintException("PersonState '" + person.getState().getCode() + "' not found", infe);
            }
        }
        if (person.getUsername() != null) {
            personNew.setUsername(person.getUsername());
        }
        if (person.getName() != null) {
            personNew.setName(person.getName());
        }
        if (person.getSurname() != null) {
            personNew.setSurname(person.getSurname());
        }
        if (person.hasNewPassword()) {
            personNew.setPassword(HashConverter.md5(person.getPasswordRaw()));
        }
    }

    public void setDaoOsoba(IDAOPerson daoOsoba) {
        this.daoOsoba = daoOsoba;
    }

    public void setDaoStav(IDAOState daoStav) {
        this.daoStav = daoStav;
    }

    public void setDaoPersonToken(IDAOPersonToken daoPersonToken) {
        this.daoPersonToken = daoPersonToken;
    }

    public void setTaskAdmin(TaskAdmin taskAdmin) {
        this.taskAdmin = taskAdmin;
    }

    public void setProjectAdmin(ProjectAdmin projectAdmin) {
        this.projectAdmin = projectAdmin;
    }

    public void setContextAdmin(ContextAdmin contextAdmin) {
        this.contextAdmin = contextAdmin;
    }

    public void setTokenUtils(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    public Collection<GrantedAuthority> getGrantsByUser(String name) {
        //ToDo grants
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return authList;
    }
}
