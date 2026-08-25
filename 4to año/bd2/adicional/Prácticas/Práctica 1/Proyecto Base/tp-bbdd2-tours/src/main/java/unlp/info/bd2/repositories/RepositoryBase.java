package unlp.info.bd2.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class RepositoryBase<T> {

    protected final SessionFactory sessionFactory;

    protected RepositoryBase(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(T object) throws Exception {
        try {
            getSession().persist(object);
        } catch (Exception e) {
            if (e instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new Exception("Constraint Violation");
            } else {
                throw new Exception("Object can't be saved");
            }
        }
    }

    public T findById(Class<T> clase, Object id) {
        return getSession().get(clase, id);
    }

    public List<T> findAll(Class<T> clase) {
        return getSession()
                .createQuery("FROM " + clase.getName(), clase)
                .getResultList();
    }

    public void update(T object) {
        try {
            getSession().merge(object);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(T object) {
        try {
            getSession().remove(object);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}