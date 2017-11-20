package javafx_salage.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx_salage.SqliteConnectionFactory;
import javafx_salage.model.*;
import javafx_salage.model.Ensalamento;
import javafx_salage.viewmodel.EnsalamentoVM;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class EnsalamentoDAO {
    private static Connection connection = null;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public EnsalamentoDAO() {
        connection = SqliteConnectionFactory.Connector();
        if (connection == null)
            System.exit(1);
    }

    public static boolean existeEnsalamento(Ensalamento e) throws SQLException{
        String query =  "SELECT * FROM ensalamento" +
                        " WHERE data_inicio = ?" +
                        " AND numero_sala = ?" +
                        " AND hora_inicio = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, e.getData_inicio());
            preparedStatement.setInt(2, e.getNumero_sala());
            preparedStatement.setString(3, e.getHora_inicio());
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (Exception el){
            el.printStackTrace();
            return true;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public ObservableList<EnsalamentoVM> getDaily() throws SQLException {
        ObservableList<EnsalamentoVM> dailyData = FXCollections.observableArrayList();
        final String query ="SELECT * FROM ensalamento e" +
                            " JOIN sala s ON e.numero_sala = s.numero_sala" +
                            " JOIN disciplina d ON e.id_disc = d.id_disc" +
                            " JOIN professor p ON e.rgf_prof = p.rgf_prof" +
                            " WHERE e.data_inicio = ?" +
                            " ORDER BY id_ensal ASC";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, LocalDate.now().toString());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                dailyData.add(new EnsalamentoVM(resultSet.getInt("id_ensal"),
                                                resultSet.getString("data_inicio"),
                                                resultSet.getString("hora_inicio"),
                                                resultSet.getInt("numero_sala"),
                                                resultSet.getString("descricao_sala"),
                                                resultSet.getString("id_disc"),
                                                resultSet.getString("nome_turma"),
                                                resultSet.getString("rgf_prof"),
                                                resultSet.getString("nome_prof")));
            }

            return dailyData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public static ObservableList<Sala> getSalas() throws SQLException {
        ObservableList<Sala> salasData = FXCollections.observableArrayList();
        String query = "SELECT * FROM sala";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                salasData.add(new Sala(
                        resultSet.getInt("numero_sala"),
                        resultSet.getString("descricao_sala"),
                        resultSet.getInt("capacidade_sala")
                ));
            }
            return salasData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public static ObservableList<Disciplina> getDisciplinas() throws SQLException {
        ObservableList<Disciplina> disciplinaData = FXCollections.observableArrayList();
        String query = "SELECT * FROM disciplina";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                disciplinaData.add(new Disciplina(
                        resultSet.getString("id_disc"),
                        resultSet.getString("nome_turma"),
                        resultSet.getInt("qtd_alunos")
                ));
            }
            return disciplinaData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public static ObservableList<Professor> getProfessores() throws SQLException {
        ObservableList<Professor> professoresData = FXCollections.observableArrayList();
        String query = "SELECT * FROM professor";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                professoresData.add(new Professor(
                        resultSet.getString("rgf_prof"),
                        resultSet.getString("nome_prof")
                ));
            }
            return professoresData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public static ObservableList<Hora> getHoras() throws SQLException{
        ObservableList<Hora> horasData = FXCollections.observableArrayList();
        String query = "SELECT * FROM hora";

        try{
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
                horasData.add(new Hora(resultSet.getString("hora_inicio")));

            return horasData;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public static ObservableList<EnsalamentoVM>  getHorasWId_ensal() throws SQLException{
        ObservableList<EnsalamentoVM> horasIdEnsalData = FXCollections.observableArrayList();
        String query = "SELECT DISTINCT id_ensal, hora_inicio FROM ensalamento";

        try{
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                horasIdEnsalData.add(new EnsalamentoVM(resultSet.getInt("id_ensal"), resultSet.getString("hora_inicio")));

            return horasIdEnsalData;
        } catch (SQLException sql){
            sql.printStackTrace();
            return null;
        } finally {
            preparedStatement.close();
            preparedStatement.close();
        }
    }

    public static Integer getMaxIdEnsalamento() throws SQLException {
        String query = "SELECT MAX(id_ensal) FROM ensalamento";

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

             return resultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public static void realizarEnsalamentos(Ensalamento e, List<Boolean> dias) throws SQLException{
        LocalDate diaOriginal = LocalDate.parse(e.getData_inicio());
        for(LocalDate dia = LocalDate.parse(e.getData_inicio()); dia.isBefore(LocalDate.parse(e.getData_final())) || dia.isEqual(LocalDate.parse(e.getData_final())); dia = dia.plusDays(1)) {
            if(dias.get(0) && dia.getDayOfWeek() == DayOfWeek.MONDAY)
                adicionarEnsalamento(e, dia.toString());
            else if(dias.get(1) && dia.getDayOfWeek() == DayOfWeek.TUESDAY)
                adicionarEnsalamento(e, dia.toString());
            else if(dias.get(2) && dia.getDayOfWeek() == DayOfWeek.WEDNESDAY)
                adicionarEnsalamento(e, dia.toString());
            else if(dias.get(3) && dia.getDayOfWeek() == DayOfWeek.THURSDAY)
                adicionarEnsalamento(e, dia.toString());
            else if(dias.get(4) && dia.getDayOfWeek() == DayOfWeek.FRIDAY)
                adicionarEnsalamento(e, dia.toString());
            else if(dias.get(5) && dia.getDayOfWeek() == DayOfWeek.SATURDAY)
                adicionarEnsalamento(e, dia.toString());
        }
        e.setData_inicio(diaOriginal.toString());
    }

    private static void adicionarEnsalamento(Ensalamento e, String dia){
        try {
            e.setData_inicio(dia);
            add(e);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private static void add(Ensalamento e) throws SQLException {
        String query = "INSERT INTO ensalamento VALUES" +
                "(?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, e.getId_ensal());
            preparedStatement.setString(2, e.getData_inicio());
            preparedStatement.setInt(3, e.getNumero_sala());
            preparedStatement.setString(4, e.getHora_inicio());
            preparedStatement.setString(5, e.getRgf_prof());
            preparedStatement.setString(6, e.getId_disc());

            preparedStatement.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            preparedStatement.close();
        }
    }

    public static void delete(EnsalamentoVM ensalamentoVM) throws SQLException{
        final String query = "DELETE FROM ensalamento" +
                            " WHERE id_ensal = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ensalamentoVM.getId_ensal());

            preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            preparedStatement.close();
        }
    }

    public static void update(EnsalamentoVM ensalamentoVM) throws SQLException{
        String query = "UPDATE ensalamento SET id_disc = ?, rgf_prof = ? WHERE id_ensal = ?";
        //String query2 = "UPDATE ensalamento SET rgf_prof = ? WHERE id_ensal = ?";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ensalamentoVM.getId_disc());
            preparedStatement.setString(2, ensalamentoVM.getRgf_prof());
            preparedStatement.setInt(3, ensalamentoVM.getId_ensal());
            preparedStatement.executeUpdate();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            preparedStatement.close();
        }
    }

    public ObservableList<EnsalamentoVM> getEnsalamentoFilters(Ensalamento e) throws SQLException{
        ObservableList<EnsalamentoVM> allData = FXCollections.observableArrayList();
        String query =  "SELECT * FROM ensalamento e" +
                " JOIN sala s ON e.numero_sala = s.numero_sala" +
                " JOIN disciplina d ON e.id_disc = d.id_disc" +
                " JOIN professor p ON e.rgf_prof = p.rgf_prof";
        try {
            if(e.getNumero_sala() != 0 && e.getRgf_prof() != null)
                 query +=   " WHERE e.numero_sala = ?" +
                            " AND e.rgf_prof = ?" +
                            " AND e.data_inicio = ?" +
                            " ORDER BY id_ensal ASC";
            else if(e.getNumero_sala() != 0){
                query += " WHERE e.numero_sala = ?" +
                         " AND e.data_inicio = ?" +
                         " ORDER BY id_ensal ASC";
            }
            else if(e.getRgf_prof() != null){
                query += " WHERE e.rgf_prof = ?" +
                         " AND e.data_inicio = ?" +
                         " ORDER BY id_ensal ASC";
            }
            else{
                query += " WHERE e.data_inicio = ?" +
                         " ORDER BY id_ensal ASC";
            }

            preparedStatement = connection.prepareStatement(query);

            if(e.getNumero_sala() != 0 && e.getRgf_prof() != null){
                preparedStatement.setInt(1, e.getNumero_sala());
                preparedStatement.setString(2, e.getRgf_prof());
                preparedStatement.setString(3, e.getData_inicio());
            }
            else if(e.getNumero_sala() != 0) {
                preparedStatement.setInt(1, e.getNumero_sala());
                preparedStatement.setString(2, e.getData_inicio());
            }
            else if(e.getRgf_prof() != null) {
                preparedStatement.setString(1, e.getRgf_prof());
                preparedStatement.setString(2, e.getData_inicio());
            }
            else{
                preparedStatement.setString(1, e.getData_inicio());
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                allData.add(new EnsalamentoVM(resultSet.getInt("id_ensal")));
            }
            return allData;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public ObservableList<EnsalamentoVM> getAllEnsalamentoFilters(Ensalamento e) throws SQLException{
        ObservableList<EnsalamentoVM> allDataFilters = getEnsalamentoFilters(e);
        ObservableList<EnsalamentoVM> allData = FXCollections.observableArrayList();
        try {
            for (EnsalamentoVM evm : allDataFilters) {
                String query =  "SELECT * FROM ensalamento e" +
                                " JOIN sala s ON e.numero_sala = s.numero_sala" +
                                " JOIN disciplina d ON e.id_disc = d.id_disc" +
                                " JOIN professor p ON e.rgf_prof = p.rgf_prof"+
                                " WHERE id_ensal = ?";

                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, evm.getId_ensal());

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    allData.add(new EnsalamentoVM(resultSet.getInt("id_ensal"),
                            resultSet.getString("data_inicio"),
                            resultSet.getString("hora_inicio"),
                            resultSet.getInt("numero_sala"),
                            resultSet.getString("descricao_sala"),
                            resultSet.getString("id_disc"),
                            resultSet.getString("nome_turma"),
                            resultSet.getString("rgf_prof"),
                            resultSet.getString("nome_prof")));
                }
            }
            return allData;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public ObservableList<EnsalamentoVM> getAll() throws SQLException {
        ObservableList<EnsalamentoVM> allData = FXCollections.observableArrayList();
        final String query =    "SELECT * FROM ensalamento e" +
                                " JOIN sala s ON e.numero_sala = s.numero_sala" +
                                " JOIN disciplina d ON e.id_disc = d.id_disc" +
                                " JOIN professor p ON e.rgf_prof = p.rgf_prof" +
                                " ORDER BY id_ensal ASC";

        try {
            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                allData.add(new EnsalamentoVM(resultSet.getInt("id_ensal"),
                                                resultSet.getString("data_inicio"),
                                                resultSet.getString("hora_inicio"),
                                                resultSet.getInt("numero_sala"),
                                                resultSet.getString("descricao_sala"),
                                                resultSet.getString("id_disc"),
                                                resultSet.getString("nome_turma"),
                                                resultSet.getString("rgf_prof"),
                                                resultSet.getString("nome_prof")));
            }

            return allData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }
}