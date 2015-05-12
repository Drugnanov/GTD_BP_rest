
package GTD.BL.BLAktivity;

import GTD.BL.BLFiltry.ContextAdmin;
import GTD.BL.BLOsoby.PersonAdmin;
import GTD.DL.DLDAO.DAOTask;
import GTD.DL.DLEntity.Activity;
import GTD.DL.DLEntity.Context;
import GTD.DL.DLEntity.Person;
import GTD.DL.DLEntity.Task;
import GTD.DL.DLEntity.TaskState;
import GTD.DL.DLInterfaces.IDAOState;
import java.util.List;
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
 * @author admin
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskAdminTest {
    
        
    @Mock
    private static DAOTask daoTask;
    
    @Mock
    private static ActivitiyAdmin activityAdmin;
    
    @Mock
    private static NoteAdmin noteAdmin;
        
    @Mock
    private static PersonAdmin personAdmin;       
    
    @Mock
    private static IDAOState daoState;
    
    @Mock
    private static ContextAdmin contextAdmin;
          
    @InjectMocks
    private static TaskAdmin instance;
    
    public TaskAdminTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        daoTask = Mockito.mock(DAOTask.class);
        activityAdmin = Mockito.mock( ActivitiyAdmin.class);
        personAdmin = Mockito.mock(PersonAdmin.class);
        daoState = Mockito.mock(IDAOState.class);
        noteAdmin = Mockito.mock(NoteAdmin.class);
        contextAdmin = Mockito.mock(ContextAdmin.class);
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
     * Test of addTask method, of class TaskAdmin.
     */
    @Test
    public void testAddUkolOK() {
        System.out.println("addTask");
        
        Person user = Mockito.mock(Person.class);
        Task ukol = Mockito.mock(Task.class, RETURNS_DEEP_STUBS);
        TaskState taskstate = Mockito.mock(TaskState.class);
        Activity cinnost = Mockito.mock(Activity.class, RETURNS_DEEP_STUBS);
        
        when(cinnost.getOwner().getId()).thenReturn(1);     
        when(taskstate.getCode()).thenReturn("V");
        when(ukol.getCalendar()).thenReturn(null);
        when(ukol.getState()).thenReturn(null);
        when(ukol.getProject().getOwner().getId()).thenReturn(1);
        when(ukol.getContext().getOwner().getId()).thenReturn(1);
        when(ukol.getOwner().getId()).thenReturn(1);
        when(user.getId()).thenReturn(1);
        
        when(daoState.getUkolVytvoreny()).thenReturn(taskstate);
        when(daoState.getUkolVKalendari()).thenReturn(taskstate);
        when(ukol.checkLengths()).thenReturn(true);
       
        instance.addUkol(ukol, user, null); 
    }
    
     @Test(expected=Exception.class)
    public void testAddUkolInvalidTitle() {
        System.out.println("addTask");
        
        Person user = Mockito.mock(Person.class);
        Task ukol = Mockito.mock(Task.class, RETURNS_DEEP_STUBS);
        TaskState taskstate = Mockito.mock(TaskState.class);

        when(taskstate.getCode()).thenReturn("V");
        when(ukol.getCalendar()).thenReturn(null);
        when(ukol.getState()).thenReturn(null);
        when(ukol.getProject().getOwner().getId()).thenReturn(1);
        when(ukol.getContext().getOwner().getId()).thenReturn(1);
        when(user.getId()).thenReturn(1);
        
        when(daoState.getUkolVytvoreny()).thenReturn(taskstate);
        when(daoState.getUkolVKalendari()).thenReturn(taskstate);
        when(ukol.checkLengths()).thenReturn(false);
       
        instance.addUkol(ukol, user, null); 
    }

    /**
     * Test of deleteUkol method, of class TaskAdmin. Id of the user is not the
     * same as the id of the owner of the ukol.
     */
    @Test(expected=Exception.class)
    public void exceptionThrowntestDeleteUkolWrongID() {
        System.out.println("deleteUkol wrong id of the user");
        Person user = Mockito.mock(Person.class);
        Task ukol = Mockito.mock(Task.class, RETURNS_DEEP_STUBS);
        
        when(ukol.getOwner().getId()).thenReturn(1);
        when(user.getId()).thenReturn(2);
        when(ukol.getProject().getOwner().getId()).thenReturn(3);
             
        instance.deleteUkol(ukol, user);
    }

    /**
     * Test of deleteUkol method, of class TaskAdmin.
     */
    @Test
    public void testDeleteUkolGoodID() {
        System.out.println("delete ukol good id");
        Person user = Mockito.mock(Person.class);
        Task ukol = Mockito.mock(Task.class, RETURNS_DEEP_STUBS);

        when(ukol.getOwner().getId()).thenReturn(1);
        when(user.getId()).thenReturn(1);
        when(ukol.getProject().getOwner().getId()).thenReturn(1);
        
        instance.deleteUkol(ukol, user);
        
    }
    /**
     * Test of getAllUkoly method, of class TaskAdmin.
     */
    @Test
    public void testGetAllUkolyEmptyList() {
        System.out.println("getAllUkoly");
        
        List<Task> list = Mockito.mock(List.class);;
       
        when(list.isEmpty()).thenReturn(true);
        when(daoTask.getAll()).thenReturn(list);

        List<Task> result = instance.getAllUkoly();
        assertEquals(true, result.isEmpty());

    }

    /**
     * Test of getUkol method, of class TaskAdmin. 
     */
    @Test
    public void testGetUkolGoodID() {
        System.out.println("getUkol");
        int expectedId = 10;
        
        Person user = Mockito.mock(Person.class);
        Task ukol = Mockito.mock(Task.class, RETURNS_DEEP_STUBS);
        
        when(user.getId()).thenReturn(1);
        when(ukol.getOwner().getId()).thenReturn(1);
        when(ukol.getProject()).thenReturn(null);
        when(ukol.getId()).thenReturn(10);
        when(daoTask.get(expectedId)).thenReturn(ukol); 
        
        Task result = instance.getUkol(expectedId, user);
        int resultID = result.getId();
        
        assertEquals(expectedId, resultID);
    }

    /**
     * Test of getUkolyKontextu method, of class TaskAdmin.
     */
    @Test
    public void testGetUkolyKontextuGoodID() {
        System.out.println("getUkolyKontextu");
        Context kontext = Mockito.mock(Context.class, RETURNS_DEEP_STUBS);
        Person user = Mockito.mock(Person.class);
        List list = Mockito.mock(List.class);
        
        when(kontext.getOwner().getId()).thenReturn(1);
        when(user.getId()).thenReturn(1);
        when(daoTask.getUkolyKontextu(kontext)).thenReturn(list);
        when(list.isEmpty()).thenReturn(true);
 
        List result = instance.getUkolyKontextu(kontext, user);
        assertEquals(true, result.isEmpty());

    }

    /**
     * Test of getUkolyOsoby method, of class TaskAdmin.
     */
    @Test
    public void testGetUkolyOsobyEmptyList() {
        System.out.println("getUkolyOsoby");
        Person user = null;
        List list = Mockito.mock(List.class);
        
        when(list.isEmpty()).thenReturn(true);
        when(daoTask.getUkolyOsoby(user)).thenReturn(list);
      
        List result = instance.getUkolyOsoby(user);
        assertEquals(true, result.isEmpty());

    }

    /**
     * Test of setKontext method, of class TaskAdmin.
     */
    @Test(expected=Exception.class)
    public void exceptionThrowntestSetKontextWrongID() {
        System.out.println("setKontext");
        
        Task ukol = Mockito.mock(Task.class);
        Context kontext = Mockito.mock(Context.class);
        Person user = Mockito.mock(Person.class);
        
        when(ukol.getOwner().getId()).thenReturn(2);
        when(kontext.getOwner().getId()).thenReturn(1);
        when(user.getId()).thenReturn(1);
        
        instance.setKontext(ukol, kontext, user);

    }

    /**
     * Test of updateUkol method, of class TaskAdmin.
     */
    @Test(expected=Exception.class)
    public void exceptionThrowntestUpdateUkolWrongUser() {
        System.out.println("updateUkol");
        Task ukol = Mockito.mock(Task.class, RETURNS_DEEP_STUBS);
        Person user = Mockito.mock(Person.class);
        Task oldukol = Mockito.mock(Task.class);
        
        when(ukol.getOwner().getId()).thenReturn(1); //newOwner
        when(daoTask.get(ukol.getId())).thenReturn(oldukol);
        when(oldukol.getOwner().getId()).thenReturn(1);
        
        when(user.getId()).thenReturn(2);
        
        instance.updateUkol(ukol, user);

    }
    
}
