package GTD.DL.DLDAO;

import GTD.DL.DLEntity.*;
import GTD.DL.DLInterfaces.IDAONote;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DAO class for Notes
 *
 * @author Drugnaov
 */
@Service
public class DAONote extends DAOGeneric<Note> implements IDAONote {

    public DAONote() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Note> getNotesOfTask(Task task) {
        Session session = null;
        List<Note> notes = null;
        Transaction tx = null;
        try {
            session = this.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(
                    "from " + Note.class.getName() + " t "
                    + "where t.task = :task"
            );
            query.setParameter("task", task);
            notes = (List<Note>) query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return notes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Note> getNotesOfProject(Project project) {
        Session session = null;
        List<Note> notes = null;
        Transaction tx = null;
        try {
            session = this.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(
                    "from " + Note.class.getName() + " t "
                    + "where t.project = :project"
            );
            query.setParameter("project", project);
            notes = (List<Note>) query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return notes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Note> getAll() {
        return (List<Note>) this.getAll(Note.class);
    }

    @Override
    public Note get(int id) {
        return (Note) this.get(Note.class, id);
    }

}
