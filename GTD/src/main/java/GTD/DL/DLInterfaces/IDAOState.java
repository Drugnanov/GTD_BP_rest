package GTD.DL.DLInterfaces;

import GTD.DL.DLEntity.ActivityState;
import GTD.DL.DLEntity.ContactType;
import GTD.DL.DLEntity.PersonState;
import GTD.DL.DLEntity.ProjectState;
import GTD.DL.DLEntity.TaskState;
import GTD.DL.DLEntity.NoteState;

/**
 * Interface pro získání typů
 *
 * @author slama
 * @version 2.0
 */
public interface IDAOState {

    /**
     * Returns activity state with given code
     *
     * @param code
     * @return
     */
    ActivityState getActivityState(String code);

    /**
     * Vrátí stav: činnost Archivovaná
     *
     * @return
     */
    ActivityState getCinnostArchivovana();

    /**
     * Vrátí stav: činost Ke zpracování
     *
     * @return
     */
    ActivityState getCinnostKeZpracovani();

    /**
     * Vrátí stav: činost Odlozena
     *
     * @return
     */
    ActivityState getCinnostOdlozena();

    /**
     * Vrátí stav: činost Zahozena
     *
     * @return
     */
    ActivityState getCinnostZahozena();

    /**
     * Vrátí stav: činost Odlozena
     *
     * @return
     */
    ActivityState getCinnostZpracovana();

    /**
     * Returns contact type with given code
     *
     * @param code
     * @return
     */
    ContactType getContactType(String code);

    /**
     * Vrátí stav: konatakt email
     *
     * @return
     */
    ContactType getKontaktEmail();

    /**
     * Vrátí stav: konatakt telefon
     *
     * @return
     */
    ContactType getKontaktTelefon();

    /**
     * Returns person state with given code
     *
     * @param code
     * @return
     */
    PersonState getPersonState(String code);

    /**
     * Vrátí stav: osoby Aktivni
     *
     * @return
     */
    PersonState getOsobaAktivni();

    /**
     * Vrátí stav: osoby Aktivni
     *
     * @return
     */
    PersonState getOsobaNeaktivni();

    /**
     * Returns project state with given code
     *
     * @param code
     * @return
     */
    ProjectState getProjectState(String code);

    /**
     * Vrátí stav: projekt Aktivni
     *
     * @return
     */
    ProjectState getProjektAktivni();

    /**
     * Vrátí stav: projekt Dokonceny
     *
     * @return
     */
    ProjectState getProjektDokonceny();

    /**
     * Returns task state with given code
     *
     * @param code
     * @return
     */
    TaskState getTaskState(String code);

    NoteState getNoteState(String code);

    /**
     * Vrátí stav: úkol aktivní
     *
     * @return
     */
    TaskState getUkolAktivni();

    /**
     * Vrátí stav: úkol hotový
     *
     * @return
     */
    TaskState getUkolHotovy();

    /**
     * Vrátí stav: úkol v kalendáři
     *
     * @return
     */
    TaskState getUkolVKalendari();

    /**
     * Vrátí stav: úkol vytvořený
     *
     * @return
     */
    TaskState getUkolVytvoreny();

    NoteState getNoteStateActive();

    NoteState getNoteStateHide();

    NoteState getNoteStateDeleted();

}
