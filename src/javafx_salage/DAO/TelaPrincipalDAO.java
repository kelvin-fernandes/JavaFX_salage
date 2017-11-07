package javafx_salage.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx_salage.SqliteConnectionFactory;
import javafx_salage.model.*;
import javafx_salage.model.Ensalamento;
import javafx_salage.viewmodel.EnsalamentoVM;

import java.sql.*;
import java.time.LocalDate;

public class TelaPrincipalDAO {
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private Ensalamento ensalamento;
    private Sala sala;
    private Disciplina disciplina;
    private Professor professor;

    public TelaPrincipalDAO(){
        connection = SqliteConnectionFactory.Connector();
        if(connection == null)
            System.exit(1);
    }
    
    public ObservableList<EnsalamentoVM> getDaily() throws  SQLException{
        ObservableList<EnsalamentoVM> dailyData = FXCollections.observableArrayList();
        final String query =    "SELECT * FROM ensalamento e" +
                                " JOIN sala s ON e.numero_sala = s.numero_sala" +
                                " JOIN disciplina d ON e.id_disc = d.id_disc" +
                                " JOIN professor p ON e.rgf_prof = p.rgf_prof" +
                                "WHERE e.data_inicio = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(LocalDate.now()));

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ensalamento = new Ensalamento(  resultSet.getDate("data_inicio").toLocalDate(),
                                                resultSet.getInt("numero_sala"),
                                                resultSet.getString("rgf_prof"),
                                                resultSet.getString("id_disc"));
                sala = new Sala(    resultSet.getInt("numero_sala"),
                                    resultSet.getString("descricao_sala"),
                                    resultSet.getInt("capacidade_sala"));
                disciplina = new Disciplina(resultSet.getString("id_disc"),
                                            resultSet.getString("nome_turma"),
                                            resultSet.getInt("qtd_alunos"));
                professor = new Professor(resultSet.getString("rgf_prof"),
                                            resultSet.getString("nome_prof"));

                dailyData.add(new EnsalamentoVM(ensalamento, sala, disciplina, professor));
            }

            return dailyData;
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

    public ObservableList<Ensalamento> getAll() throws SQLException {
        ObservableList<Ensalamento> disciplinasData = FXCollections.observableArrayList();
        String query = "Select * from disciplina";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
//                disciplinasData.add(new Ensalamento(
//
//                ));
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
