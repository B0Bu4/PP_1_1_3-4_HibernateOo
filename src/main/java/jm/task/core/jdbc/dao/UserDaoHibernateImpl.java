package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        Util util = Util.getInstance();
        sessionFactory = util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {

        Session session = sessionFactory.openSession();

        try {

            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users ( id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    " name VARCHAR(45), lastName VARCHAR(45), age INT)").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();

        } catch (HibernateException e) {

            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            try {
                if (session.isOpen()) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void dropUsersTable() {

        Session session = sessionFactory.openSession();

        try {

            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();

        } catch (HibernateException e) {

            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            try {
                if (session.isOpen()) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Session session = sessionFactory.openSession();

        try {

            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

        } catch (HibernateException e) {

            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            try {
                if (session.isOpen()) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        Session session = sessionFactory.openSession();

        try {

            session.beginTransaction();
            Query query = session.createQuery("DELETE User WHERE id = :ID");
            query.setParameter("ID", id);
            query.executeUpdate();
            session.getTransaction();

        } catch (HibernateException e) {

            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            try {
                if (session.isOpen()) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public List<User> getAllUsers() {

        Session session = sessionFactory.openSession();

        List<User> users = null;

        try {

            users = session.createQuery("From User").list();

        } catch (HibernateException e) {

            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            try {

                if (session.isOpen()) {
                    session.close();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return users;
    }

    @Override
    public void cleanUsersTable() {

        Session session = sessionFactory.openSession();

        try {

            session.beginTransaction();
            session.createSQLQuery("TRUNCATE users").executeUpdate();
            session.getTransaction();

        } catch (HibernateException e) {

            session.getTransaction().rollback();
            e.printStackTrace();

        } finally {

            try {

                if (session.isOpen()) {
                    session.close();
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}
