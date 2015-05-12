package GTD.BL.BLAktivity;

import GTD.BL.BLAktivity.exceptions.InvalidEntityException;
import GTD.BL.BLFiltry.ContextAdmin;
import GTD.DL.DLDAO.exceptions.ConstraintException;
import GTD.DL.DLDAO.exceptions.ItemNotFoundException;
import GTD.DL.DLEntity.*;
import GTD.DL.DLInterfaces.IDAOTask;
import GTD.BL.BLOsoby.PersonAdmin;
import GTD.DL.DLInterfaces.IDAOState;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Trída pro manipulaci s úkoly.
 *
 * @author slama
 * @version 2.0
 * @created 19-10-2014 12:30:55
 */
@Component
public class TaskAdmin {

  private IDAOTask daoTask;
  /**
   * Odkaz na ActivitiyAdmin - při přidání úkolu vzniklého z činnosti je
   * třeba tuto činnost označit jako "zpracovanou"
   * - to zařídí právě ActivitiyAdmin.
   */
  private ActivitiyAdmin activityAdmin;
  /**
   * Správce osob - pomocí něj přistupují ostatní správci k přihlášenému uživateli.
   */
  private PersonAdmin personAdmin;

  private NoteAdmin noteAdmin;

  private IDAOState daoState;

  @Autowired
  private ProjectAdmin projectAdmin;
  @Autowired
  private ContextAdmin contextAdmin;

  public TaskAdmin() {
  }

  @Autowired
  public TaskAdmin(IDAOTask daoTask, ActivitiyAdmin activityAdmin, PersonAdmin personAdmin, IDAOState daoState,
                   NoteAdmin noteAdmin) {
    this.daoTask = daoTask;
    this.activityAdmin = activityAdmin;
    this.personAdmin = personAdmin;
    this.daoState = daoState;
    this.noteAdmin = noteAdmin;
  }

  public void setDaoTask(IDAOTask daoTask) {
    this.daoTask = daoTask;
  }

  public void setActivityAdmin(ActivitiyAdmin activityAdmin) {
    this.activityAdmin = activityAdmin;
  }

  public void setPersonAdmin(PersonAdmin personAdmin) {
    this.personAdmin = personAdmin;
  }

  public void setDaoState(IDAOState daoState) {
    this.daoState = daoState;
  }

  public ProjectAdmin getProjectAdmin() {
    return projectAdmin;
  }

  public void setProjectAdmin(ProjectAdmin projectAdmin) {
    this.projectAdmin = projectAdmin;
  }

  public ContextAdmin getContextAdmin() {
    return contextAdmin;
  }

  public void setContextAdmin(ContextAdmin contextAdmin) {
    this.contextAdmin = contextAdmin;
  }

  /**
   * Vytvoří nový úkol. Úkol v projektu může vytvořit pouze vlastník tohoto projektu.
   *
   * @param user    logged-in user
   * @param ukol
   * @param cinnost Činnost, ze které úkol vznikl (pokud existuje) - používá se pro označení činnosti jako
   *                "zpracované".
   * @return
   */
  public void addUkol(Task ukol, Person user, Activity cinnost) {
    populateTask(ukol, user);
    if (!isValid(ukol)) {
      throw new InvalidEntityException("Invalid task data");
    }

    boolean isProjectOwner = ukol.getProject() == null ? true
        : ukol.getProject().getOwner().getId() == user.getId();
    boolean isActivityOwner = cinnost == null ? true
        : ukol.getOwner().getId() == cinnost.getOwner().getId();
    boolean isContextOwner = ukol.getContext() == null ? true
        : ukol.getContext().getOwner().getId() == user.getId();
    if (isProjectOwner && isActivityOwner && isContextOwner) {
      noteAdmin.addNotes(ukol.getNotes());
      if (ukol.getState() == null) {
        if (ukol.getCalendar() == null) {
          ukol.setState(daoState.getUkolVytvoreny());
        }
        else {
          ukol.setState(daoState.getUkolVKalendari());
        }
      }
      ukol.setCreator(user);
      ukol.setOwner(user); // don't care if owner or creator is already set - maybe not?
      daoTask.create(ukol);
      if (cinnost != null) {
        activityAdmin
            .processCinnost(cinnost, user);
        // TODO steklsim pokud processCinnost() hodi vyjimku, nemel by se zrusit ukol?
      }      // TODO steklsim tady hodit nejakou Spring exception? (az bude Spring)
    }
    else {
      throw new SecurityException("User '" + user.getUsername()
          + "' can't add a task to a project owned by '"
          + ukol.getOwner().getUsername() + "'");
    }
  }

  /**
   * Smaže úkol (resp. označí jako smazaný). Mazat úkol může pouze jeho vlastník a vlastník nadřazeného projektu (v
   * 1.úrovni).
   *
   * @param user logged-in user
   * @param ukol
   * @return
   */
  public void deleteUkol(Task ukol, Person user) {
    if (ukol.getOwner().getId() == user.getId()
        || (ukol.getProject() != null && ukol.getProject().getOwner().getId() == user.getId())) {
      daoTask.delete(ukol);
    }
    else {
      throw new SecurityException("Task owned by '"
          + ukol.getOwner().getUsername() + "' can't be deleted by '"
          + user.getUsername() + "'");
    }
  }

  /**
   * Vrátí všechny úkoly
   *
   * @return
   */
  public List<Task> getAllUkoly() {
    return daoTask.getAll();
  }

