package GTD.DL.DLInterfaces;

import GTD.DL.DLEntity.Context;
import GTD.DL.DLEntity.Person;
import java.util.List;

/**
 * Interface pro správu Kontextu v databázi.
 *
 * @author slama
 * @version 2.0
 */
public interface IDAOContext extends IDAOGeneric<Context> // TODO steklsim mozna jeste meziinterface IDAOFilter?
{

    List<Context> getAll();

    Context get(int id);

    /**
     * Vrátí všechny kontexty patrící zadané osobe.
     *
     * @return
     *
     * @param osoba
     */
    List<Context> getKontextyOsoby(Person osoba);

}
