package javafx_salage.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx_salage.DAO.EnsalamentoDAO;
import javafx_salage.viewmodel.EnsalamentoVM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class DiarioController implements Initializable{

    @FXML
    private TableView<EnsalamentoVM> tableEnsalamento;

    @FXML
    private TableColumn<EnsalamentoVM, Integer> colSala;

    @FXML
    private TableColumn<EnsalamentoVM, String> colDataInicio;

    @FXML
    private TableColumn<EnsalamentoVM, String> colDataFinal;

    @FXML
    private TableColumn<EnsalamentoVM, String> colPeriodicidade;

    @FXML
    private TableColumn<EnsalamentoVM, String> colDisciplina;

    @FXML
    private TableColumn<EnsalamentoVM, String> colProfessor;

    private EnsalamentoDAO ensalamentoDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ensalamentoDAO = new EnsalamentoDAO();
        initializeDailyEnsalamentoTable();
    }

    private void setItemsTable(){
        tableEnsalamento.getItems().clear();
        colSala.setCellValueFactory(new PropertyValueFactory<>("numero_sala"));
        colDataInicio.setCellValueFactory(new PropertyValueFactory<>("data_inicio"));
        colDataFinal.setCellValueFactory(new PropertyValueFactory<>("data_final"));
        colPeriodicidade.setCellValueFactory(new PropertyValueFactory<>("horas"));
        colDisciplina.setCellValueFactory(new PropertyValueFactory<>("nome_turma"));
        colProfessor.setCellValueFactory(new PropertyValueFactory<>("nome_prof"));
    }

    private void initializeDailyEnsalamentoTable(){
        try {
            setItemsTable();
            ObservableList<EnsalamentoVM> ensalamentos = ensalamentoDAO.getDaily();

            tableEnsalamento.setPlaceholder(new Label("Não há ensalamentos no dia de hoje - " + LocalDate.now().toString()));
            tableEnsalamento.setItems(ensalamentos);
        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }
}
