package GTD.DL.DLInterfaces;

import GTD.DL.DLEntity.Activity;
import GTD.DL.DLEntity.Person;
import java.util.List;

/**
 * Interface pro správu Cinností v databázi.
 *
 * @author slama
 * @version 2.0
 */
public interface IDAOActivity extends IDAOGeneric<Activity> {

    List<Activity> getAll();

    Activity get(int id);

    /**
     * Vrátí všechny cinnosti patrící zadané osobe.
     *
     * @return
     *
     * @param osoba
     */
    List<Activity> getCinnostiOsoby(Person osoba);

}
