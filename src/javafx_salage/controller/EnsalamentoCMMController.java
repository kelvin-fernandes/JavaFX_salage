package javafx_salage.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx_salage.DAO.EnsalamentoDAO;
import javafx_salage.Main;
import javafx_salage.model.Ensalamento;
import javafx_salage.model.Hora;
import javafx_salage.model.Professor;
import javafx_salage.model.Sala;
import javafx_salage.viewmodel.EnsalamentoVM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EnsalamentoCMMController implements Initializable{

    @FXML
    private TableView<EnsalamentoVM> tableEnsalamento;

    @FXML
    private JFXComboBox<Sala> cbSala;

    @FXML
    private JFXDatePicker dtInicio;

    @FXML
    private JFXComboBox<Professor> cbProfessor;

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
    private Sala cbSalaChoosed;
    private Professor cbProfessorChoosed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ensalamentoDAO = new EnsalamentoDAO();
        initializeComboBoxes();
        initializeListeners();
        initializeEnsalamentoTable(null);
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

    private void initializeEnsalamentoTable(Ensalamento en){
        try{
            setItemsTable();
            ObservableList<EnsalamentoVM> ensalamentos;
            if(en == null) ensalamentos = ensalamentoDAO.getAll();
            else ensalamentos = ensalamentoDAO.getAllEnsalamentoFilters(en);

            ObservableList<EnsalamentoVM> ensalamentosNovos = FXCollections.observableArrayList();
            int id_ensal, contadorEnsalamento = 0;
            EnsalamentoVM ensalamentoInicial;
            if(ensalamentos.size() > 1) {
                for(int i = 0; i < ensalamentos.size() - 1; i++) {
                    id_ensal = ensalamentos.get(i).getId_ensal();
                    boolean proximoDiferente = false;

                    if(ensalamentos.get(i + 1).getId_ensal() != id_ensal) {
                        ensalamentoInicial = ensalamentos.get(i - contadorEnsalamento);
                        ensalamentoInicial.setData_final(ensalamentos.get(i).getData_inicioWOformat());
                        LocalDate df = LocalDate.parse(ensalamentoInicial.getData_finalWOformat());
                        if(df.isAfter(LocalDate.now()) || df.isEqual(LocalDate.now())) {
                            ensalamentosNovos.add(ensalamentoInicial);
                        }
                        proximoDiferente = true;
                        contadorEnsalamento = 0;
                    }
                    if(i + 1 == ensalamentos.size() - 1) {
                        if(ensalamentos.get(i + 1).getId_ensal() == id_ensal) { //caso o último registro não seja diferente do penúltimo registro (ou seja, mesmo ensalamento)
                            if(contadorEnsalamento > 0) { //verifica o último ensalamento caso este tenha mais de 2 registros
                                ensalamentoInicial = ensalamentos.get(i - contadorEnsalamento);
                                ensalamentoInicial.setData_final(ensalamentos.get(i + 1).getData_inicioWOformat());
                            }
                            else { //caso o último ensalamento tenha 2 registros
                                ensalamentoInicial = ensalamentos.get(i);
                                ensalamentoInicial.setData_final(ensalamentos.get(i + 1).getData_inicioWOformat());
                            }
                            LocalDate df = LocalDate.parse(ensalamentoInicial.getData_finalWOformat());
                            if(df.isAfter(LocalDate.now()) || df.isEqual(LocalDate.now())) {
                                ensalamentosNovos.add(ensalamentoInicial);
                            }
                        }
                        else {
                            ensalamentos.get(i + 1).setData_final(ensalamentos.get(i + 1).getData_inicioWOformat());
                            LocalDate df = LocalDate.parse(ensalamentos.get(i + 1).getData_finalWOformat());
                            if(df.isAfter(LocalDate.now()) || df.isEqual(LocalDate.now())) {
                                ensalamentosNovos.add(ensalamentos.get(i + 1));
                            }
                        }
                    }
                    else if(!proximoDiferente){
                        contadorEnsalamento++;
                    }
                }
            }
            else if(ensalamentos.size() == 1) {
                LocalDate dia = LocalDate.parse(ensalamentos.get(0).getData_inicioWOformat());
                ensalamentos.get(0).setData_final(ensalamentos.get(0).getData_inicioWOformat());
            }

            ObservableList<EnsalamentoVM> horasWIdEnsal = EnsalamentoDAO.getHorasWId_ensal();

            if(ensalamentos.size() == 1) {
                ArrayList<Hora> h = new ArrayList<>();
                for(EnsalamentoVM e : horasWIdEnsal){
                    h.add(new Hora(e.getHora_inicio()));
                }
                ensalamentos.get(0).setHoras(h);
                tableEnsalamento.setItems(ensalamentos);
            }
            else if(ensalamentosNovos.size() > 0) {
                for(EnsalamentoVM e : ensalamentos){
                    ArrayList<Hora> h = new ArrayList<>();
                    for(EnsalamentoVM ehoras : horasWIdEnsal){
                        if(ehoras.getId_ensal() == e.getId_ensal())
                            h.add(new Hora(ehoras.getHora_inicio()));
                    }
                    e.setHoras(h);
                }
                tableEnsalamento.setItems(ensalamentosNovos);
            }

        } catch (SQLException sql) {
            sql.printStackTrace();
        }
    }

    private void initializeComboBoxes(){
        try {
            cbSala.getItems().clear();
            cbProfessor.getItems().clear();

            cbSala.setItems(EnsalamentoDAO.getSalas());
            dtInicio.setValue(LocalDate.now());
            cbProfessor.setItems(EnsalamentoDAO.getProfessores());

            StringConverter<LocalDate> converter =  new StringConverter<LocalDate>() {
                private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                @Override
                public String toString(LocalDate localDate)
                {
                    if(localDate==null)
                        return "";
                    return dateTimeFormatter.format(localDate);
                }

                @Override
                public LocalDate fromString(String dateString)
                {
                    if(dateString==null || dateString.trim().isEmpty())
                        return null;
                    return LocalDate.parse(dateString,dateTimeFormatter);
                }
            };

            dtInicio.setConverter(converter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeListeners(){
        cbSala.valueProperty().addListener((ov, s1, s2) -> cbSalaChoosed = s2);
        cbProfessor.valueProperty().addListener((ov, p1, p2) -> cbProfessorChoosed = p2);
    }

    @FXML
    private void filtrosAction(){
        if(cbSalaChoosed != null && cbProfessorChoosed != null){
            Ensalamento e = new Ensalamento(0, dtInicio.getValue().toString(), null, cbSalaChoosed.getNumero_sala(), cbProfessorChoosed.getRgf_prof(), null);
            initializeEnsalamentoTable(e);
        }
        else if(cbSalaChoosed != null){
            Ensalamento e = new Ensalamento(0, dtInicio.getValue().toString(), null, cbSalaChoosed.getNumero_sala(), null, null);
            initializeEnsalamentoTable(e);
        }
        else if(cbProfessorChoosed != null) {
            Ensalamento e = new Ensalamento(0, dtInicio.getValue().toString(), null, 0, cbProfessorChoosed.getRgf_prof(), null);
            initializeEnsalamentoTable(e);
        }
        else {
            Ensalamento e = new Ensalamento(0, dtInicio.getValue().toString(), null, 0, null, null);
            initializeEnsalamentoTable(e);
        }
    }

    @FXML
    private void cleanFields(){
        cbSala.setValue(null);
        cbSalaChoosed = null;
        dtInicio.setValue(LocalDate.now());
        cbProfessor.setValue(null);
        cbProfessorChoosed = null;
        initializeEnsalamentoTable(null);
    }
}
