package GTD.BL.BLAktivity;

import GTD.DL.DLInterfaces.IDAOActivity;
import GTD.DL.DLInterfaces.IDAOState;
import GTD.DL.DLEntity.Activity;
import GTD.DL.DLEntity.ActivityState;
import GTD.DL.DLEntity.Person;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Trída pro manipulaci s cinnostmi.
 *
 * @author slama
 * @version 2.0
 * @created 10-01-2015 10:30:00
 */
@Component
public class ActivitiyAdmin {

    private IDAOActivity daoCinnost;
    private IDAOState daoStav;

    /**
     * Správce osob - pomocí něj přistupují ostatní správci k přihlášenému uživateli.
     */
    public ActivitiyAdmin() {

    }

    @Autowired
    public ActivitiyAdmin(IDAOActivity daoCinnost, IDAOState daoStav) {
        this.daoCinnost = daoCinnost;
        this.daoStav = daoStav;
    }

    public void setDaoCinnost(IDAOActivity dao) {
        daoCinnost = dao;
    }

    public void setDaoStav(IDAOState daoStav) {
        this.daoStav = daoStav;
    }

    /**
     * Vytvorí novou cinnost.
     *
     * @param user logged-in user
     *
     * @param cinnost
     */
    public void addCinnost(Activity cinnost, Person user) {
        if (cinnost.getOwner().equals(user)) {
            daoCinnost.create(cinnost);
        } else {
            throw new SecurityException("Activity owned by '"
                    + cinnost.getOwner().getUsername() + "' can't be added by '"
                    + user.getUsername() + "'");
        }
    }

    /**
     * Označí činnost jako "archivovanou". Toto může udělat pouze vlastník činnosti.
     *
     * @param user logged-in user
     *
     * @param cinnost
     */
    public void archiveCinnost(Activity cinnost, Person user) {
        if (cinnost.getOwner().equals(user)) {
            ActivityState archivovana = daoStav.getCinnostArchivovana();
            cinnost.setState(archivovana);
            daoCinnost.update(cinnost);
        } else {
            throw new SecurityException("Activity owned by '"
                    + cinnost.getOwner().getUsername() + "' can't be archived by '"
                    + user.getUsername() + "'");
        }
    }

    /**
     * Smaže činnost. Toto může udělat pouze vlastník činnosti.
     *
     * @param user logged-in user
     * @return
     *
     * @param cinnost
     */
    public void deleteCinnost(Activity cinnost, Person user) {
        if (cinnost.getOwner().equals(user)) {
            daoCinnost.delete(cinnost);
        } else {
            throw new SecurityException("Activity owned by '"
                    + cinnost.getOwner().getUsername() + "' can't be deleted by '"
                    + user.getUsername() + "'");
        }
    }

    /**
     * Vrátí cinnost na základě jejího ID.
     *
     * @param user logged-in user
     * @return
     *
     * @param id
     */
    public Activity getCinnost(int id, Person user) {
        Activity cinnost = daoCinnost.get(id);
        if (cinnost != null) {
            if (!cinnost.getOwner().equals(user)) {
                throw new SecurityException("Activity owned by '"
                        + cinnost.getOwner().getUsername() + "' can't be read by '"
                        + user.getUsername() + "'");
            }
        }
        return cinnost;
    }

    /**
     * Vrátí všechny cinnosti osoby.
     *
     * @param user logged-in user
     * @return
     *
     */
    public List<Activity> getCinnostiOsoby(Person user) {
        return daoCinnost.getCinnostiOsoby(user);
    }

    /**
     * Označí činnost jako "odloženou". Toto může udělat pouze vlastník činnosti.
     *
     * @param user logged-in user
     * @return
     *
     * @param cinnost
     */
    public void postponeCinnost(Activity cinnost, Person user) {
        if (cinnost.getOwner().equals(user)) {
            ActivityState odlozena = daoStav.getCinnostOdlozena();
            cinnost.setState(odlozena);
            daoCinnost.update(cinnost);
        } else {
            throw new SecurityException("Activity owned by '"
                    + cinnost.getOwner().getUsername() + "' can't be postponed by '"
                    + user.getUsername() + "'");
        }
    }

    /**
     * Označí činnost jako "zpracovanou". Toto může udělat pouze vlastník činnosti.
     *
     * @param user logged-in user
     * @return
     *
     * @param cinnost
     */
    public void processCinnost(Activity cinnost, Person user) {
        if (cinnost.getOwner().equals(user)) {
            ActivityState zpracovana = daoStav.getCinnostZpracovana();
            cinnost.setState(zpracovana);
            daoCinnost.update(cinnost);
        } else {
            throw new SecurityException("Activity owned by '"
                    + cinnost.getOwner().getUsername() + "' can't be processed by '"
                    + user.getUsername() + "'");
        }
    }

    /**
     * Uloží změněnou činnost. Toto může udělat pouze vlastník činnosti.
     *
     * @param user logged-in user
     * @return
     *
     * @param cinnost
     */
    public void updateCinnost(Activity cinnost, Person user) { // TODO steklsim updateCinnnost() je asi k nicemu
        if (cinnost.getOwner().equals(user)) {
            daoCinnost.update(cinnost);
        } else {
            throw new SecurityException("Activity owned by '"
                    + cinnost.getOwner().getUsername() + "' can't be updated by '"
                    + user.getUsername() + "'");
        }
    }

}
