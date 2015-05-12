package GTD.BL.BLInterfaces;

import GTD.DL.DLEntity.Activity;

/**
 * Rozhraní pro komunikaci přímo s hlavní třídou GUI (PL) (v BL používáno po změně dat pro pokyn k aktualizaci dat v
 * GUI).
 *
 * @author GTD team
 * @version 2.0
 * @created 19-10-2014 12:30:53
 */
public interface IGTDGUI {

    /**
     * Refreshes all concerned views
     */
    void refresh();

    /**
     * Shows view with actions concerning activities
     */
    void showActivities();

    /**
     * Shows an error message
     *
     * @param error error
     */
    void showError(String error);

    /**
     * Shows tasks of the logged in user
     */
    void showMojeUkoly();

    /**
     * Shows the login view
     */
    void showPrihlaseni();

    /**
     * Shows process activity dialog
     *
     * @param activity activity
     */
    void showProcessActivity(Activity activity);

    /**
     * Shows view with all tasks and projects
     */
    void showUkolyProjekty();

}
