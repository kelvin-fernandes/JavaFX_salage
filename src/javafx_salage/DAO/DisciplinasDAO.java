package javafx_salage.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx_salage.SqliteConnectionFactory;
import javafx_salage.model.Disciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DisciplinasDAO {
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public DisciplinasDAO(){
        connection = SqliteConnectionFactory.Connector();
        if(connection == null)
            System.exit(1);
    }

    public boolean isDisciplina(String disc) throws SQLException{
        String query = "SELECT * FROM disciplina WHERE id_disc = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, disc);

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

    public void add(String disc, String nome, Integer qtd) throws SQLException{
        String query = "INSERT INTO disciplina VALUES (?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, disc);
            preparedStatement.setString(2, nome);
            preparedStatement.setInt(3, qtd);
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

    public void delete(String disc) throws SQLException{
        String query = "DELETE FROM disciplina WHERE id_disc = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, disc);
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

    public Disciplina find(String usu) throws SQLException{
        String query = "SELECT * FROM disciplina WHERE id_disc = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usu);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return new Disciplina(
                        resultSet.getString("id_disc"),
                        resultSet.getString("nome_turma"),
                        resultSet.getInt("qtd_alunos"));
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

    public void update(String disc, String nome, Integer qtd) throws SQLException{
        String query1 = "UPDATE disciplina SET nome_turma = ? WHERE id_disc = ?";
        String query2 = "UPDATE disciplina SET qtd_alunos = ? WHERE id_disc = ?";

        try {
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, disc);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setInt(1, qtd);
            preparedStatement.setString(2, disc);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public ObservableList<Disciplina> getAll() throws SQLException {
        ObservableList<Disciplina> disciplinasData = FXCollections.observableArrayList();
        String query = "Select * from disciplina";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                disciplinasData.add(new Disciplina(
                        resultSet.getString("id_disc"),
                        resultSet.getString("nome_turma"),
                        resultSet.getInt("qtd_alunos")
                ));
            }
            return disciplinasData;
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
