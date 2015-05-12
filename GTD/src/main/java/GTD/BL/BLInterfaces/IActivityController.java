package GTD.BL.BLInterfaces;

import GTD.DL.DLEntity.Activity;
import GTD.DL.DLEntity.Person;
import java.util.List;

/**
 * Interface defines the way BL and PL communicates concerning activities
 *
 * @author GTD team
 * @version 2.0
 * @created 19-10-2014 12:30:52
 */
public interface IActivityController {

    /**
     * Adds new activity
     *
     * @return
     *
     * @param title
     * @param desc
     */
    boolean addActivity(String title, String desc);

    /**
     * Označí činnost jako "archivovanou".
     *
     * @return
     *
     * @param activity
     */
    boolean archiveActivity(Activity activity);

    /**
     * Smaže činnost.
     *
     * @return
     *
     * @param activity
     */
    boolean deleteActivity(Activity activity);

    /**
     * Vrátí činnosti konkrétní osoby.
     *
     * @return
     *
     * @param person
     */
    List getActivitiesOfPerson(Person person);

    /**
     * Označí činnost jako "odloženou".
     *
     * @return
     *
     * @param activity
     */
    boolean postponeActivity(Activity activity);

    /**
     * Označí činnost jako "zpracovanou".
     *
     * @return
     *
     * @param activity
     */
    boolean processActivity(Activity activity);

    /**
     * Odešle GUI pokyn k obnovení.
     */
    void refresh();

    /**
     * Uloží změněnou činnost (změna jména/popisu).
     *
     * @return
     *
     * @param activity
     */
    boolean updateActivity(Activity activity);

}
