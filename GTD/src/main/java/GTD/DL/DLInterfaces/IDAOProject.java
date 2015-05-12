package GTD.DL.DLInterfaces;

import GTD.DL.DLEntity.Project;
import GTD.DL.DLEntity.Person;
import java.util.List;

/**
 * Interface pro správu Projektu v databázi.
 *
 * @author slama
 * @version 2.0
 */
public interface IDAOProject extends IDAOGeneric<Project> {

    List<Project> getAll();

    Project get(int id);

    /**
     * Vrátí všechny projekty patřící zadané osobe.
     *
     * @return
     *
     * @param osoba
     */
    List<Project> getProjektyOsoby(Person osoba);

}
