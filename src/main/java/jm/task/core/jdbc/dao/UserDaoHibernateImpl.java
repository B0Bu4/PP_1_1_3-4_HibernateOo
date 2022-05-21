package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory;
    private Transaction transaction;

    public UserDaoHibernateImpl() {
        Util util = Util.getInstance();
        sessionFactory = util.getSessionFactory();
    }
    @Override
    public void createUsersTable() {

        try (Session session = sessionFactory.openSession()){

            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users ( id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    " name VARCHAR(45), lastName VARCHAR(45), age INT)").addEntity(User.class).executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {

            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = sessionFactory.openSession()){

            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class).executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {

            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = sessionFactory.openSession()){

            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();

        } catch (HibernateException e) {

            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = sessionFactory.openSession()){

            transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE User WHERE id = :ID");
            query.setParameter("ID", id);
            query.executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {

            transaction.rollback();
            e.printStackTrace();

        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = null;

        try (Session session = sessionFactory.openSession()){

            transaction = session.beginTransaction();
            users = session.createQuery("From User").list();

        } catch (HibernateException e) {

            transaction.rollback();
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = sessionFactory.openSession()){

            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {

            transaction.rollback();
            e.printStackTrace();
        }
    }
}
