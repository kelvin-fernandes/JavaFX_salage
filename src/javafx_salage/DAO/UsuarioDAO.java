package javafx_salage.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx_salage.SqliteConnectionFactory;
import javafx_salage.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(){
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

//    public boolean isLogin(String user, String pass) throws SQLException {
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        String query = "Select * from usuario where login_usu = ? and senha_usu = ?";
//
//        try {
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, user);
//            preparedStatement.setString(2, pass);
//
//            resultSet = preparedStatement.executeQuery();
//
//            return resultSet.next();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        finally {
//            preparedStatement.close();
//            resultSet.close();
//        }
//    }

    public ObservableList<Usuario> getAll() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ObservableList usuarios = FXCollections.observableArrayList();
        String query = "Select * from usuario";

        try {
            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Usuario u = new Usuario(resultSet.getString("login_usu"),
                                        resultSet.getString("senha_usu"),
                                        resultSet.getInt("id_ace"));
                usuarios.add(u);
            }
            return usuarios;
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
