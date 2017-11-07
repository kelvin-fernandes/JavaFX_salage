package javafx_salage.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx_salage.SqliteConnectionFactory;
import javafx_salage.model.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorDAO {
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ProfessorDAO(){
        connection = SqliteConnectionFactory.Connector();
        if(connection == null)
            System.exit(1);
    }

    public boolean isProfessor(String rgf) throws SQLException{
        String query = "SELECT * FROM professor WHERE rgf_prof = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, rgf);
            
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

    public void add(String rgf, String nome) throws SQLException{
        String query = "INSERT INTO professor VALUES (?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, rgf);
            preparedStatement.setString(2, nome);
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

    public void delete(String rgf) throws SQLException{
        String query = "DELETE FROM professor WHERE rgf_prof = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, rgf);
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

    public Professor find(String prof) throws SQLException{
        String query = "SELECT * FROM professor WHERE rgf_prof = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, prof);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return new Professor(
                        resultSet.getString("rgf_prof"),
                        resultSet.getString("nome_prof"));
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

    public void update(String rgf, String nome) throws SQLException{
        String query1 = "UPDATE professor SET nome_prof = ? WHERE rgf_prof = ?";

        try {
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, rgf);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public ObservableList<Professor> getAll() throws SQLException {
        ObservableList<Professor> professorsData = FXCollections.observableArrayList();
        String query = "Select * from professor";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                professorsData.add(new Professor(
                        resultSet.getString("rgf_prof"),
                        resultSet.getString("nome_prof")
                ));
            }
            return professorsData;
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
