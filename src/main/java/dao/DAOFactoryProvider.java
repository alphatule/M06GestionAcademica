package dao;

import java.io.InputStream;
import java.util.Properties;

public class DAOFactoryProvider {
    private static DAOFactory factory;

    static {
        try (InputStream input = DAOFactoryProvider.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            String impl = prop.getProperty("dao.impl");

            if ("jpa".equalsIgnoreCase(impl)) {
                factory = new JPADAOFactory();
            } else {
                factory = new JDBCDAOFactory();
            }
        } catch (Exception e) {
            e.printStackTrace();
            factory = new JDBCDAOFactory(); // Fallback
        }
    }

    public static DAOFactory getFactory() {
        return factory;
    }
}
