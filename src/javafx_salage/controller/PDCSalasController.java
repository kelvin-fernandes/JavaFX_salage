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
import javafx_salage.DAO.SalasDAO;
import javafx_salage.model.Sala;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PDCSalasController implements Initializable {

    @FXML
    private TableView<Sala> tableSalas;

    @FXML
    private TableColumn<Sala, Integer> colNumero;

    @FXML
    private TableColumn<Sala, String> colDescricao;

    @FXML
    private TableColumn<Sala, Integer> colCapacidade;

    @FXML
    private JFXTextField txtNumero, txtDescricao, txtCapacidade;

    @FXML
    private JFXButton btnAdicionar, btnEditar, btnCancelar, btnDeletar;

    @FXML
    private Label lblStatus;

    private SalasDAO salasDAO;
    private static Sala salaEditing;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        salasDAO = new SalasDAO();
        initializeSalasTable();
        disableBtnEditar();
        setRowToEdit();
        btnDeletar.setDisable(true);
    }

    private void initializeSalasTable(){
        try {
            tableSalas.getItems().clear();
            colNumero.setCellValueFactory(new PropertyValueFactory<>("numero_sala"));
            colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao_sala"));
            colCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade_sala"));

            ObservableList<Sala> salas = salasDAO.getAll();
            addTableListener();
            addNumberTextFieldListener();
            tableSalas.setItems(salas);

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
        ObservableList<Sala> selectedCells = tableSalas.getSelectionModel().getSelectedItems();
        selectedCells.addListener((ListChangeListener<Sala>) c -> btnDeletar.setDisable(false));
    }

    private void addNumberTextFieldListener(){
        ChangeListener<String> forceNumberListener = (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                ((StringProperty) observable).set(oldValue);
        };

        txtNumero.textProperty().addListener(forceNumberListener);
        txtCapacidade.textProperty().addListener(forceNumberListener);
    }

    private void setRowToEdit(){
        tableSalas.setRowFactory( tv -> {
            TableRow<Sala> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Sala rowData = row.getItem();
                    setEditForm(rowData);
                }
            });
            return row ;
        });
    }

    private void setEditForm(Sala sala){
        salaEditing = sala;
        txtNumero.setText(sala.getNumero_sala().toString());
        txtDescricao.setText(sala.getDescricao_sala());
        txtCapacidade.setText(sala.getCapacidade_sala().toString());
        activeBtnEditar();
    }

    @FXML
    void btnAdicionarAction() {
        try {
            if(txtNumero.getText().isEmpty() || txtDescricao.getText().isEmpty() || txtCapacidade.getText().isEmpty())
                lblStatus.setText("Preencha todos os campos!");
            else if(salasDAO.isSala(Integer.parseInt(txtNumero.getText())))
                lblStatus.setText("Sala já existente!");
            else{
                salasDAO.add(Integer.parseInt(txtNumero.getText()), txtDescricao.getText(), Integer.parseInt(txtCapacidade.getText()));
                limparCamposAdicionar();
                initializeSalasTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeletarAction() {
        try {
            Sala s = tableSalas.getSelectionModel().getSelectedItem();
            if (s != null)
                salasDAO.delete(s.getNumero_sala());
            initializeSalasTable();
            btnDeletar.setDisable(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnEditarAction() {
        try {
            Sala newSala = new Sala(Integer.parseInt(txtNumero.getText()), txtDescricao.getText(), Integer.parseInt(txtCapacidade.getText()));
            Sala salaBD = salasDAO.find(Integer.parseInt(txtNumero.getText()));

            if(txtNumero.getText().isEmpty() || txtDescricao.getText().isEmpty() || txtCapacidade.getText().isEmpty())
                lblStatus.setText("Preencha todos os campos!");
            else if(( newSala.getNumero_sala().equals(salaEditing.getNumero_sala()) &&
                     newSala.getDescricao_sala().equals(salaEditing.getDescricao_sala()) &&
                     newSala.getCapacidade_sala().equals(salaEditing.getCapacidade_sala())) ||
                    (salaBD != null) &&
                    salaBD.getNumero_sala() != salaEditing.getNumero_sala())
                lblStatus.setText("Sala já existente!");
            else if(!salaEditing.equals(salasDAO.find(Integer.parseInt(txtNumero.getText())))){
                salasDAO.delete(salaEditing.getNumero_sala());
                salasDAO.add(Integer.parseInt(txtNumero.getText()), txtDescricao.getText(), Integer.parseInt(txtCapacidade.getText()));
                limparCamposAdicionar();
                activeBtnAdicionar();
                initializeSalasTable();
            }
            else{
                salasDAO.update(Integer.parseInt(txtNumero.getText()), txtDescricao.getText(), Integer.parseInt(txtCapacidade.getText()));
                limparCamposAdicionar();
                activeBtnAdicionar();
                initializeSalasTable();
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
        txtNumero.setText("");
        txtDescricao.setText("");
        txtCapacidade.setText("");
    }
}
