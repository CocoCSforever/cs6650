package dbManager;

import org.apache.commons.dbcp2.*;

public class DBCPDataSource {
    private static BasicDataSource dataSource;
    private static final String HOST_NAME = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "LiftRides";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root@yijiama";
    // NEVER store sensitive information below in plain text!
//    private static final String HOST_NAME = System.getProperty("MySQL_IP_ADDRESS");
//    private static final String PORT = System.getProperty("MySQL_PORT");
//    private static final String DATABASE = "LiftRides";
//    private static final String USERNAME = System.getProperty("DB_USERNAME");
//    private static final String PASSWORD = System.getProperty("DB_PASSWORD");

    static {
        System.out.println(HOST_NAME);
        System.out.println(PORT);
        // https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-jdbc-url-format.html
        dataSource = new BasicDataSource();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC", HOST_NAME, PORT, DATABASE);
        dataSource.setUrl(url);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setInitialSize(10);
        dataSource.setMaxTotal(60);
    }

    public static BasicDataSource getDataSource() {
        return dataSource;
    }
}
