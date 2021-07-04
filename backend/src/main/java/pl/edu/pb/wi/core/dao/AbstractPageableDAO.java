package pl.edu.pb.wi.core.dao;

import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.Query;
import pl.edu.pb.wi.core.dto.Pageable;
import pl.edu.pb.wi.utils.HibernateUtil;

/**
 * Data Access Object with basic operations on given entity
 * @param <T> data transport object type
 */
public class AbstractPageableDAO<T extends Pageable<T>> extends AbstractDAO<T> {

    public AbstractPageableDAO(Class<T> entityClass) {
        super(entityClass);
    }

    /**
     * Get page of entity objects
     * @param page requested page number, numbered from 1
     * @return page filled with returned objects
     */
    public Pageable<T> getPage(int page) throws Exception {
        final Session session = HibernateUtil.getSessionFactory().openSession();
        int pages = getPageNumbers(session);

        if (page > pages) {
            return entityClass.newInstance();
        }

        String queryRaw = "SELECT e FROM " + entityClass.getName() + " e ";
        Query<T> query = session.createQuery(queryRaw, entityClass);
        query.setFirstResult((page - 1) * FIXED_PAGE_SIZE);
        query.setMaxResults(FIXED_PAGE_SIZE);

        Pageable<T> pageable = entityClass.newInstance();
        pageable.setResults(query.getResultList());
        pageable.setPage(page);
        pageable.setPages(pages);

        return pageable;
    }

    /**
     * Get page of entity objects with additional conditions
     * @param page requested page number, numbered from 1
     * @param conditions list of condition in string (connected with AND, to create OR please write it as one condition)
     * @apiNote Please use "e" as name of object from entity table in PQL
     * @return page filled with returned objects
     */
    public Pageable<T> getPage(int page, List<String> conditions, Map<String, Object> parameters)
            throws IllegalAccessException, InstantiationException {
        final Session session = HibernateUtil.getSessionFactory().openSession();
        int pages = getPageNumbers(session, conditions, parameters);

        if (page > pages) {
            return entityClass.newInstance();
        }

        Query<T> query = session.createQuery(handleConditions(conditions, "SELECT e FROM " + entityClass.getName() + " e "), entityClass);
        query.setFirstResult((page - 1) * FIXED_PAGE_SIZE);
        query.setMaxResults(FIXED_PAGE_SIZE);
        for (String parameter : parameters.keySet()) {
            query.setParameter(parameter, parameters.get(parameter));
        }

        Pageable<T> pageable = entityClass.newInstance();
        pageable.setResults(query.getResultList());
        pageable.setPage(page);
        pageable.setPages(pages);

        return pageable;
    }
}
