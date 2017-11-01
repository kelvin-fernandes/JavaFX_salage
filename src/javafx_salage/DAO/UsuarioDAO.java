package javafx_salage.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx_salage.SqliteConnectionFactory;
import javafx_salage.model.Usuario;
import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public UsuarioDAO(){
        connection = SqliteConnectionFactory.Connector();
        if(connection == null)
            System.exit(1);
    }

    public boolean isUsuario(String user) throws SQLException{
        String query = "SELECT * FROM usuario WHERE login_usu = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);

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

    public void add(String user, String pass, int adm) throws SQLException{
        String query = "INSERT INTO usuario VALUES (?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            preparedStatement.setInt(3, adm);
            preparedStatement.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public void delete(String user) throws SQLException{
        String query = "DELETE FROM usuario WHERE login_usu = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public Usuario find(String usu){
        String query = "SELECT * FROM usuario WHERE login_usu = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usu);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                return new Usuario(
                        resultSet.getString("login_usu"),
                        resultSet.getString("senha_usu"),
                        resultSet.getInt("id_ace"));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(String user, String pass, int ace){
        String query1 = "UPDATE usuario SET senha_usu = ? WHERE login_usu = ?";
        String query2 = "UPDATE usuario SET id_ace = ? WHERE login_usu = ?";

        try {
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1, pass);
            preparedStatement.setString(2, user);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setInt(1, ace);
            preparedStatement.setString(2, user);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Usuario> getAll() throws SQLException {
        ObservableList<Usuario> usuariosData = FXCollections.observableArrayList();
        String query = "Select * from usuario";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                usuariosData.add(new Usuario(
                        resultSet.getString("login_usu"),
                        resultSet.getString("senha_usu"),
                        resultSet.getInt("id_ace")
                ));
            }
            return usuariosData;
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
