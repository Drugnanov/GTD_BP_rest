
package GTD.BL.BLAktivity;

import GTD.BL.BLOsoby.PersonAdmin;
import GTD.DL.DLEntity.Activity;
import GTD.DL.DLEntity.Person;
import GTD.DL.DLEntity.Project;
import GTD.DL.DLEntity.ProjectState;
import GTD.DL.DLInterfaces.IDAOProject;
import GTD.DL.DLInterfaces.IDAOState;
import java.util.List;
import org.easymock.Mock;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
public class ProjectAdminTest {
    
    @Mock
    private static IDAOProject DAOProject;
    
    @Mock
    private static PersonAdmin spravceOsob;
  
    @Mock
    private static IDAOState DAOStav;
    
    @Mock
    private static NoteAdmin noteAdmin;
    
    @Mock
    private static ActivitiyAdmin spravceCinnosti;
    
    @InjectMocks
    private static ProjectAdmin instance;
    
    public ProjectAdminTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        DAOProject = Mockito.mock(IDAOProject.class);
        spravceOsob = Mockito.mock(PersonAdmin.class);
        DAOStav = Mockito.mock(IDAOState.class);
        spravceCinnosti = Mockito.mock(ActivitiyAdmin.class);
        noteAdmin = Mockito.mock(NoteAdmin.class);
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
     * Test of addProjekt method, of class ProjectAdmin.
     */
    @Test
    public void testAddProjektOK() {
        System.out.println("addProjekt");
        
        Project projekt = Mockito.mock(Project.class, RETURNS_DEEP_STUBS);
        Activity cinnost = Mockito.mock(Activity.class, RETURNS_DEEP_STUBS);
        Person user = Mockito.mock(Person.class);
        ProjectState state = new ProjectState();
            
        when(projekt.getState().getCode()).thenReturn("A");
        when(DAOStav.getProjectState("A")).thenReturn(state);
        when(user.getId()).thenReturn(1);
        when(cinnost.getOwner().getId()).thenReturn(1);
        when(projekt.getParent().getOwner().getId()).thenReturn(1);
        when(projekt.checkLengths()).thenReturn(true);

        instance.addProjekt(projekt, cinnost, user);
    }
    
    @Test (expected=Exception.class)
    public void testAddProjektInvalidTitle() {
        System.out.println("addProjekt");
        
        Project projekt = Mockito.mock(Project.class, RETURNS_DEEP_STUBS);
        Activity cinnost = Mockito.mock(Activity.class, RETURNS_DEEP_STUBS);
        Person user = Mockito.mock(Person.class);
        
        when(user.getId()).thenReturn(1);
        when(cinnost.getOwner().getId()).thenReturn(1);
        when(projekt.getParent().getOwner().getId()).thenReturn(1);
        when(projekt.checkLengths()).thenReturn(false);

        instance.addProjekt(projekt, cinnost, user);
    }
    
    @Test(expected=Exception.class)
    public void exceptionThrowntestAddProjektWrongID() {
        System.out.println("addProjekt");
        
        Project projekt = Mockito.mock(Project.class, RETURNS_DEEP_STUBS);
        Activity cinnost = Mockito.mock(Activity.class, RETURNS_DEEP_STUBS);
        Person user = Mockito.mock(Person.class);
        
        when(user.getId()).thenReturn(1);
        when(cinnost.getOwner().getId()).thenReturn(2);
        when(projekt.getParent().getOwner().getId()).thenReturn(1);

        instance.addProjekt(projekt, cinnost, user);

    }
    
    /**
     * Test of deleteProjekt method, of class ProjectAdmin.
     */
    @Test
    public void testDeleteProjektOK() {
        System.out.println("deleteProjekt");
        Project projekt = Mockito.mock(Project.class, RETURNS_DEEP_STUBS);
        Person user = Mockito.mock(Person.class);

        when(projekt.getOwner().getId()).thenReturn(1);
        when(user.getId()).thenReturn(1);
        
        instance.deleteProjekt(projekt, user);

    }

    /**
     * Test of deleteProjekt method, of class ProjectAdmin.
     */
    @Test(expected=Exception.class)
    public void exceptionThrowntestDeleteProjektWrongID() {
        System.out.println("deleteProjekt");
        Project projekt = Mockito.mock(Project.class);
        Person user = Mockito.mock(Person.class);

        when(projekt.getOwner().getId()).thenReturn(1);
        when(user.getId()).thenReturn(2);
        
        instance.deleteProjekt(projekt, user);

    }

    /**
     * Test of finishProjekt method, of class ProjectAdmin.
     */
    @Test
    public void testFinishProjektOK() {
        System.out.println("finishProjekt");
        Project projekt = Mockito.mock(Project.class, RETURNS_DEEP_STUBS);
        Person user = Mockito.mock(Person.class);
        
        when(projekt.getOwner().getId()).thenReturn(1);
        when(user.getId()).thenReturn(1);
        when(projekt.getParent().getOwner().getId()).thenReturn(2);
        
        instance.finishProjekt(projekt, user);

    }

    /**
     * Test of getAllProjekty method, of class ProjectAdmin.
     */
    @Test
    public void testGetAllProjektyEmptyList() {
        System.out.println("getAllProjekty");
        List<Project> list = Mockito.mock(List.class);;
       
        when(list.isEmpty()).thenReturn(true);
        when(DAOProject.getAll()).thenReturn(list);

        List<Project> result = instance.getAllProjekty();
        assertEquals(true, result.isEmpty());
    }

    /**
     * Test of getProjekt method, of class ProjectAdmin.
     */
    @Test
    public void testGetProjektGoodID() {
        System.out.println("getProjekt");
        int id = 10;
        Person user = Mockito.mock(Person.class);

        Project expResult = null;
        Project result = instance.getProjekt(id, user);
        assertEquals(expResult, result);

    }

    /**
     * Test of getProjektyOsoby method, of class ProjectAdmin.
     */
    @Test
    public void testGetProjektyOsobyWith5Porjects() {
        System.out.println("getProjektyOsoby");
        Person user =  Mockito.mock(Person.class);
        List<Project> list = Mockito.mock(List.class);
        
        when(list.size()).thenReturn(5);
        when(DAOProject.getProjektyOsoby(user)).thenReturn(list);
        
        List<Project> result = instance.getProjektyOsoby(user);
        assertEquals(5, result.size());

    }

}
