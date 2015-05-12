package GTD.DL.DLDAO;

import GTD.DL.DLDAO.exceptions.DAOException;
import GTD.DL.DLDAO.exceptions.ItemNotFoundException;
import GTD.DL.DLEntity.ActivityState;
import GTD.DL.DLEntity.ContactType;
import GTD.DL.DLEntity.PersonState;
import GTD.DL.DLEntity.ProjectState;
import GTD.DL.DLEntity.TaskState;
import GTD.DL.DLEntity.NoteState;
import GTD.DL.DLEntity.Type;
import GTD.DL.DLInterfaces.IDAOState;
import GTD.DL.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

/**
 * Trída zapouzdruje metody pro získání stavů objektů
 *
 * @author slama
 * @version 2.0
 */
@Component
public class DAOState implements IDAOState {

    private static final String TYPE_PERSON_AKTIVNI = "A";
    private static final String TYPE_PERSON_NEAKTIVNI = "N";

    private static final String TYPE_ACTIVITY_KEZPRACOVANI = "K";
    private static final String TYPE_ACTIVITY_ZAHOZENA = "H";
    private static final String TYPE_ACTIVITY_ARCHIVOVANA = "A";
    private static final String TYPE_ACTIVITY_ODLOZENA = "O";
    private static final String TYPE_ACTIVITY_ZPRACOVANA = "Z";

    private static final String TYPE_CONTACT_EMAIL = "E";
    private static final String TYPE_CONTACT_TELEFON = "T";

    private static final String TYPE_PROJECT_AKTIVNI = "A";
    private static final String TYPE_PROJECT_DOKONCENY = "D";

    private static final String TYPE_TASK_VYTVORENY = "V";
    private static final String TYPE_TASK_AKTIVNI = "A";
    private static final String TYPE_TASK_VKALENDARI = "K";
    private static final String TYPE_TASK_HOTOVY = "H";

    private static final String TYPE_NOTE_ACTIVE = "A";
    private static final String TYPE_NOTE_DELETED = "D";
    private static final String TYPE_NOTE_HIDE = "H";

    private SessionFactory sessionFactory;

    public DAOState() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void setSessionFactory(SessionFactory factory) {
        this.sessionFactory = factory;
    }

    protected Session openSession() {
        return sessionFactory.openSession();
    }

    public void finalize() throws Throwable {

    }

    private Type getType(Class clazz, String kod) {
        List list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = this.openSession();
            tx = session.beginTransaction();
            String hql = "from " + clazz.getSimpleName() + " where code = :code ";
            Query query = session.createQuery(hql);
            query.setParameter("code", kod);
            list = query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        if (list != null && list.size() != 1) {
            throw new RuntimeException(
                    "One type for entity '" + clazz.getSimpleName() + "' with code '" + kod + "' expected, got " + list.
                    size());
        }
        if (list == null || list.isEmpty()) {
            throw new ItemNotFoundException(
                    "Type with class '" + clazz.getSimpleName() + "' and code '" + kod + "' not found");
        } else {
            return (Type) list.get(0);
        }
    }

    @Override
    public ActivityState getActivityState(String code) {
        return (ActivityState) getType(ActivityState.class, code);
    }

    @Override
    public ContactType getContactType(String code) {
        return (ContactType) getType(ContactType.class, code);
    }

    @Override
    public PersonState getPersonState(String code) {
        return (PersonState) getType(PersonState.class, code);
    }

    @Override
    public ProjectState getProjectState(String code) {
        return (ProjectState) getType(ProjectState.class, code);
    }

    @Override
    public TaskState getTaskState(String code) {
        return (TaskState) getType(TaskState.class, code);
    }

    @Override
    public NoteState getNoteState(String code) {
        return (NoteState) getType(NoteState.class, code);
    }

    @Override
    public NoteState getNoteStateActive() {
        return (NoteState) this.getType(NoteState.class, TYPE_NOTE_ACTIVE);
    }

    @Override
    public NoteState getNoteStateHide() {
        return (NoteState) this.getType(NoteState.class, TYPE_NOTE_HIDE);
    }

    @Override
    public NoteState getNoteStateDeleted() {
        return (NoteState) this.getType(NoteState.class, TYPE_NOTE_DELETED);
    }

    @Override
    public ActivityState getCinnostArchivovana() {
        return (ActivityState) this.getType(ActivityState.class, TYPE_ACTIVITY_ARCHIVOVANA);
    }

    @Override
    public ActivityState getCinnostKeZpracovani() {
        return (ActivityState) this.getType(ActivityState.class, TYPE_ACTIVITY_KEZPRACOVANI);
    }

    @Override
    public ActivityState getCinnostOdlozena() {
        return (ActivityState) this.getType(ActivityState.class, TYPE_ACTIVITY_ODLOZENA);
    }

    @Override
    public ActivityState getCinnostZahozena() {
        return (ActivityState) this.getType(ActivityState.class, TYPE_ACTIVITY_ZAHOZENA);
    }

    @Override
    public ActivityState getCinnostZpracovana() {
        return (ActivityState) this.getType(ActivityState.class, TYPE_ACTIVITY_ZPRACOVANA);
    }

    @Override
    public ContactType getKontaktEmail() {
        return (ContactType) this.getType(ContactType.class, TYPE_CONTACT_EMAIL);
    }

    @Override
    public ContactType getKontaktTelefon() {
        return (ContactType) this.getType(ContactType.class, TYPE_CONTACT_TELEFON);
    }

    @Override
    public PersonState getOsobaAktivni() {
        return (PersonState) this.getType(PersonState.class, TYPE_PERSON_AKTIVNI);
    }

    @Override
    public PersonState getOsobaNeaktivni() {
        return (PersonState) this.getType(PersonState.class, TYPE_PERSON_NEAKTIVNI);
    }

    @Override
    public ProjectState getProjektAktivni() {
        return (ProjectState) this.getType(ProjectState.class, TYPE_PROJECT_AKTIVNI);
    }

    @Override
    public ProjectState getProjektDokonceny() {
        return (ProjectState) this.getType(ProjectState.class, TYPE_PROJECT_DOKONCENY);
    }

    @Override
    public TaskState getUkolAktivni() {
        return (TaskState) this.getType(TaskState.class, TYPE_TASK_AKTIVNI);
    }

    @Override
    public TaskState getUkolHotovy() {
        return (TaskState) this.getType(TaskState.class, TYPE_TASK_HOTOVY);
    }

    @Override
    public TaskState getUkolVKalendari() {
        return (TaskState) this.getType(TaskState.class, TYPE_TASK_VKALENDARI);
    }

    @Override
    public TaskState getUkolVytvoreny() {
        return (TaskState) this.getType(TaskState.class, TYPE_TASK_VYTVORENY);
    }

    /**
     * Resi vyjimky v DAO operacich - zrusi transakci a vyhodi vlastni vyjimku
     *
     * @param e
     * @param tx
     */
    protected void handleException(HibernateException e, Transaction tx) {
        if (tx != null) {
            tx.rollback();
        }
        throw new DAOException(e.getMessage(), e);
    }
}
