package DataBaseMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/pointofsell";
    private static final String USER = "root";
    private static final String PSW = "Lazlo123";
           
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PSW);
    }
}
