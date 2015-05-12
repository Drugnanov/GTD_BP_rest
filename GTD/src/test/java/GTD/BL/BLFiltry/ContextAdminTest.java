package GTD.BL.BLFiltry;

import GTD.DL.DLEntity.Context;
import GTD.DL.DLEntity.Person;
import GTD.DL.DLInterfaces.IDAOContext;
import GTD.DL.DLInterfaces.IDAOTask;
import java.util.List;

import GTD.restapi.util.exceptions.ObjectAlreadyExistsException;
import org.easymock.Mock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author skvarla
 */
@RunWith(MockitoJUnitRunner.class)
public class ContextAdminTest {

    @Mock
    private static IDAOContext DAOKontext;
    
    @Mock
    private static IDAOTask DAOTask;

    @InjectMocks
    private static ContextAdmin instance;

    public ContextAdminTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        DAOKontext = Mockito.mock(IDAOContext.class);
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
     * Test of addContext method, of class ContextAdmin.
     */
    @Test
    public void testAddKontextValidTitle() throws ObjectAlreadyExistsException {
        System.out.println("addContext");
        Context kontext = Mockito.mock(Context.class);
        Person user = Mockito.mock(Person.class);

        when(kontext.checkLengths()).thenReturn(true);

        instance.addContext(kontext, user);

    }

    @Test(expected = Exception.class)
    public void testAddKontextInvalidTitle() throws ObjectAlreadyExistsException {
        System.out.println("addContext");
        Context kontext = Mockito.mock(Context.class);
        Person user = Mockito.mock(Person.class);

        when(kontext.checkLengths()).thenReturn(false);

        instance.addContext(kontext, user);
    }

    /**
     * Test of deleteKontext method, of class ContextAdmin.
     */
    @Test(expected = Exception.class)
    public void testDeleteKontextWrongUserID() {
        System.out.println("deleteKontext");
        Context kontext = Mockito.mock(Context.class, RETURNS_DEEP_STUBS);
        Person user = Mockito.mock(Person.class);

        when(user.getId()).thenReturn(1);
        when(kontext.getOwner().getId()).thenReturn(2);

        instance.deleteKontext(kontext, user);
    }

    /**
     * Test of getKontext method, of class ContextAdmin.
     */
    @Test(expected = Exception.class)
    public void testGetKontext_int_PersonWrongID() {
        System.out.println("getKontext");
        int id = 0;
        Person user = Mockito.mock(Person.class);
        Context context = Mockito.mock(Context.class, RETURNS_DEEP_STUBS);

        when(user.getId()).thenReturn(1);
        when(context.getOwner().getId()).thenReturn(0);
        when(DAOKontext.get(id)).thenReturn(context);

        Context result = instance.getKontext(id, user);
        assertEquals(0, result.getId());

    }

    /**
     * Test of getKontext method, of class ContextAdmin.
     */
    @Test
    public void testGetKontext_int_PersonOK() {
        System.out.println("getKontext");
        int id = 0;
        Person user = Mockito.mock(Person.class);
        Context context = Mockito.mock(Context.class, RETURNS_DEEP_STUBS);

        when(user.getId()).thenReturn(0);
        when(context.getOwner().getId()).thenReturn(0);
        when(DAOKontext.get(id)).thenReturn(context);

        Context result = instance.getKontext(id, user);
        assertEquals(0, result.getId());

    }

    /**
     * Test of getKontext method, of class ContextAdmin.
     */
    @Test(expected = Exception.class)
    public void testGetKontext_String_PersonContextNotFound() {
        System.out.println("getKontext");
        String title = "contextTitle";
        List<Context> contexts = Mockito.mock(List.class);
        Context context = Mockito.mock(Context.class);
        Person user = Mockito.mock(Person.class);
        contexts.add(context);

        when(DAOKontext.getKontextyOsoby(user)).thenReturn(contexts);
        when(context.getTitle().equals(title)).thenReturn(false);

        instance.getKontext(title, user);
    }

    /**
     * Test of updateKontext method, of class ContextAdmin.
     */
    @Test
    public void testUpdateKontextValidTitle() {
        System.out.println("updateKontext");
        Context context = Mockito.mock(Context.class);
        Context oldContext = Mockito.mock(Context.class, RETURNS_DEEP_STUBS);
        Person user = Mockito.mock(Person.class);

        when(oldContext.getOwner().getId()).thenReturn(1);
        when(user.getId()).thenReturn(1);
        when(context.checkLengths()).thenReturn(true);
        when(DAOKontext.get(context.getId())).thenReturn(oldContext);

        instance.updateKontext(context, user);
    }

    /**
     * Test of updateKontext method, of class ContextAdmin.
     */
    @Test(expected = Exception.class)
    public void testUpdateKontextInvalidTitle() {
        System.out.println("updateKontext");
        Context context = Mockito.mock(Context.class);
        Context oldContext = Mockito.mock(Context.class, RETURNS_DEEP_STUBS);
        Person user = Mockito.mock(Person.class);

        when(oldContext.getOwner().getId()).thenReturn(1);
        when(user.getId()).thenReturn(1);
        when(context.checkLengths()).thenReturn(false);
        when(DAOKontext.get(context.getId())).thenReturn(oldContext);

        instance.updateKontext(context, user);
    }

    /**
     * Test of updateKontext method, of class ContextAdmin.
     */
    @Test(expected = Exception.class)
    public void testUpdateKontextWrongID() {
        System.out.println("updateKontext");
        Context context = Mockito.mock(Context.class);
        Context oldContext = Mockito.mock(Context.class, RETURNS_DEEP_STUBS);
        Person user = Mockito.mock(Person.class);

        when(oldContext.getOwner().getId()).thenReturn(1);
        when(user.getId()).thenReturn(2);
        when(context.checkLengths()).thenReturn(true);

        instance.updateKontext(context, user);
    }

}
