package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

public class Main {
  public static void main(String[] args) {

    UserDao userDao = new UserDaoHibernateImpl();

    userDao.createUsersTable();

    userDao.saveUser("Ivan", "Petrov", (byte) 33);
    userDao.saveUser("Sergey", "Ivanov", (byte) 29);
    userDao.saveUser("Nataliya", "Petrova", (byte) 31);
    userDao.saveUser("Marina", "Ivanova", (byte) 28);

    for (User user : userDao.getAllUsers()) {
      System.out.println(user);
    }

    userDao.removeUserById(1);

    userDao.cleanUsersTable();

    userDao.dropUsersTable();

  }
}
