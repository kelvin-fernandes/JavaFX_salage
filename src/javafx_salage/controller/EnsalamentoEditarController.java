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
import javafx_salage.viewmodel.EnsalamentoVM;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class EnsalamentoEditarController implements Initializable{

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
    private JFXButton btnEditar, btnCancelar;

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

    private EnsalamentoVM ensalamentoOriginal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListeners();
    }

    public void setEnsalamentoOriginal(EnsalamentoVM ensalamentoOriginal){
        this.ensalamentoOriginal = ensalamentoOriginal;
        initializeComboBoxes();
    }

    private void initializeComboBoxes(){
        try {
            cbSala.getItems().clear();
            cbDisciplina.getItems().clear();
            cbProfessor.getItems().clear();

            cbSala.setPromptText(ensalamentoOriginal.getNumero_sala().toString());
            dtInicio.setPromptText("Data Inicial: " + ensalamentoOriginal.getData_inicio());
            dtInicio.setValue(LocalDate.parse(ensalamentoOriginal.getData_inicioWOformat()));
            dtFinal.setPromptText("Data Final: " + ensalamentoOriginal.getData_final());
            dtFinal.setValue(LocalDate.parse(ensalamentoOriginal.getData_finalWOformat()));
            cbDisciplina.setPromptText(ensalamentoOriginal.getNome_turma());
            cbProfessor.setPromptText(ensalamentoOriginal.getNome_prof());

            cbSala.setItems(EnsalamentoDAO.getSalas());
            cbDisciplina.setItems(EnsalamentoDAO.getDisciplinas());
            cbProfessor.setItems(EnsalamentoDAO.getProfessores());

            cbSala.setValue(new Sala(ensalamentoOriginal.getNumero_sala()));
            cbSalaChoosed = new Sala(ensalamentoOriginal.getNumero_sala());
            setMbHoras();
            cbDisciplina.setValue(new Disciplina(ensalamentoOriginal.getId_disc(), ensalamentoOriginal.getNome_turma()));
            cbDisciplinaChoosed = new Disciplina(ensalamentoOriginal.getId_disc(), ensalamentoOriginal.getNome_turma());
            cbProfessor.setValue(new Professor(ensalamentoOriginal.getRgf_prof(), ensalamentoOriginal.getNome_prof()));
            cbProfessorChoosed = new Professor(ensalamentoOriginal.getRgf_prof(), ensalamentoOriginal.getNome_prof());
            setDias();

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
                boolean inseriu = false;
                for(Hora h : ensalamentoOriginal.getHoras()){
                    if(h.getHora_inicio().equals(hora.getHora_inicio())) {
                        CheckMenuItem cmi = new CheckMenuItem(h.getHora_inicio());
                        cmi.setSelected(true);
                        items.add(cmi);
                        inseriu = true;
                        break;
                    }
                }
                if(!inseriu)
                    items.add(new CheckMenuItem(hora.getHora_inicio()));
            }
            mbHoras.getItems().addAll(items);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void setDias(){
        List<Boolean> dias = ensalamentoOriginal.getDias();
        if(dias.get(0))
            chSegunda.setSelected(true);
        if(dias.get(1))
            chTerca.setSelected(true);
        if(dias.get(2))
            chQuarta.setSelected(true);
        if(dias.get(3))
            chQuinta.setSelected(true);
        if(dias.get(4))
            chSexta.setSelected(true);
        if(dias.get(5))
            chSabado.setSelected(true);
    }

    private void initializeListeners(){
        cbSala.valueProperty().addListener((ov, s1, s2) -> cbSalaChoosed = s2);
        cbDisciplina.valueProperty().addListener((ov, d1, d2) -> cbDisciplinaChoosed = d2);
        cbProfessor.valueProperty().addListener((ov, p1, p2) -> cbProfessorChoosed = p2);
    }

    @FXML
    void btnEditarAction() {
        try {
            ArrayList<String> horas = new ArrayList<>();
            for(MenuItem item : mbHoras.getItems()) {
                CheckMenuItem checkMenuItem = (CheckMenuItem) item;
                if(checkMenuItem.isSelected()) {
                    horas.add(checkMenuItem.getText());
                }
            }

            boolean horasIguais = false;
            int contHoras = 0;
            for(Hora h : ensalamentoOriginal.getHoras()){
                for(String hs : horas){
                    if(h.getHora_inicio().equals(hs)){
                        contHoras++;
                        break;
                    }
                }
            }
            if(contHoras == ensalamentoOriginal.getHoras().size())
                horasIguais = true;

            List<Boolean> dias = Arrays.asList( chSegunda.isSelected(), chTerca.isSelected(),
                                                chQuarta.isSelected(), chQuinta.isSelected(),
                                                chSexta.isSelected(), chSabado.isSelected());
            List<Boolean> diasOriginais = ensalamentoOriginal.getDias();
            int it = 0, dt = 0;
            boolean diasIguais = false;
            for(Boolean b : dias){
                if(b == diasOriginais.get(it++))
                    dt++;
                else
                    break;
            }

            if(dt == 6)
                diasIguais = true;

            if(cbSalaChoosed == null)
                lblStatus.setText("Escolha a sala!");
            else if(dtInicio.getValue().isBefore(LocalDate.parse(ensalamentoOriginal.getData_inicioWOformat())))
                lblStatus.setText("Não altere a data inicial!");
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
                if((cbSalaChoosed.getNumero_sala() == ensalamentoOriginal.getNumero_sala()) &&
                        (dtInicio.getValue() == LocalDate.parse(ensalamentoOriginal.getData_inicioWOformat())) &&
                        (dtFinal.getValue() == LocalDate.parse(ensalamentoOriginal.getData_finalWOformat())) &&
                        (horasIguais) &&
                        (diasIguais)){
                    if(cbDisciplinaChoosed.getId_disc().equals(ensalamentoOriginal.getId_disc()) ||
                       cbProfessorChoosed.getRgf_prof().equals(ensalamentoOriginal.getRgf_prof())) {
                        ensalamentoOriginal.setId_disc(cbDisciplinaChoosed.getId_disc());
                        ensalamentoOriginal.setRgf_prof(cbProfessorChoosed.getRgf_prof());
                        EnsalamentoDAO.update(ensalamentoOriginal);

                        Stage st = (Stage) btnEditar.getScene().getWindow();
                        st.close();
                    }
                }
                else {
                    EnsalamentoDAO.delete(ensalamentoOriginal);
                    Ensalamento e = new Ensalamento(
                            ensalamentoOriginal.getId_ensal(),
                            dtInicio.getValue().toString(),
                            dtFinal.getValue().toString(),
                            cbSala.getValue().getNumero_sala(),
                            cbProfessor.getValue().getRgf_prof(),
                            cbDisciplina.getValue().getId_disc()
                    );

                    Integer contadorHoras = 0;

                    for (String hora : horas) {
                        e.setHora_inicio(hora);
                        if (!EnsalamentoDAO.existeEnsalamento(e))
                            contadorHoras++;
                        else {
                            lblStatus.setText("Ensalamento existente às " + e.getHora_inicio() + " horas!");
                            break;
                        }
                    }

                    if (contadorHoras == horas.size()) {
                        for (String hora : horas) {
                            e.setHora_inicio(hora);
                            EnsalamentoDAO.realizarEnsalamentos(e, dias);
                        }

                        cleanFields();
                        Stage st = (Stage) btnEditar.getScene().getWindow();
                        st.close();
                    }
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
        dtInicio.setValue(null);
        dtFinal.setValue(null);
        setMbHoras();
        cbDisciplina.setValue(null);
        cbProfessor.setValue(null);
    }
}

