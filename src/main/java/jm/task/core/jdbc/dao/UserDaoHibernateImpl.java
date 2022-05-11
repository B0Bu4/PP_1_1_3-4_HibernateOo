package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {

        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users ( id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                " name VARCHAR(45), lastName VARCHAR(45), age INT)").addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
        session.close();


    }

    @Override
    public void dropUsersTable() {

        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("delete User where id = :ID");
        query.setParameter("ID", id);
        query.executeUpdate();
        session.getTransaction();
        session.close();

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = (List<User>) Util.getSessionFactory().openSession().createQuery("From User").list();
        for (User user: users){
            System.out.println(user);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE users").executeUpdate();
        session.getTransaction();
        session.close();
    }
}
