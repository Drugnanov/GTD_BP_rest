
package GTD.BL.BLAktivity;

import GTD.BL.BLOsoby.PersonAdmin;
import GTD.DL.DLEntity.Activity;
import GTD.DL.DLEntity.Person;
import GTD.DL.DLInterfaces.IDAOActivity;
import GTD.DL.DLInterfaces.IDAOState;
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
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author skvarla
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivitiyAdminTest {
    
    @Mock
    private static IDAOActivity DAOCinnost;
    
    @Mock
    private static IDAOState DAOStav;
    
    @Mock
    private static PersonAdmin spravceOsob;
    
    @InjectMocks
    private static ActivitiyAdmin instance = new ActivitiyAdmin();
    
    public ActivitiyAdminTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        DAOCinnost = Mockito.mock(IDAOActivity.class);
        DAOStav = Mockito.mock(IDAOState.class);
        spravceOsob = Mockito.mock(PersonAdmin.class);
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
     * Test of addCinnost method, of class ActivitiyAdmin.
     */
    @Test(expected = Exception.class)
    public void testAddCinnostWrongOwner() {
        System.out.println("addCinnost");
        Activity cinnost = Mockito.mock(Activity.class, RETURNS_DEEP_STUBS);
        Person user = Mockito.mock(Person.class);
        
        when(cinnost.getOwner()).thenReturn(user);
        when(user.equals(user)).thenReturn(false);
        
        instance.addCinnost(cinnost, user);
    }
    
    /**
     * Test of addCinnost method, of class ActivitiyAdmin.
     */
    @Test
    public void testAddCinnostOK() {
        System.out.println("addCinnost");
        Activity cinnost = Mockito.mock(Activity.class);
        Person owner = new Person();
        Person user = new Person();
        
        when(cinnost.getOwner()).thenReturn(owner);
        
        instance.addCinnost(cinnost, user);
        
    }

    /**
     * Test of archiveCinnost method, of class ActivitiyAdmin.
     */
    @Test(expected = SecurityException.class)
    public void testArchiveCinnostWrongOwner() {
        System.out.println("archiveCinnost");
        Activity cinnost = Mockito.mock(Activity.class);
        Person owner = new Person();
        owner.setId(2);
        Person user = new Person();
        user.setId(1);
        
        when(cinnost.getOwner()).thenReturn(owner);
        
        instance.archiveCinnost(cinnost, user);
    }
    
    /**
     * Test of archiveCinnost method, of class ActivitiyAdmin.
     */
    @Test
    public void testArchiveCinnostOK() {
        System.out.println("archiveCinnost");
        Activity cinnost = Mockito.mock(Activity.class);
        Person owner = new Person();
        owner.setId(1);
        Person user = new Person();
        user.setId(1);
        
        when(cinnost.getOwner()).thenReturn(owner);
        
        instance.archiveCinnost(cinnost, user);
    }

    /**
     * Test of deleteCinnost method, of class ActivitiyAdmin.
     */
    @Test(expected = SecurityException.class)
    public void testDeleteCinnostWrongOwner() {
        System.out.println("deleteCinnost");
        Activity cinnost = Mockito.mock(Activity.class);
        Person owner = new Person();
        owner.setId(2);
        Person user = new Person();
        user.setId(1);
        
        when(cinnost.getOwner()).thenReturn(owner);
        
        instance.deleteCinnost(cinnost, user);
    }

    /**
     * Test of getCinnost method, of class ActivitiyAdmin.
     */
    @Test (expected = SecurityException.class)
    public void testGetCinnostWrongOwner() {
        System.out.println("getCinnost");
        Activity cinnost = Mockito.mock(Activity.class, RETURNS_DEEP_STUBS);
        Person owner = new Person();
        owner.setId(2);
        Person user = new Person();
        user.setId(1);
        int id = 0;
        when(DAOCinnost.get(id)).thenReturn(cinnost);
        when(cinnost.getOwner()).thenReturn(owner);
        when(cinnost.getId()).thenReturn(id);
        Activity result = Mockito.mock(Activity.class);
        result = instance.getCinnost(id, user);

        assertEquals(cinnost, result);
    }
    /**
     * Test of postponeCinnost method, of class ActivitiyAdmin.
     */
    @Test
    public void testPostponeCinnostOK() {
        System.out.println("postponeCinnost");
        Activity cinnost = Mockito.mock(Activity.class);
        Person owner = new Person();
        owner.setId(1);
        Person user = new Person();
        user.setId(1);
        
        when(cinnost.getOwner()).thenReturn(owner);

        instance.postponeCinnost(cinnost, user);

    }
    
    /**
     * Test of postponeCinnost method, of class ActivitiyAdmin.
     */
    @Test(expected = SecurityException.class)
    public void testPostponeCinnostWrongOwner() {
        System.out.println("postponeCinnost");
        Activity cinnost = Mockito.mock(Activity.class);
        Person owner = new Person();
        owner.setId(1);
        Person user = new Person();
        user.setId(2);
        
        when(cinnost.getOwner()).thenReturn(owner);

        instance.postponeCinnost(cinnost, user);

    }

    /**
     * Test of processCinnost method, of class ActivitiyAdmin.
     */
    @Test
    public void testProcessCinnostOK() {
        System.out.println("processCinnost");
        Activity cinnost = Mockito.mock(Activity.class);
        Person owner = new Person();
        owner.setId(1);
        Person user = new Person();
        user.setId(1);
        
        when(cinnost.getOwner()).thenReturn(owner);

        instance.processCinnost(cinnost, user);
    }
    
    /**
     * Test of processCinnost method, of class ActivitiyAdmin.
     */
    @Test(expected = SecurityException.class)
    public void testProcessCinnostWrongOwner() {
        System.out.println("processCinnost");
        Activity cinnost = Mockito.mock(Activity.class);
        Person owner = new Person();
        owner.setId(2);
        Person user = new Person();
        user.setId(1);
        
        when(cinnost.getOwner()).thenReturn(owner);

        instance.processCinnost(cinnost, user);
    }

    /**
     * Test of updateCinnost method, of class ActivitiyAdmin.
     */
    @Test
    public void testUpdateCinnostOK() {
        System.out.println("updateCinnost");
        Activity cinnost = Mockito.mock(Activity.class);
        Person owner = new Person();
        owner.setId(1);
        Person user = new Person();
        user.setId(1);
        
        when(cinnost.getOwner()).thenReturn(owner);

        instance.updateCinnost(cinnost, user);
    }
    
    /**
     * Test of updateCinnost method, of class ActivitiyAdmin.
     */
    @Test (expected = SecurityException.class)
    public void testUpdateCinnostWrongOwner() {
        System.out.println("updateCinnost");
        Activity cinnost = Mockito.mock(Activity.class);
        Person owner = new Person();
        owner.setId(2);
        Person user = new Person();
        user.setId(1);
        
        when(cinnost.getOwner()).thenReturn(owner);

        instance.updateCinnost(cinnost, user);
    }
    
}
