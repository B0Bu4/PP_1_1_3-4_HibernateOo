package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {

      UserDao userDao = new UserDaoHibernateImpl();



        userDao.createUsersTable();

        userDao.saveUser("Ivan", "Petrov", (byte) 33);
        userDao.saveUser("Sergey", "Ivanov", (byte) 29);
        userDao.saveUser("Nataliya", "Petrova", (byte) 31);
        userDao.saveUser("Marina", "Ivanova", (byte) 28);

        userDao.getAllUsers();
         userDao.removeUserById(1);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.getAllUsers();
        userDao.dropUsersTable();



    }
}
