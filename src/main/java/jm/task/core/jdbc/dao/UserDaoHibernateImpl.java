package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        Util.configureHibernate();
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<?> nativeQuery = session.createNativeQuery("""
                                CREATE TABLE IF NOT EXISTS users
                                (
                                    id INT8 PRIMARY KEY AUTO_INCREMENT,
                                    name VARCHAR(128),
                                    last_name VARCHAR(128),
                                    age TINYINT
                                )
                """);
        nativeQuery.executeUpdate();
        transaction.commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<?> nativeQuery = session.createNativeQuery("DROP TABLE IF EXISTS users");
        nativeQuery.executeUpdate();
        transaction.commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.persist(new User(name, lastName, age));
        transaction.commit();
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        User user = new User();
        user.setId(id);
        session.remove(user);
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<User> allUsers = sessionFactory.getCurrentSession().createQuery("from User", User.class).list();
        transaction.commit();
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        transaction.commit();
    }
}
