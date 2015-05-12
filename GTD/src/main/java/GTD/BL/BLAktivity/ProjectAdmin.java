package GTD.BL.BLAktivity;

import GTD.BL.BLAktivity.exceptions.InvalidEntityException;
import GTD.DL.DLDAO.exceptions.ConstraintException;
import GTD.DL.DLDAO.exceptions.ItemNotFoundException;
import GTD.DL.DLEntity.*;
import GTD.DL.DLInterfaces.IDAOProject;
import GTD.BL.BLOsoby.PersonAdmin;
import GTD.DL.DLInterfaces.IDAOState;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Trída pro manipulaci s projekty.
 *
 * @author slama
 * @version 2.0
 * @created 19-10-2014 12:30:55
 */
@Component
public class ProjectAdmin {

    //private IDAOProject DAOProjekt;
    /**
     * Odkaz na ActivitiyAdmin - při přidání projektu vzniklého z činnosti je třeba tuto činnost označit jako
     * "zpracovanou" - to zařídí právě ActivitiyAdmin.
     */
    private ActivitiyAdmin spravceCinnosti;
    /**
     * Správce osob - pomocí něj přistupují ostatní správci k přihlášenému uživateli.
     */
    private PersonAdmin spravceOsob;
    private NoteAdmin noteAdmin;
    private IDAOProject daoProjekt;
    private IDAOState daoStav;

    public ProjectAdmin() {

    }

    @Autowired
    public ProjectAdmin(IDAOProject daoProjekt, IDAOState daoStav, ActivitiyAdmin spravceCinnosti,
            PersonAdmin spravceOsob, NoteAdmin noteAdmin) {
        this.daoProjekt = daoProjekt;
        this.daoStav = daoStav;
        this.spravceCinnosti = spravceCinnosti;
        this.spravceOsob = spravceOsob;
        this.noteAdmin = noteAdmin;
    }

    public void setDaoProjekt(IDAOProject daoProjekt) {
        this.daoProjekt = daoProjekt;

    }

    public void setSpravceCinnosti(ActivitiyAdmin spravceCinnosti) {
        this.spravceCinnosti = spravceCinnosti;
    }

    public void setDaoStav(IDAOState daoStav) {
        this.daoStav = daoStav;
    }

    /**
     * Vytvorí nový projekt zadaných vlastností a uloží ho do databáze.
     * Podprojekt v projektu může vytvořit pouze
     * vlastník tohoto nadřazeného projektu.
     *
     * @param user logged-in user
     * @param projekt
     * @param cinnost Činnost, ze které projekt vznikl (pokud existuje)
     * - používá se pro označení činnosti jako "zpracované".
     * @return
     */
    public void addProjekt(Project projekt, Activity cinnost, Person user) {
        populateProject(projekt, user);
        if (!isValid(projekt)) {
            throw new InvalidEntityException("Invalid project data");
        }

        boolean isActivityOwner = cinnost == null ? true : user.getId() == cinnost.getOwner().getId();
        boolean isParentOwner = projekt.getParent() == null ? true : user.getId() == projekt.getParent().getOwner().
                getId();

//      if (projekt.getOwner().getId() == cinnost.getOwner().getId()
//              && projekt.getOwner().getId() == user.getId()
//              && projekt.getRodic().getOwner().getId() == user.getId()) {
        if (isActivityOwner && isParentOwner) {
            if (projekt.getState() == null) {
                projekt.setState(daoStav.getProjektAktivni());
            }
            noteAdmin.addNotes(projekt.getNotes());
            projekt.setOwner(user);
            daoProjekt.create(projekt);
            if (cinnost != null) {
                spravceCinnosti.processCinnost(cinnost,
                        user); // TODO steklsim pokud processCinnost() hodi vyjimku, nemel by se zrusit projekt?
            }
        } else {
            throw new SecurityException("Project owned by '"
                    + projekt.getOwner().getUsername() + "' can't be added by '"
                    + user.getUsername() + "'");
        }
    }

    /**
     * Smaže projekt (resp. označí jako smazaný) z databáze spolu se všemi jeho úkoly a podprojekty.
     * Project může mazat jeho vlastník nebo vlastník nadřazeného projektu (v 1.úrovni).
     *
     * @param user logged-in user
     * @param projekt
     * @return
     */
    public void deleteProjekt(Project projekt,
            Person user) { // TODO steklsim "oznacen jako smazany" - nemame na to stav
        if (projekt.getOwner().getId() == user.getId()
                || projekt.getParent().getOwner().getId() == user.getId()) {
            daoProjekt.delete(projekt);
        } else {
            throw new SecurityException("Project owned by '"
                    + projekt.getOwner().getUsername() + "' can't be deleted by '"
                    + user.getUsername() + "'");
        }
    }

