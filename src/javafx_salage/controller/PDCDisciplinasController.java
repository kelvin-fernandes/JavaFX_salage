package javafx_salage.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx_salage.DAO.DisciplinasDAO;
import javafx_salage.model.Disciplina;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PDCDisciplinasController implements Initializable {

    @FXML
    private TableView<Disciplina> tableDisciplinas;

    @FXML
    private TableColumn<Disciplina, String> colId;

    @FXML
    private TableColumn<Disciplina, String> colNome;

    @FXML
    private TableColumn<Disciplina, Integer> colQtdAlunos;

    @FXML
    private JFXTextField txtId, txtNome, txtQtdAlunos;
    

    @FXML
    private JFXButton btnAdicionar, btnEditar, btnCancelar, btnDeletar;

    @FXML
    private Label lblStatus;

    private DisciplinasDAO disciplinasDAO;
    private static Disciplina discEditing;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disciplinasDAO = new DisciplinasDAO();
        initializeDisciplinaTable();
        disableBtnEditar();
        setRowToEdit();
        btnDeletar.setDisable(true);
    }

    private void initializeDisciplinaTable(){
        try {
            tableDisciplinas.getItems().clear();
            colId.setCellValueFactory(new PropertyValueFactory<>("id_disc"));
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome_turma"));
            colQtdAlunos.setCellValueFactory(new PropertyValueFactory<>("qtd_alunos"));

            ObservableList<Disciplina> disciplinas = disciplinasDAO.getAll();
            addTableListener();
            addNumberTextFieldListener();
            tableDisciplinas.setItems(disciplinas);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void activeBtnEditar(){
        btnAdicionar.setVisible(false);
        btnAdicionar.toBack();
        btnEditar.setVisible(true);
        btnCancelar.setVisible(true);
    }

    private void activeBtnAdicionar(){
        disableBtnEditar();
        btnAdicionar.setVisible(true);
    }

    private void disableBtnEditar(){
        btnEditar.setVisible(false);
        btnCancelar.setVisible(false);
    }

    private void addTableListener(){
        ObservableList<Disciplina> selectedCells = tableDisciplinas.getSelectionModel().getSelectedItems();
        selectedCells.addListener((ListChangeListener<Disciplina>) c -> btnDeletar.setDisable(false));
    }

    private void addNumberTextFieldListener(){
        ChangeListener<String> forceNumberListener = (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                ((StringProperty) observable).set(oldValue);
        };

        txtQtdAlunos.textProperty().addListener(forceNumberListener);
    }

    private void setRowToEdit(){
        tableDisciplinas.setRowFactory( tv -> {
            TableRow<Disciplina> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Disciplina rowData = row.getItem();
                    setEditForm(rowData);
                }
            });
            return row ;
        });
    }

    private void setEditForm(Disciplina disc){
        discEditing = disc;
        txtId.setText(disc.getId_disc());
        txtNome.setText(disc.getNome_turma());
        txtQtdAlunos.setText(disc.getQtd_alunos().toString());
        activeBtnEditar();
    }

    @FXML
    void btnAdicionarAction() {
        try {
            if(disciplinasDAO.isDisciplina(txtId.getText()))
                lblStatus.setText("Disciplina já existente!");
            else if(txtId.getText().isEmpty() || txtNome.getText().isEmpty())
                lblStatus.setText("Preencha todos os campos!");
            else{
                disciplinasDAO.add(txtId.getText(), txtNome.getText(), Integer.parseInt(txtQtdAlunos.getText()));
                limparCamposAdicionar();
                initializeDisciplinaTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeletarAction() {
        try {
            Disciplina u = tableDisciplinas.getSelectionModel().getSelectedItem();
            if (u != null)
                disciplinasDAO.delete(u.getId_disc());
            initializeDisciplinaTable();
            btnDeletar.setDisable(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnEditarAction() {
        try {
            Disciplina newUser = new Disciplina(txtId.getText(), txtNome.getText(), Integer.parseInt(txtQtdAlunos.getText()));
            Disciplina discBD = disciplinasDAO.find(txtId.getText());

            if( txtId.getText().isEmpty() || txtNome.getText().isEmpty())
                lblStatus.setText("Preencha todos os campos!");
            else if((newUser.getId_disc().equals(discEditing.getId_disc()) &&
                     newUser.getNome_turma().equals(discEditing.getNome_turma()) &&
                     newUser.getQtd_alunos() == (discEditing.getQtd_alunos())) ||
                    (discBD != null &&
                    (!discBD.getId_disc().equals(discEditing.getId_disc()))))
                lblStatus.setText("Disciplina já existente!");
            else if(!discEditing.equals(disciplinasDAO.find(txtId.getText()))){
                disciplinasDAO.delete(discEditing.getId_disc());
                disciplinasDAO.add(txtId.getText(), txtNome.getText(), Integer.parseInt(txtQtdAlunos.getText()));
                limparCamposAdicionar();
                activeBtnAdicionar();
                initializeDisciplinaTable();
            }
            else{
                disciplinasDAO.update(txtId.getText(), txtNome.getText(), Integer.parseInt(txtQtdAlunos.getText()));
                limparCamposAdicionar();
                activeBtnAdicionar();
                initializeDisciplinaTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnCancelarAction() {
        limparCamposAdicionar();
        activeBtnAdicionar();
    }

    private void limparCamposAdicionar(){
        lblStatus.setText("");
        txtId.setText("");
        txtNome.setText("");
        txtQtdAlunos.setText("");
    }
}