  /**
   * Vrátí úkol.
   *
   * @param user logged-in user
   * @param id
   * @return
   */
  public Task getUkol(int id, Person user) {
    Task ukol = daoTask.get(id);
    if (ukol != null) {
      int taskOwnerId = ukol.getOwner().getId();
      Project project = ukol.getProject();
      if ((taskOwnerId != user.getId() && project == null)
          || (taskOwnerId != user.getId()
          && ukol.getProject().getOwner().getId() != user.getId())) {
        throw new SecurityException("Task owned by '"
            + ukol.getOwner() + "' can't be read by '"
            + user);
      }
    }
    return ukol;
  }

  /**
   * Vrátí všechny úkoly daného kontextu.
   *
   * @param user    logged-in user
   * @param kontext
   * @return
   */
  public List<Task> getUkolyKontextu(Context kontext, Person user) {
    if (kontext.getOwner().getId() == user.getId()) {
      return daoTask.getUkolyKontextu(kontext);
    }
    else {
      throw new SecurityException("User '" + user.getUsername()
          + "' can't access context owned by '"
          + kontext.getOwner().getUsername() + "'");
    }
  }

  /**
   * @param user logged-in user
   * @return
   */
  public List<Task> getUkolyOsoby(Person user) {
    return daoTask.getUkolyOsoby(user);
  }

  /**
   * Nastaví kontext úkolu. Toto může udělat pouze vlastník úkolu.
   *
   * @param user    logged-in user
   * @param ukol
   * @param kontext
   * @return
   */
  public void setKontext(Task ukol, Context kontext, Person user) {
    if (!isValid(kontext)) {
      throw new InvalidEntityException("Invalid context data");
    }
    if (!isValid(ukol)) {
      throw new InvalidEntityException("Invalid task data");
    }

    if (ukol.getOwner().getId() == kontext.getOwner().getId()
        && ukol.getOwner().getId() == user.getId()) {
      ukol.setContext(kontext);
      daoTask.update(ukol);
      // TODO steklsim tady hodit nejakou Spring exception? (az bude Spring)
    }
    else {
      throw new SecurityException("Task owned by '"
          + ukol.getOwner().getUsername() + "' can't be altered by '"
          + user.getUsername() + "'");
    }
  }

  /**
   * Uloží změněný úkol (změna názvu/popisu). Změnit úkol může jeho vlastník nebo vlastník nadřazeného projektu (v
   * 1.úrovni).
   *
   * @param user logged-in user
   * @param ukol
   * @return
   */
  public void updateUkol(Task ukol, Person user) {
    populateTask(ukol, user);
    if (!isValid(ukol)) {
      throw new InvalidEntityException("Invalid task data");
    }

    Task oldTask = daoTask.get(ukol.getId());
    Person oldOwner = oldTask.getOwner();

    oldTask.update(ukol);
    if (oldTask.getOwner().getId() != oldOwner.getId()) {
      oldTask.setContext(null); // changing owner -> remove context (each user has their own contexts)
    }
    noteAdmin.updateNotes(ukol.getNotes(), oldTask);
    if (oldTask.getOwner().getId() == user.getId()
        || (oldTask.getProject() != null && oldTask.getProject().getOwner().getId() == user.getId())) {
      daoTask.update(oldTask);
    }
    else {
      throw new SecurityException("Task owned by '"
          + ukol.getOwner().getUsername() + "' can't be updated by '"
          + user.getUsername() + "'");
    }
  }

  private boolean isValid(Task task) {
    return task.checkLengths();
  }

  private boolean isValid(Context context) {
    return context.checkLengths();
  }

  public void deleteUkolyOsoby(Person personToDelete) {
    List<Task> tasks = getUkolyOsoby(personToDelete);
    for (Task task : tasks) {
      deleteUkol(task, personToDelete);
    }
  }

  /**
   * Replaces dummy properties from API with their real counterparts from database (where it's needed)
   *
   * @param task
   * @param user logged-in user
   */
  private void populateTask(Task task, Person user) {
    if (task.getOwner() != null) {
      try {
        Person owner = personAdmin.getOsoba(task.getOwner().getUsername());
        task.setOwner(owner);
      }
      catch (ItemNotFoundException infe) {
        throw new ConstraintException("Person '" + task.getOwner().getUsername() + "' not found", infe);
      }
    }
    if (task.getState() != null) {
      try {
        TaskState state = daoState.getTaskState(task.getState().getCode());
        task.setState(state);
      }
      catch (ItemNotFoundException infe) {
        throw new ConstraintException("TaskState '" + task.getState().getCode() + "' not found", infe);
      }
    }
    if (task.getContext() != null) {
      try {
        Context context = contextAdmin.getKontext(task.getContext().getId(), user);
        task.setContext(context);
      }
      catch (ItemNotFoundException infe) {
        throw new ConstraintException("Context with id" + task.getContext().getId() + " not found", infe);
      }
    }

    if (task.getProject() != null && task.getProject().getId() > 0) {
      try {
        Project project = projectAdmin.getProjekt(task.getProject().getId(), user);
        task.setProject(project);
      }
      catch (ItemNotFoundException infe) {
        throw new ConstraintException("Project with id" + task.getProject().getId() + " not found", infe);
      }
    }
    task.setTaskForNotes(task);
  }

  public boolean isCalendarTask(Task taskToPublish) {
    if (daoState.getUkolVKalendari().getCode().equals(
        taskToPublish.getState().getCode())) {
      return true;
    }
    return false;
  }

  public boolean checkInterval(Task taskToPublish) {
    if (!isCalendarTask(taskToPublish)) {
      return false;
    }
    Interval interval = taskToPublish.getCalendar();
    if (interval == null){
      return false;
    }
    Date from = interval.getFrom();
    Date to = interval.getTo();
    if (from == null || to == null){
      return false;
    }
    long fromLong = from.getTime();
    long toLong = to.getTime();
    //interval is too long
    if ((toLong - fromLong) > (24*60*60*1000)) {
      return false;
    }
    return true;
  }
}
