package javafx_salage.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx_salage.DAO.TelaPrincipalDAO;
import javafx_salage.model.*;
import javafx_salage.viewmodel.EnsalamentoVM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TelaPrincipalController implements Initializable{

    @FXML
    private TableView<EnsalamentoVM> tableEnsalamento;

    @FXML
    private JFXComboBox<Sala> cbSala;

    @FXML
    private JFXDatePicker dtInicio;

    @FXML
    private JFXDatePicker dtFinal;

    @FXML
    private JFXComboBox<Disciplina> cbDisciplina;

    @FXML
    private JFXComboBox<Professor> cbProfessor;

    @FXML
    private TableColumn<Sala, Integer> colSala;

    @FXML
    private TableColumn<Ensalamento, LocalDate> colDataInicio;

    @FXML
    private TableColumn<Ensalamento, LocalDate> colDataFinal;

    @FXML
    private TableColumn<Disciplina, String> colDisciplina;

    @FXML
    private TableColumn<Professor, String> colProfessor;

    private TelaPrincipalDAO telaPrincipalDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            telaPrincipalDAO = new TelaPrincipalDAO();
            tableEnsalamento.getItems().clear();
            colSala.setCellValueFactory(new PropertyValueFactory<>("numero_sala"));
            colDataInicio.setCellValueFactory(new PropertyValueFactory<>("data_inicio"));
            colDataFinal.setCellValueFactory(new PropertyValueFactory<>("data_inicio"));
            colDisciplina.setCellValueFactory(new PropertyValueFactory<>("nome_turma"));
            colProfessor.setCellValueFactory(new PropertyValueFactory<>("nome_prof"));

            ObservableList<EnsalamentoVM> ensalamentos = telaPrincipalDAO.getDaily();
            //addTableListener();
            tableEnsalamento.setItems(ensalamentos);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
