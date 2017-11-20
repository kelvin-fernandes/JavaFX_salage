package javafx_salage.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx_salage.SqliteConnectionFactory;
import javafx_salage.model.Usuario;

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
        }
    }

    public Usuario getUsuario(String user) throws SQLException{
        String query = "SELECT * FROM usuario WHERE login_usu = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);

            resultSet = preparedStatement.executeQuery();

            return new Usuario( resultSet.getString("login_usu"),
                                resultSet.getString("senha_usu"),
                                resultSet.getInt("id_ace"));
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            preparedStatement.close();
            resultSet.close();
        }
    }
}
