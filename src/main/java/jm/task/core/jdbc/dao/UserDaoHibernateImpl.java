package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Properties;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(45),
                lastName VARCHAR(45),
                age INT)
                """;
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery(sql).addEntity(User.class).executeUpdate();;
        session.getTransaction().commit();
        session.close();
        System.out.println("Таблица users успешно создана");

    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
        session.close();
        System.out.println("Таблица users удалена");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("delete User where id = :ID");
        query.setParameter("ID", id);

        int result = query.executeUpdate();
        session.getTransaction();
        if(result==1){
            System.out.println("User чей ID = "+id+" успешно удален");
        }

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
        session.createQuery("DELETE FROM User").executeUpdate();
        session.getTransaction();
        session.close();
    }
}
