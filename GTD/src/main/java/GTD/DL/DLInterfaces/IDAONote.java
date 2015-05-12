package GTD.DL.DLInterfaces;

import GTD.DL.DLEntity.*;

import java.util.List;

/**
 * Interface pro správu Úkolu v databázi.
 *
 * @author slama
 * @version 2.0
 */
public interface IDAONote extends IDAOGeneric<Note> {

    List<Note> getAll();

    Note get(int id);

    /**
     * Returns all notes for a task
     *
     * @return
     *
     * @param task
     */
    List<Note> getNotesOfTask(Task task);

    /**
     * Returns all notes for a project
     *
     * @return
     *
     * @param project
     */
    List<Note> getNotesOfProject(Project project);

}
