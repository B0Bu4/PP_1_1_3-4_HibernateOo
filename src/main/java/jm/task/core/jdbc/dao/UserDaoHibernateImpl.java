package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        Util util = Util.getInstance();
        sessionFactory = util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users ( id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    " name VARCHAR(45), lastName VARCHAR(45), age INT)").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = sessionFactory.openSession()) {

            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            Query query = session.createQuery("DELETE User WHERE id = :ID");
            query.setParameter("ID", id);
            query.executeUpdate();
            session.getTransaction();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = null;

        try (Session session = sessionFactory.openSession()) {

            users = session.createQuery("From User").list();

            for (User user : users) {
                System.out.println(user);
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.createSQLQuery("TRUNCATE users").executeUpdate();
            session.getTransaction();

        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }
}
