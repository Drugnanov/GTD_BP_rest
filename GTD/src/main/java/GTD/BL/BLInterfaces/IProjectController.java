package GTD.BL.BLInterfaces;

import GTD.DL.DLEntity.Activity;
import GTD.DL.DLEntity.Project;
import GTD.DL.DLEntity.Person;
import java.util.List;

/**
 * Interface defines the way BL and PL communicates concerning projects
 *
 * @author GTD team
 * @version 2.0
 * @created 19-10-2014 12:30:54
 */
public interface IProjectController {

    /**
     * Přidá nový projekt zadaných vlastností.
     *
     * @return
     *
     * @param nazev
     * @param popis
     * @param vlastnik
     * @param rodicID
     * @param skupina
     * @param cinnost Činnost, ze které projekt vznikl (volitelné).
     */
    boolean addProject(String nazev, String popis, int vlastnik, int rodicID, List<Person> skupina, Activity cinnost);

    /**
     * Smaže projekt (resp. označí jako smazaný).
     *
     * @return
     *
     * @param projekt
     */
    boolean deleteProject(Project projekt);

    /**
     * Označí projekt jako "dokončený".
     *
     * @return
     *
     * @param projekt
     */
    boolean finishProject(Project projekt);

    /**
     * Vrátí všechny projekty
     *
     * @return
     */
    List getAllProjects();

    /**
     * Vrátí konkrétní projekt (GUI toto používá pro nastavení aktuálně zobrazeného projektu).
     *
     * @return
     *
     * @param id
     */
    Project getProject(int id);

    /**
     * Vrátí všechny projekty patřící dané osobě.
     *
     * @return
     *
     * @param osoba
     */
    List getProjectsOfPerson(Person osoba);

    /**
     * Změní vlastníka projektu.
     *
     * @return
     *
     * @param projekt
     * @param novyVlastnik
     */
    boolean changeOwner(Project projekt, Person novyVlastnik);

    /**
     * Odešle GUI pokyn k obnovení.
     */
    void refresh();

    /**
     * Změní název a/nebo popis projektu.
     *
     * @return
     *
     * @param projekt
     */
    boolean updateProject(Project projekt);

}
