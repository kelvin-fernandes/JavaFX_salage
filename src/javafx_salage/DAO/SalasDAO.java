package javafx_salage.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx_salage.SqliteConnectionFactory;
import javafx_salage.model.Sala;
import javafx_salage.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalasDAO {
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public SalasDAO(){
        connection = SqliteConnectionFactory.Connector();
        if(connection == null)
            System.exit(1);
    }

    public boolean isSala(Integer num) throws SQLException{
        String query = "SELECT * FROM sala WHERE numero_sala = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, num);

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

    public void add(Integer num, String desc, Integer cap) throws SQLException{
        String query = "INSERT INTO sala VALUES (?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, num);
            preparedStatement.setString(2, desc);
            preparedStatement.setInt(3, cap);
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

    public void delete(Integer num) throws SQLException{
        String query = "DELETE FROM sala WHERE numero_sala = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, num);
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

    public Sala find(Integer num) throws SQLException{
        String query = "SELECT * FROM sala WHERE numero_sala = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, num);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return new Sala(
                        resultSet.getInt("numero_sala"),
                        resultSet.getString("descricao_sala"),
                        resultSet.getInt("capacidade_sala"));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public void update(Integer num, String desc, Integer cap) throws SQLException{
        String query1 = "UPDATE sala SET descricao_sala = ? WHERE numero_sala = ?";
        String query2 = "UPDATE sala SET capacidade_sala = ? WHERE numero_sala = ?";

        try {
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1, desc);
            preparedStatement.setInt(2, num);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setInt(1, cap);
            preparedStatement.setInt(2, num);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public ObservableList<Sala> getAll() throws SQLException {
        ObservableList<Sala> salasData = FXCollections.observableArrayList();
        String query = "Select * from sala";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                salasData.add(new Sala(
                        resultSet.getInt("numero_sala"),
                        resultSet.getString("descricao_sala"),
                        resultSet.getInt("capacidade_sala")
                ));
            }
            return salasData;
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
