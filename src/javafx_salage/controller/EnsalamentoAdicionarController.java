package javafx_salage.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx_salage.DAO.EnsalamentoDAO;
import javafx_salage.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class EnsalamentoAdicionarController implements Initializable{

    @FXML
    private JFXComboBox<Sala> cbSala;

    @FXML
    private JFXDatePicker dtInicio;

    @FXML
    private JFXDatePicker dtFinal;

    @FXML
    private MenuButton mbHoras;

    @FXML
    private JFXComboBox<Disciplina> cbDisciplina;

    @FXML
    private JFXComboBox<Professor> cbProfessor;

    @FXML
    private JFXButton btnAdicionar, btnCancelar;

    @FXML
    private Label lblStatus;

    @FXML
    private JFXCheckBox chSegunda;

    @FXML
    private JFXCheckBox chTerca;

    @FXML
    private JFXCheckBox chQuarta;

    @FXML
    private JFXCheckBox chQuinta;

    @FXML
    private JFXCheckBox chSexta;

    @FXML
    private JFXCheckBox chSabado;

    private Sala cbSalaChoosed;
    private Disciplina cbDisciplinaChoosed;
    private Professor cbProfessorChoosed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComboBoxes();
        initializeListeners();
    }

    private void initializeComboBoxes(){
        try {
            cbSala.getItems().clear();
            cbDisciplina.getItems().clear();
            cbProfessor.getItems().clear();

            cbSala.setItems(EnsalamentoDAO.getSalas());
            dtInicio.setValue(LocalDate.now());
            dtFinal.setValue(LocalDate.now());
            setMbHoras();
            cbDisciplina.setItems(EnsalamentoDAO.getDisciplinas());
            cbProfessor.setItems(EnsalamentoDAO.getProfessores());

            StringConverter<LocalDate> converter =  new StringConverter<LocalDate>()
            {
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
                    {
                        return null;
                    }
                    return LocalDate.parse(dateString,dateTimeFormatter);
                }
            };

            dtInicio.setConverter(converter);
            dtFinal.setConverter(converter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setMbHoras(){
        mbHoras.getItems().clear();
        try {
            ObservableList<Hora> horas = EnsalamentoDAO.getHoras();
            ObservableList<CheckMenuItem> items = FXCollections.observableArrayList();
            for (Hora hora : horas) {
                items.add(new CheckMenuItem(hora.getHora_inicio()));
            }
            mbHoras.getItems().addAll(items);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void initializeListeners(){
        cbSala.valueProperty().addListener((ov, s1, s2) -> cbSalaChoosed = s2);
        cbDisciplina.valueProperty().addListener((ov, d1, d2) -> cbDisciplinaChoosed = d2);
        cbProfessor.valueProperty().addListener((ov, p1, p2) -> cbProfessorChoosed = p2);
    }

    @FXML
    void btnAdicionarAction() {
        try {
            ArrayList<String> horas = new ArrayList<>();
            for(MenuItem item : mbHoras.getItems()) {
                CheckMenuItem checkMenuItem = (CheckMenuItem) item;
                if(checkMenuItem.isSelected()) {
                    horas.add(checkMenuItem.getText());
                }
            }
            if(cbSalaChoosed == null)
                lblStatus.setText("Escolha a sala!");
            else if(dtInicio.getValue().isBefore(LocalDate.now()))
                lblStatus.setText("Escolha uma data igual ou posterior à hoje!");
            else if(dtFinal.getValue().isBefore((dtInicio.getValue())))
                lblStatus.setText("A data final deve ser igual ou posterior a inicial!");
            else if(horas.size() == 0)
                lblStatus.setText("Escolha pelo menos 1 hora");
            else if(cbDisciplinaChoosed == null)
                lblStatus.setText("Escolha a Disciplina!");
            else if(cbProfessorChoosed == null)
                lblStatus.setText("Escolha o Professor!");
            else if(!chSegunda.isSelected() && !chTerca.isSelected() && !chQuarta.isSelected() &&
                    !chQuinta.isSelected() && !chSexta.isSelected() && !chSabado.isSelected())
                lblStatus.setText("Selecione ao menos 1 dia da semana!");
            else {
                List<Boolean> dias = Arrays.asList( chSegunda.isSelected(), chTerca.isSelected(),
                        chQuarta.isSelected(), chQuinta.isSelected(),
                        chSexta.isSelected(), chSabado.isSelected());

                Integer max = EnsalamentoDAO.getMaxIdEnsalamento() + 1;

                Ensalamento e = new Ensalamento(
                        max,
                        dtInicio.getValue().toString(),
                        dtFinal.getValue().toString(),
                        cbSalaChoosed.getNumero_sala(),
                        cbProfessorChoosed.getRgf_prof(),
                        cbDisciplinaChoosed.getId_disc()
                );

                Integer contadorHoras = 0;

                for(String hora : horas){
                    e.setHora_inicio(hora);
                    if(!EnsalamentoDAO.existeEnsalamento(e))
                        contadorHoras++;
                    else {
                        lblStatus.setText("Ensalamento existente às " + e.getHora_inicio() + " horas!");
                        break;
                    }
                }

                if(contadorHoras == horas.size()){
                    for(String hora : horas){
                        e.setHora_inicio(hora);
                        EnsalamentoDAO.realizarEnsalamentos(e, dias);
                    }

                    cleanFields();
                    Stage st = (Stage) btnAdicionar.getScene().getWindow();
                    st.close();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void btnCancelarAction(){
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnLimparAction(){
        cleanFields();
    }

    private void cleanFields(){
        cbSala.setValue(null);
        dtInicio.setValue(LocalDate.now());
        dtFinal.setValue(LocalDate.now());
        setMbHoras();
        cbDisciplina.setValue(null);
        cbProfessor.setValue(null);
        lblStatus.setText("");
    }
}

