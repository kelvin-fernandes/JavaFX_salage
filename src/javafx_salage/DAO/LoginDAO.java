package javafx_salage.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx_salage.SqliteConnectionFactory;

/**
 *
 * @author kelvin-fernandes
 */
public class LoginDAO {
    
    private Connection connection;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public LoginDAO(){
        connection = SqliteConnectionFactory.Connector();
        if(connection == null)
            System.exit(1);
    }
    
    public boolean isDbConnected(){
        try{
            return !connection.isClosed();
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLogin(String user, String pass) throws SQLException{
        String query = "SELECT * FROM usuario WHERE login_usu = ? AND senha_usu = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        finally {
            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
    }
}
