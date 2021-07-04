package pl.edu.pb.wi.utils;

import java.util.Properties;
import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import pl.edu.pb.wi.pokemon.dto.EvolutionData;
import pl.edu.pb.wi.pokemon.dto.PokemonData;
import pl.edu.pb.wi.user.dto.Role;
import pl.edu.pb.wi.user.dto.TeamData;
import pl.edu.pb.wi.user.dto.UserData;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.sqlite.JDBC");
                settings.put(Environment.URL, "jdbc:sqlite:pokemon.sqlite");
                settings.put(Environment.USER, "");
                settings.put(Environment.PASS, "");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.SQLiteDialect");

                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-only");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(PokemonData.class);
                configuration.addAnnotatedClass(EvolutionData.class);
                configuration.addAnnotatedClass(UserData.class);
                configuration.addAnnotatedClass(TeamData.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                createAdministrator(sessionFactory.openSession());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }

    private static void createAdministrator(Session session) {
        boolean isAdminCreated = getAdmin(session) != null;
        if (!isAdminCreated) {
            UserData admin = new UserData();
            admin.setLogin("admin");
            admin.setPassword("admin");
            admin.setRole(Role.ADMINISTRATOR);

            Transaction transaction = session.beginTransaction();
            session.save(admin);
            transaction.commit();
        }
    }

    private static UserData getAdmin(Session session) {
        Query<UserData> query = session.createQuery("SELECT e FROM UserData e WHERE e.role = 1", UserData.class);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
