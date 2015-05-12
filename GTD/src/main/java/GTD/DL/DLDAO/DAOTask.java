package GTD.DL.DLDAO;

import GTD.DL.DLEntity.Task;
import GTD.DL.DLEntity.Context;
import GTD.DL.DLEntity.Person;
import GTD.DL.DLInterfaces.IDAOTask;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

/**
 * Trída zapouzdruje metody pro ukládání a nacítání úkolu z databáze.
 *
 * @author slama
 * @version 2.0
 */
@Service
public class DAOTask extends DAOGeneric<Task> implements IDAOTask {

    /**
     * Konstruktor úkolu
     */
    public DAOTask() {

    }

    /**
     * Vrátí všechny úkoly daného kontextu.
     *
     * @return ukoly
     *
     * @param kontext
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Task> getUkolyKontextu(Context kontext) {
        Session session = null;
        List<Task> tasks = null;
        Transaction tx = null;
        try {
            session = this.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(
                    "from " + Task.class.getName() + " t "
                    + "where t.context = :context"
            );
            query.setParameter("context", kontext);
            tasks = (List<Task>) query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return tasks;
    }

    /**
     * Vrátí úkoly osoby.
     *
     * @return List<Ukol>
     *
     * @param osoba
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Task> getUkolyOsoby(Person osoba) {

        Session session = null;
        Transaction tx = null;
        List<Task> tasks = null;
        try {
            session = this.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(
                    "from " + Task.class.getName() + " t "
                    + "where t.owner = :owner"
            );
            query.setParameter("owner", osoba);
            tasks = (List<Task>) query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return tasks;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Task> getAll() {
        return (List<Task>) this.getAll(Task.class);
    }

    @Override
    public Task get(int id) {
        return (Task) this.get(Task.class, id);
    }

}
