package GTD.DL.DLDAO;

import GTD.DL.DLEntity.Project;
import GTD.DL.DLEntity.Person;
import GTD.DL.DLInterfaces.IDAOProject;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

/**
 * Trída zapouzdruje metody pro ukládání a nacítání projektu z databáze.
 *
 * @author slama
 * @version 2.0
 */
@Component
public class DAOProject extends DAOGeneric<Project> implements IDAOProject {

    /**
     * Konstruktor projektu
     */
    public DAOProject() {

    }

    /**
     * Vrátí všechny projekty patřící zadané osobe.
     *
     * @return List<Projekt>
     *
     * @param osoba
     */
    // TODO steklsim refactor vsechny metody getXOsoby() - obecna metoda getOwnedEntities() - ale kam s ni??
    @Override
    @SuppressWarnings("unchecked")
    public List<Project> getProjektyOsoby(Person osoba) {
        Session session = null;
        Transaction tx = null;
        List<Project> projects = null;
        try {
            session = this.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery(
                    "from " + Project.class.getName() + " p "
                    + "where p.owner = :owner"
            );
            query.setParameter("owner", osoba);
            projects = (List<Project>) query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return projects;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Project> getAll() {
        return (List<Project>) this.getAll(Project.class);
    }

    @Override
    public Project get(int id) {
        return (Project) this.get(Project.class, id);
    }

}
