package GTD.BL.BLInterfaces;

import GTD.DL.DLEntity.Task;
import GTD.DL.DLEntity.Activity;
import GTD.DL.DLEntity.Person;
import GTD.DL.DLEntity.Context;
import GTD.DL.DLEntity.Interval;
import java.util.List;

/**
 * Interface defines the way BL and PL communicates concerning tasks
 *
 * @author GTD team
 * @version 2.0
 * @created 19-10-2014 12:30:54
 */
public interface ITaskController {

    /**
     * Označí úkol jako aktivní.
     *
     * @return boolean
     *
     * @param task
     */
    boolean activateTask(Task task);

    /**
     * Přidá nový úkol zadaných vlastností.
     *
     * @return boolean
     *
     * @param title
     * @param desc
     * @param ownerId
     * @param projektId
     * @param activity Činnost, ze které úkol vznikl (volitelné).
     */
    boolean addTask(String title, String desc, int ownerId, int projektId, Activity activity);

    /**
     * Vytvoří úkol a hned ho označí jako "hotový" (používá se při zpracování činnosti, pokud ji mohu a chci dokončit do
     * 2 minut).
     *
     * @return boolean
     *
     * @param nazev
     * @param popis
     * @param projektId
     * @param activity Činnost, ze které úkol vznikl (volitelné).
     */
    boolean addTwoMinutesTask(String nazev, String popis, int projektId, Activity activity);

    /**
     * Smaže úkol (resp. označí jako smazaný).
     *
     * @return boolean
     *
     * @param task
     */
    boolean deleteTask(Task task);

    /**
     * Označí úkol jako "dokončený".
     *
     * @return boolean
     *
     * @param task
     */
    boolean finishTask(Task task);

    /**
     * Vrátí všechny úkoly
     *
     * @return List of all tasks
     */
    List getAllUkoly();

    /**
     * Vrátí všechny úkoly přiřazené dané osobě
     *
     * @return List of person's tasks
     *
     * @param person
     */
    List getUkolyOsoby(Person person);

    /**
     * Změní vlastníka úkolu.
     *
     * @return boolean
     *
     * @param task
     * @param newOwner
     */
    boolean changeOwner(Task task, Person newOwner);

    /**
     * Odešle GUI pokyn k obnovení.
     */
    void refresh();

    /**
     * Nastaví kontext úkolu.
     *
     * @return boolean
     *
     * @param task
     * @param context
     */
    boolean setContext(Task task, Context context);

    /**
     * Přidá úkol do kalendáře.
     *
     * @return boolean
     *
     * @param task
     * @param interval
     */
    boolean scheduleTask(Task task, Interval interval);

    /**
     * Změní název a/nebo popis úkolu.
     *
     * @return boolean
     *
     * @param task
     */
    boolean updateTask(Task task);

}
