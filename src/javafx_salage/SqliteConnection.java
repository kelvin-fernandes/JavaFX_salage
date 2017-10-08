package javafx_salage;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author kelvin-fernandes
 */
public class SqliteConnection {
    public static Connection Connector(){
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:salage.sqlite");
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
