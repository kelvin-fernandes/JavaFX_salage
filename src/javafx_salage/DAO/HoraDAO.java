package javafx_salage.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx_salage.SqliteConnectionFactory;
import javafx_salage.model.Hora;

import java.sql.*;
import java.time.LocalTime;

public class HoraDAO {
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public HoraDAO(){
        connection = SqliteConnectionFactory.Connector();
        if(connection == null)
            System.exit(1);
    }

    public boolean isHora(String hora) throws SQLException{
        String query = "SELECT * FROM hora WHERE hora_inicio = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, hora);
            
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

    public void add(String hora) throws SQLException{
        String query = "INSERT INTO hora VALUES (?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, hora);
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

    public void delete(String hora) throws SQLException{
        String query = "DELETE FROM hora WHERE hora_inicio = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, hora);
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

    public Hora find(String hora) throws SQLException{
        String query = "SELECT * FROM hora WHERE hora_inicio = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, hora);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return new Hora(
                        resultSet.getString("hora_inicio"));
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

    public ObservableList<Hora> getAll() throws SQLException {
        ObservableList<Hora> horasData = FXCollections.observableArrayList();
        String query = "Select * from hora";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                horasData.add(new Hora(
                        resultSet.getString("hora_inicio")
                ));
            }
            return horasData;
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
