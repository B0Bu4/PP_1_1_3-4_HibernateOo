package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    // --------------------Util for JDBC--------------------------
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/ppkata";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // --------------------Util for Hibernate--------------------------


    private static SessionFactory sessionFactory;
    private static Util instance;

    private Util() {

    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }


    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {

                Properties prop = new Properties();

                prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/ppkata");

                prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");

                prop.setProperty("hibernate.connection.username", "root");
                prop.setProperty("hibernate.connection.password", "1234");
                prop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                prop.setProperty("show_sql", "true");


                sessionFactory = new Configuration().addAnnotatedClass(User.class).addProperties(prop).buildSessionFactory();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
