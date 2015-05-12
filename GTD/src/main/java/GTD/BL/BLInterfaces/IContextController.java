package GTD.BL.BLInterfaces;

import GTD.DL.DLEntity.Person;
import GTD.DL.DLEntity.Context;

/**
 * Interface defines the way BL and PL communicates concerning contexts
 *
 * @author GTD team
 * @version 2.0
 * @created 19-10-2014 12:30:53
 */
public interface IContextController {

    /**
     * Přidá nový kotext zadané osobě.
     *
     * @return boolean
     *
     * @param context
     * @param person
     */
    boolean addContext(Context context, Person person);

    /**
     * Smaže kontext.
     *
     * @return boolean
     *
     * @param context
     */
    boolean deleteContext(Context context);

    /**
     * Pošle GUI pokyn k obnovení.
     */
    void refresh();

    /**
     * Změní název kontextu.
     *
     * @return boolean
     *
     * @param context
     */
    boolean updateContext(Context context);

}
