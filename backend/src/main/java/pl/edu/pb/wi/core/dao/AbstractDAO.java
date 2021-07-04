package pl.edu.pb.wi.core.dao;

import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.edu.pb.wi.utils.HibernateUtil;

/**
 * Data Access Object with basic operations on given entity
 * @param <T> data transport object type
 */
public class AbstractDAO<T> {

    protected static final int FIXED_PAGE_SIZE = 10;
    protected final Class<T> entityClass;

    public AbstractDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> getResults(List<String> conditions, Map<String, Object> parameters) {
        final Session session = HibernateUtil.getSessionFactory().openSession();
        Query<T> query = session.createQuery(handleConditions(conditions, "SELECT e FROM " + entityClass.getName() + " e "), entityClass);
        for (String parameter : parameters.keySet()) {
            query.setParameter(parameter, parameters.get(parameter));
        }

        return query.getResultList();
    }

    public void saveEntity(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }

    public T getEntity(List<String> conditions, Map<String, Object> parameters) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String queryRaw = handleConditions(conditions, "SELECT e FROM " + entityClass.getName() + " e ");
            Query<T> query = session.createQuery(queryRaw, entityClass);
            for (String parameter : parameters.keySet()) {
                query.setParameter(parameter, parameters.get(parameter));
            }

            try {
                return query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

    protected int getPageNumbers(Session session) {
        String countQueryRaw = "SELECT count(e.id) FROM " + entityClass.getName() + " e";
        Query<Long> query = session.createQuery(countQueryRaw, Long.class);
        Long countResults = query.uniqueResult();

        return (int) (Math.ceil(countResults / (double) FIXED_PAGE_SIZE));
    }

    protected int getPageNumbers(Session session, List<String> conditions, Map<String, Object> parameters) {
        String countQueryRaw = "SELECT count(e.id) FROM " + entityClass.getName() + " e";
        Query<Long> query = session.createQuery(handleConditions(conditions, countQueryRaw), Long.class);
        for (String parameter : parameters.keySet()) {
            query.setParameter(parameter, parameters.get(parameter));
        }

        Long countResults = query.uniqueResult();

        return (int) (Math.ceil(countResults / (double) FIXED_PAGE_SIZE));
    }

    protected String handleConditions(List<String> conditions, String query) {
        StringBuilder queryRaw = new StringBuilder(query);
        if (conditions.size() > 0) {
            queryRaw.append(" WHERE ");
        }

        for (int i = 0, conditionsSize = conditions.size(); i < conditionsSize; i++) {
            String condition = conditions.get(i);
            queryRaw.append(condition);
            if (i + 1 < conditionsSize) {
                queryRaw.append(" AND ");
            }
        }

        return queryRaw.toString();
    }
}
