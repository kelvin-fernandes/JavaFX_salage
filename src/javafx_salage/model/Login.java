package javafx_salage.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx_salage.SqliteConnection;

/**
 *
 * @author kelvin-fernandes
 */
public class Login {
    
    private Connection connection;
    
    public Login(){
        connection = SqliteConnection.Connector();
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
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "Select * from usuario where login_usu = ? and senha_usu = ?";

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
        }
    }
}
