package GTD.BL.BLAktivity;

import GTD.BL.BLAktivity.exceptions.InvalidEntityException;
import GTD.DL.DLEntity.*;
import GTD.DL.DLInterfaces.IDAONote;
import GTD.DL.DLInterfaces.IDAOState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Trída pro manipulaci s úkoly.
 *
 * @author slama
 * @version 2.0
 * @created 19-10-2014 12:30:55
 */
@Component
public class NoteAdmin {

    @Autowired
    private IDAONote daoNote;

    @Autowired
    private IDAOState daoState;

    /**
     * Creates new Note
     *
     * @param note
     * @return
     */
    public void addNote(Note note) {
        if (!isValid(note)) {
            throw new InvalidEntityException("Invalid note data");
        }
        note.setCreateAttr(note);
        note.setState(daoState.getNoteStateActive());
    }

    public void addNotes(List<Note> notes) {
        for (Note note : notes) {
            addNote(note);
        }
    }

    /**
     * Delete a Note
     *
     * @param note l
     * @return
     */
    public void deleteUkol(Note note) {
        daoNote.delete(note);
    }

    /**
     * Returns all notes
     *
     * @return
     */
    public List<Note> getAllNotes() {
        return daoNote.getAll();
    }

    /**
     * Returns Note vy idNote.
     *
     * @param idNote
     * @return
     */
    public Note getNote(int idNote) {
        Note note = daoNote.get(idNote);
        return note;
    }

    /**
     * Updates Notes
     *
     * @param note
     * @return
     */
    public void updateNote(Note note) {
        if (!isValid(note)) {
            throw new InvalidEntityException("Invalid task data");
        }

        Note oldNote = daoNote.get(note.getId());
        if (oldNote.equals(note)) {
            return;
        }
        note.setUpdateAttr(oldNote);
        //daoNote.update(note);
    }

    private void deleteNotes(Action action) {
        List<Note> notes = new ArrayList<Note>();
        if (action instanceof Task){
            notes = daoNote.getNotesOfTask((Task) action);
        }
        if (action instanceof Project){
            notes = daoNote.getNotesOfProject((Project) action);
        }
        for (Note note: notes){
            deleteNote(note);
        }

    }

    private void deleteNote(Note note) {
        daoNote.delete(note);
    }

    public void updateNotes(List<Note> notes, Action action) {
        if (notes == null || notes.size() == 0){
            deleteNotes(action);
            return;
        }
        for (Note note : notes) {
            if (note.getId() > 0) {
                updateNote(note);
            } else {
                addNote(note);
            }
        }
    }



    private boolean isValid(Note note) {
        return note.checkLengths();
    }

}
