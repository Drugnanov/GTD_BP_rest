/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.DL.DLDAO;

import GTD.DL.DLDAO.exceptions.ConstraintException;
import GTD.DL.DLDAO.exceptions.DAOServerException;
import GTD.DL.DLDAO.exceptions.ItemNotFoundException;
import GTD.DL.DLInterfaces.IDAOGeneric;
import GTD.DL.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author slama
 * @param <T>
 */
public abstract class DAOGeneric<T> implements IDAOGeneric<T> {

    private SessionFactory sessionFactory;

    public DAOGeneric() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void setSessionFactory(SessionFactory factory) {
        this.sessionFactory = factory;
    }

    protected Session openSession() {
        return sessionFactory.openSession();
    }

    @Override
    public void create(T entity) {
        Session session = null;
        Transaction tx = null;
        try {
            session = this.openSession();
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void update(T entity) {
        Session session = null;
        Transaction tx = null;
        try {
            session = this.openSession();
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(T entity) {
        Session session = null;
        Transaction tx = null;
        try {
            session = this.openSession();
            tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected List getAll(Class<T> clazz) {
        Session session = null;
        Transaction tx = null;
        List all = null;
        try {
            session = this.openSession();
            tx = session.beginTransaction();
            Query query = session.createQuery("from " + clazz.getName());
            all = query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return all;
    }

    @SuppressWarnings("unchecked")
    protected T get(Class<T> clazz, int id) {
        Session session = null;
        Transaction tx = null;
        T t = null;
        try {
            session = this.openSession();
            tx = session.beginTransaction();
            t = (T) session.get(clazz, id);
            tx.commit();
        } catch (HibernateException e) {
            handleException(e, tx);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        if (t == null) {
            throw new ItemNotFoundException(
                    "entity of class '" + clazz.getSimpleName() + "' with id '" + id + "' not found");
        }
        return t;
    }

    /**
     * Resi vyjimky v DAO operacich - zrusi transakci a vyhodi vlastni vyjimku
     *
     * @param e
     * @param tx
     */
    protected void handleException(HibernateException e, Transaction tx) {
        if (tx != null) {
            try {
                tx.rollback();
            } catch (HibernateException he) {
                throw new DAOServerException(he.getMessage(), he);
            }
        }
        if (e instanceof ConstraintViolationException) {
            throw new ConstraintException(e.getCause().getMessage(), e);
        } else {
            throw new DAOServerException(e.getMessage(), e);
        }
    }

    Object get(Object object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
