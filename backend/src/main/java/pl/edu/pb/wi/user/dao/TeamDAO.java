package pl.edu.pb.wi.user.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.edu.pb.wi.core.dao.AbstractDAO;
import pl.edu.pb.wi.user.dto.TeamData;
import pl.edu.pb.wi.utils.HibernateUtil;

public class TeamDAO extends AbstractDAO<TeamData> {
    public TeamDAO() {
        super(TeamData.class);
    }

    public List<TeamData> getUserTeam(int user) {
        List<String> conditions = Collections.singletonList("e.user = :userId");
        Map<String, Object> parameters = Collections.singletonMap("userId", user);
        return getResults(conditions, parameters);
    }

    public void removeUserTeam(int userId, int dex) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM TeamData e WHERE e.pokemon = :dex AND e.user = :user");
            query.setParameter("user", userId);
            query.setParameter("dex", dex);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }
}
