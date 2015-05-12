package GTD.BL.BLFiltry;

import GTD.BL.BLAktivity.exceptions.InvalidEntityException;
import GTD.DL.DLDAO.exceptions.ItemNotFoundException;
import GTD.DL.DLInterfaces.IDAOContext;
import GTD.DL.DLEntity.Person;
import GTD.DL.DLEntity.Context;
import GTD.DL.DLEntity.Task;
import GTD.DL.DLInterfaces.IDAOTask;

import java.util.List;

import GTD.restapi.util.exceptions.ObjectAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Trída zapouzdruje metody pro ukládání a nacítání kontextu z databáze.
 *
 * @author slama
 * @version 2.0
 * @created 19-10-2014 12:30:51
 */
@Component
public class ContextAdmin {

  private IDAOContext daoKontext;
  private IDAOTask daoTask;

  public ContextAdmin() {

  }

  @Autowired
  public ContextAdmin(IDAOContext daoContext, IDAOTask daoTask) {
    daoKontext = daoContext;
    this.daoTask = daoTask;
  }

  public void setDaoContext(IDAOContext dao) {
    this.daoKontext = dao;
  }

  public void setDaoTask(IDAOTask daoTask) {
    this.daoTask = daoTask;
  }

  /**
   * Přidá osobě nový kontext.
   *
   * @param context
   * @param user    logged-in user
   * @return
   */
  public void addContext(Context context, Person user) throws ObjectAlreadyExistsException {
    if (!isValid(context)) {
      throw new InvalidEntityException("Invalid context data");
    }

    context.setOwner(user);
    context.setId(0); // ignore sent id value
    List<Context> contexts = daoKontext.getKontextyOsoby(user);
    if (contexts.contains(context)) {
      throw new ObjectAlreadyExistsException("Context " + context.toString() + " already exists!");
    }
    daoKontext.create(context);
  }

  /**
   * Smaže kontext.
   *
   * @param user    logged-in user
   * @param kontext
   * @return
   */
  public void deleteKontext(Context kontext, Person user) {
    if (kontext.getOwner().getId() == user.getId()) {
      List<Task> tasks = daoTask.getUkolyKontextu(kontext);
      for (Task task : tasks) {
        task.setContext(null); // remove context from all tasks
        daoTask.update(task);
      }
      daoKontext.delete(kontext);
    }
    else {
      throw new SecurityException("Context owned by '"
          + kontext.getOwner().getUsername() + "' can't be deleted by '"
          + user.getUsername() + "'");
    }
  }

  /**
   * Vrátí kontext podle jeho ID.
   *
   * @param user logged-in user
   * @param id
   * @return
   */
  public Context getKontext(int id, Person user) {
    Context kontext = daoKontext.get(id);
    if (kontext.getOwner().getId() != user.getId()) {
      throw new SecurityException("Context owned by '"
          + kontext.getOwner().getUsername() + "' can't be accessed by '"
          + user.getUsername() + "'");
    }
    return kontext;
  }

  public Context getKontext(String title, Person user) {
    List<Context> contexts = getKontextyOsoby(user);
    for (Context context : contexts) {
      if (context.getTitle().equals(title)) {
        return context;
      }
    }
    throw new ItemNotFoundException(
        "User '" + user.getUsername() + "' doesn't have any context titled '" + title + "'");
  }

  /**
   * Vrátí všechny kontexty patrící zadané osobe.
   *
   * @param user logged-in user
   * @return
   */
  public List<Context> getKontextyOsoby(Person user) {
    return daoKontext.getKontextyOsoby(user);
  }

  /**
   * Uloží upraveny kontext.
   *
   * @param user    logged-in user
   * @param kontext
   * @return
   */
  public void updateKontext(Context kontext, Person user) {
    if (!isValid(kontext)) {
      throw new InvalidEntityException("Invalid context data");
    }

    Context oldContext = daoKontext.get(kontext.getId());

    if (oldContext.getOwner().getId() == user.getId()) {
      oldContext.update(kontext);
      daoKontext.update(oldContext);
    }
    else {
      throw new SecurityException("Context owned by '"
          + kontext.getOwner().getUsername() + "' can't be updated by '"
          + user.getUsername() + "'");
    }
  }

  private boolean isValid(Context context) {
    return context.checkLengths();
  }

  public void deleteKontextyOsoby(Person personToDelete) {
    List<Context> projects = getKontextyOsoby(personToDelete);
    for (Context context : projects) {
      deleteKontext(context, personToDelete);
    }
  }
}
