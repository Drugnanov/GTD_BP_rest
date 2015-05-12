package GTD.BL.BLOsoby;

import GTD.BL.BLAktivity.ProjectAdmin;
import GTD.BL.BLAktivity.TaskAdmin;
import GTD.BL.BLAktivity.exceptions.InvalidEntityException;
import GTD.BL.BLFiltry.ContextAdmin;
import GTD.DL.DLDAO.exceptions.PermissionDeniedException;
import GTD.DL.DLEntity.Person;
import GTD.DL.DLEntity.PersonToken;
import GTD.DL.DLInterfaces.IDAOPerson;
import GTD.DL.DLInterfaces.IDAOPersonToken;
import GTD.DL.DLInterfaces.IDAOState;
import GTD.restapi.util.exceptions.ResourceExistsException;
import GTD.restapi.util.security.TokenUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author skvarla
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonAdminTest {

    @Mock
    private static IDAOPerson daoOsoba;
    @Mock
    private static IDAOState daoStav;
    @Mock
    private static IDAOPersonToken daoPersonToken;
    @Mock
    private static TaskAdmin taskAdmin;
    @Mock
    private static ProjectAdmin projectAdmin;
    @Mock
    private static ContextAdmin contextAdmin;
    @Mock
    private static TokenUtils tokenUtils;

    @InjectMocks
    private static PersonAdmin instance;

    public PersonAdminTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        daoOsoba = Mockito.mock(IDAOPerson.class);
        daoStav = Mockito.mock(IDAOState.class);
        tokenUtils = Mockito.mock(TokenUtils.class);
        daoPersonToken = Mockito.mock(IDAOPersonToken.class);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addPersonToken method, of class PersonAdmin.
     */
    @Test
    public void testAddPersonTokenValidToken() {
        System.out.println("addPersonToken");
        PersonToken personToken = Mockito.mock(PersonToken.class);

        when(personToken.checkValidate()).thenReturn(true);

        instance.addPersonToken(personToken);

        Mockito.verify(daoPersonToken, Mockito.times(1)).create(personToken);
    }

    /**
     * Test of addPersonToken method, of class PersonAdmin.
     */
    @Test(expected = InvalidEntityException.class)
    public void testAddPersonTokenInvalidToken() {
        System.out.println("addPersonToken");
        PersonToken personToken = Mockito.mock(PersonToken.class);
        when(personToken.checkValidate()).thenReturn(false);
        instance.addPersonToken(personToken);
    }

    /**
     * Test of addOsoba method, of class PersonAdmin.
     */
    @Test(expected = InvalidEntityException.class)
    public void testAddOsobaInvalidPerson() throws Exception {
        System.out.println("addOsoba");
        Person person = Mockito.mock(Person.class);;
        when(person.checkLengths()).thenReturn(false);
        instance.addOsoba(person);
    }

    /**
     * Test of addOsoba method, of class PersonAdmin.
     */
    @Test(expected = ResourceExistsException.class)
    public void testAddOsobaAlreadExisting() throws Exception {
        System.out.println("addOsoba");
        Person person = Mockito.mock(Person.class);
        when(person.checkLengths()).thenReturn(true);
        when(person.getUsername()).thenReturn("jano");
        when(daoOsoba.existPerson("jano")).thenReturn(true);
        instance.addOsoba(person);
    }

    /**
     * Test of getOsoba method, of class PersonAdmin.
     */
    @Test(expected = SecurityException.class)
    public void testGetOsoba_int_PersonDoesNotHaveRights() {
        System.out.println("getOsoba");
        int personId = 10;
        Person person = Mockito.mock(Person.class);
        Person user = Mockito.mock(Person.class);

        when(user.getUsername()).thenReturn("Jano");
        when(person.getUsername()).thenReturn("Fero");
        when(daoOsoba.get(personId)).thenReturn(person);

        instance.getOsoba(personId, user);
    }

    /**
     * Test of deletePerson method, of class PersonAdmin.
     */
    @Test(expected = SecurityException.class)
    public void testDeletePerson() {
        System.out.println("deletePerson");
        Person personToDelete = Mockito.mock(Person.class);
        Person user = Mockito.mock(Person.class);

        when(user.getUsername()).thenReturn("Jano");
        when(personToDelete.getUsername()).thenReturn("Fero");

        instance.deletePerson(personToDelete, user);
    }

    /**
     * Test of loginOsoba method, of class PersonAdmin.
     */
    @Test
    public void testLoginOsobaDoesNotExist() throws Exception {
        System.out.println("loginOsoba");
        Person person = null;
        String login = "Jano";
        String password = "abcd";

        when(daoOsoba.getOsobaByLoginPass(login, password)).thenReturn(person);

        assertFalse(instance.loginOsoba(login, password));
    }

     /**
     * Test of loginOsoba method, of class PersonAdmin.
     */
    @Test
    public void testLoginOsobaDoesExist() throws Exception {
        System.out.println("loginOsoba");
        Person person = Mockito.mock(Person.class);
        String login = "Jano";
        String password = "abcd";

        when(daoOsoba.getOsobaByLoginPass(login, password)).thenReturn(person);

        assertTrue(instance.loginOsoba(login, password));
    }

    /**
     * Test of loginOsobaWithHash method, of class PersonAdmin.
     */
    @Test
    public void testLoginOsobaWithHashDoesNotExist() {
        System.out.println("loginOsobaWithHash");
        Person person = null;
        String login = "Jano";
        String passwordHash = "abcd";

        when(daoOsoba.getOsobaByLoginPassHash(login, passwordHash)).thenReturn(person);

        assertFalse(instance.loginOsobaWithHash(login, passwordHash));
    }

        /**
     * Test of loginOsobaWithHash method, of class PersonAdmin.
     */
    @Test
    public void testLoginOsobaWithHashDoesExist() {
        System.out.println("loginOsobaWithHash");
       Person person = Mockito.mock(Person.class);
        String login = "Jano";
        String passwordHash = "abcd";

        when(daoOsoba.getOsobaByLoginPassHash(login, passwordHash)).thenReturn(person);

        assertTrue(instance.loginOsobaWithHash(login, passwordHash));
    }

    /**
     * Test of getPersonByToken method, of class PersonAdmin.
     */
    @Test (expected = PermissionDeniedException.class)
    public void testGetPersonByTokenZeroTokens() {
        System.out.println("getPersonByToken");
        String token = "abc";
        List<PersonToken> personTokens = new ArrayList<PersonToken>();

        when(daoPersonToken.getPersonForToken(token)).thenReturn(personTokens);

        instance.getPersonByToken(token);

    }

    /**
     * Test of updateOsoba method, of class PersonAdmin.
     */
    @Test
    public void testUpdateOsobaOK() throws Exception {
        System.out.println("updateOsoba");
        Person person = Mockito.mock(Person.class);
        Person user = Mockito.mock(Person.class);
        Person newperson = new Person();
        

        when(person.getUsername()).thenReturn("Jano");
        when(user.getUsername()).thenReturn("Jano");
        when(person.checkLengths()).thenReturn(true);
        when(daoOsoba.get(person.getId())).thenReturn(newperson);

        instance.updateOsoba(person, user);

        Mockito.verify(daoOsoba).update(newperson);
        Mockito.verify(daoOsoba, Mockito.times(1)).update(newperson);

    }

}
