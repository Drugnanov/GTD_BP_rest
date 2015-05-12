package GTD.DL.DLInterfaces;

import GTD.DL.DLEntity.Task;
import GTD.DL.DLEntity.Context;
import GTD.DL.DLEntity.Person;
import java.util.List;

/**
 * Interface pro správu Úkolu v databázi.
 *
 * @author slama
 * @version 2.0
 */
public interface IDAOTask extends IDAOGeneric<Task> {

    List<Task> getAll();

    Task get(int id);

    /**
     * Vrátí všechny úkoly daného kontextu.
     *
     * @return
     *
     * @param kontext
     */
    List<Task> getUkolyKontextu(Context kontext);

    /**
     * Vrátí všechny úkoly přiřazené dané osobě
     *
     * @return
     *
     * @param osoba
     */
    List<Task> getUkolyOsoby(Person osoba);

}
