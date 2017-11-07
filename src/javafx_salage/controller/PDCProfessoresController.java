package javafx_salage.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx_salage.DAO.ProfessorDAO;
import javafx_salage.model.Professor;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PDCProfessoresController implements Initializable {

    @FXML
    private TableView<Professor> tableProfessores;

    @FXML
    private TableColumn<Professor, String> colRgf;

    @FXML
    private TableColumn<Professor, String> colNome;

    @FXML
    private JFXTextField txtRgf, txtNome;

    @FXML
    private JFXButton btnAdicionar, btnEditar, btnCancelar, btnDeletar;

    @FXML
    private Label lblStatus;

    private ProfessorDAO professorDAO;
    private static Professor profEditing;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        professorDAO = new ProfessorDAO();
        initializeProfessorTable();
        disableBtnEditar();
        setRowToEdit();
        btnDeletar.setDisable(true);
    }

    private void initializeProfessorTable(){
        try {
            tableProfessores.getItems().clear();
            colRgf.setCellValueFactory(new PropertyValueFactory<>("rgf_prof"));
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome_prof"));

            ObservableList<Professor> professores = professorDAO.getAll();
            addTableListener();
            addTextFieldProperty();
            tableProfessores.setItems(professores);

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
        ObservableList<Professor> selectedCells = tableProfessores.getSelectionModel().getSelectedItems();
        selectedCells.addListener((ListChangeListener<Professor>) c -> btnDeletar.setDisable(false));
    }

    private void addTextFieldProperty(){
        txtRgf.lengthProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            String mask = "####.####.###-#";
            String alphaAndDigits = txtRgf.getText().replaceAll("[\\-.]","");
            StringBuilder resultado = new StringBuilder();
            int qtd = 0;

            if (newValue.intValue() > oldValue.intValue()) {
                if (txtRgf.getText().length() <= mask.length()) {
                    for(int i = 0; i < mask.length(); i++) {
                        if (qtd < alphaAndDigits.length()) {
                            if ("#".equals(mask.substring(i, i + 1))) {
                                resultado.append(alphaAndDigits.substring(qtd, qtd + 1));
                                qtd++;
                            } else {
                                resultado.append(mask.substring(i, i + 1));
                            }
                        }
                    }
                    txtRgf.setText(resultado.toString());
                }
                if (txtRgf.getText().length() > mask.length()) {
                    txtRgf.setText(txtRgf.getText(0, mask.length()));
                }
            }
        });
    }

    private void setRowToEdit(){
        tableProfessores.setRowFactory( tv -> {
            TableRow<Professor> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Professor rowData = row.getItem();
                    setEditForm(rowData);
                }
            });
            return row ;
        });
    }

    private void setEditForm(Professor prof){
        profEditing = prof;
        txtRgf.setText(prof.getRgf_prof());
        txtNome.setText(prof.getNome_prof());
        activeBtnEditar();
    }

    @FXML
    void btnAdicionarAction() {
        try {
            if(txtRgf.getText().isEmpty() || txtNome.getText().isEmpty())
                lblStatus.setText("Preencha os campos RGF e Nome!");
            else if(professorDAO.isProfessor(txtRgf.getText()))
                lblStatus.setText("Professor já existente!");
            else{
                professorDAO.add(txtRgf.getText(), txtNome.getText());
                limparCamposAdicionar();
                initializeProfessorTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeletarAction() {
        try {
            Professor u = tableProfessores.getSelectionModel().getSelectedItem();
            if (u != null)
                professorDAO.delete(u.getRgf_prof());
            initializeProfessorTable();
            btnDeletar.setDisable(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnEditarAction() {
        try {
            Professor newProf = new Professor(txtRgf.getText(), txtNome.getText());
            Professor profBD = professorDAO.find(txtRgf.getText());

            if( txtRgf.getText().isEmpty() || txtNome.getText().isEmpty())
                lblStatus.setText("Preencha os campos RGF e Nome!");
            else if( (newProf.getRgf_prof().equals(profEditing.getRgf_prof()) &&
                     newProf.getNome_prof().equals(profEditing.getNome_prof())) ||
                    (profBD != null &&
                    !profBD.getRgf_prof().equals(profEditing.getRgf_prof())))
                lblStatus.setText("Professor já existente!");
            else if(!profEditing.equals(professorDAO.find(txtRgf.getText()))){
                professorDAO.delete(profEditing.getRgf_prof());
                professorDAO.add(txtRgf.getText(), txtNome.getText());
                limparCamposAdicionar();
                activeBtnAdicionar();
                initializeProfessorTable();
            }
            else{
                professorDAO.update(txtRgf.getText(), txtNome.getText());
                limparCamposAdicionar();
                activeBtnAdicionar();
                initializeProfessorTable();
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
        txtRgf.setText("");
        txtNome.setText("");
    }
}