    /**
     * Označí projekt jako "dokončený".
     * Dokončit projekt může jeho vlastník nebo vlastník nadřazeného projektu (v 1.úrovni).
     *
     * @param user logged-in user
     * @param projekt
     * @return
     */
    public void finishProjekt(Project projekt, Person user) {
        if (projekt.getOwner().getId() == user.getId()
                || projekt.getParent().getOwner().getId() == user.getId()) {
            ProjectState dokonceny = daoStav.getProjektDokonceny();
            projekt.setState(dokonceny);
            daoProjekt.update(projekt);
        } else {
            throw new SecurityException("Project '"
                    + projekt.getTitle() + "' can't be marked as finished by '"
                    + user.getUsername() + "'");
        }
    }

    /**
     * Vrátí všechny projekty
     *
     * @return
     */
    public List<Project> getAllProjekty() {
        // if user has role ROLE_ADMIN
        return daoProjekt.getAll();
    }

    /**
     * Vrátí projekt podle jeho ID.
     *
     * @param user logged-in user
     * @param id
     * @return
     */
    public Project getProjekt(int id, Person user) {
        Project projekt = daoProjekt.get(id);
        if (projekt != null
                && !(projekt.getOwner().getId() == user.getId())
                && !(projekt.getParent().getOwner().getId() == user.getId())) {
            throw new SecurityException("Project '"
                    + projekt.getTitle() + "' can't be marked as finished by '"
                    + user.getUsername() + "'");
        }
        return projekt;
    }

    /**
     * Vrátí všechny projekty patrící zadané osobe.
     *
     * @param user logged-in user
     * @return
     */
    public List<Project> getProjektyOsoby(Person user) {
        return daoProjekt.getProjektyOsoby(user);
    }

    /**
     * Uloží změněný projekt (změna názvu/popisu). Měnit projekt může jeho vlastník nebo vlastník nadřazeného projektu
     * (v 1.úrovni).
     *
     * @param user logged-in user
     * @param projekt
     * @return
     */
    public void updateProjekt(Project projekt, Person user) {
        populateProject(projekt, user);
        if (!isValid(projekt)) {
            throw new InvalidEntityException("Invalid project data");
        }

        Project oldProject = daoProjekt.get(projekt.getId());
        oldProject.update(projekt);

        if (oldProject.getOwner().getId() == user.getId()
                || (oldProject.getParent() != null && oldProject.getParent().getOwner().getId() == user.getId())) {
            noteAdmin.updateNotes(projekt.getNotes(), oldProject);
            daoProjekt.update(oldProject);
        } else {
            throw new SecurityException("Project '"
                    + oldProject.getTitle() + "' can't be updated by '"
                    + user.getUsername() + "'");
        }
    }

    private boolean isValid(Action action) {
        if (action == null) {
            return true;
        }
        return action.checkLengths();
    }

    public void deleteProjektyOsoby(Person personToDelete) {
        List<Project> projects = getProjektyOsoby(personToDelete);
        for (Project project : projects) {
            deleteProjekt(project, personToDelete);
        }
    }

    /**
     * Replaces dummy properties from API with their real counterparts from database (where it's needed)
     *
     * @param prj
     * @param user logged-in user
     */
    private void populateProject(Project prj, Person user) {
        if (prj.getState() != null) {
            try {
                ProjectState state = daoStav.getProjectState(prj.getState().getCode());
                prj.setState(state);
            } catch (ItemNotFoundException infe) {
                throw new ConstraintException("ProjectState '" + prj.getState().getCode() + "' not found", infe);
            }
        }

        if (prj.getParent() != null) {
            try {
                Project project = getProjekt(prj.getParent().getId(), user);
                prj.setParent(project);
            } catch (ItemNotFoundException infe) {
                throw new ConstraintException("Project with id " + prj.getParent().getId() + " not found", infe);
            }
        }
        if (prj.getOwner() != null) {
            try {
                Person person = spravceOsob.getOsoba(prj.getOwner().getUsername());
                prj.setOwner(person);
            } catch (ItemNotFoundException infe) {
                throw new ConstraintException("Person '" + prj.getOwner().getUsername() + "' not found", infe);
            }
        }
        prj.setProjectForNotes(prj);
    }
}
