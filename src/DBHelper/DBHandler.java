package DBHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHandler extends DBConfig{
    Connection connection;

    public Connection getConnection() throws SQLException {
        String connectionURL ="jdbc:mysql://" + dbHost +":"
                + dbPort +"/"
                + dbName;

        connection = DriverManager.getConnection(connectionURL, dbUser, dbPass);
        return connection;
    }
}
