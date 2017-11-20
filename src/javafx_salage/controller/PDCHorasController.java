package javafx_salage.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx_salage.DAO.HoraDAO;
import javafx_salage.model.Hora;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class PDCHorasController implements Initializable {

    @FXML
    private TableView<Hora> tableHoras;

    @FXML
    private TableColumn<Hora, String> colHora;

    @FXML
    private JFXDatePicker dpHora;

    @FXML
    private JFXButton btnAdicionar, btnEditar, btnCancelar, btnDeletar;

    @FXML
    private Label lblStatus;

    private HoraDAO horaDAO;
    private static Hora horaEditing;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        horaDAO = new HoraDAO();
        dpHora.setShowTime(true);
        dpHora.setTime(LocalTime.MIN);
        initializeHoraTable();
        disableBtnEditar();
        setRowToEdit();
        btnDeletar.setDisable(true);
    }

    private void initializeHoraTable(){
        try {
            tableHoras.getItems().clear();
            colHora.setCellValueFactory(new PropertyValueFactory<>("hora_inicio"));

            ObservableList<Hora> horas = horaDAO.getAll();
            addTableListener();
            tableHoras.setItems(horas);

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
        ObservableList<Hora> selectedCells = tableHoras.getSelectionModel().getSelectedItems();
        selectedCells.addListener((ListChangeListener<Hora>) c -> btnDeletar.setDisable(false));
    }

    private void setRowToEdit(){
        tableHoras.setRowFactory( tv -> {
            TableRow<Hora> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Hora rowData = row.getItem();
                    setEditForm(rowData);
                }
            });
            return row ;
        });
    }

    private void setEditForm(Hora hora){
        horaEditing = hora;
        dpHora.setTime(LocalTime.parse(hora.getHora_inicio()));
        activeBtnEditar();
    }

    public static boolean verifyInterval(LocalTime time){
        return time.isAfter(LocalTime.of(6, 59)) && time.isBefore(LocalTime.of(23, 1)) && time.getMinute() == 0;
    }

    @FXML
    void btnAdicionarAction() {
        try {
            if(!verifyInterval(dpHora.getTime()))
                lblStatus.setText("Permitido: Hora >= 7 e Hora <= 22 e Minutos == 0");
            else if(horaDAO.isHora(dpHora.getTime().toString()))
                lblStatus.setText("Hora já existente!");
            else{
                horaDAO.add(dpHora.getTime().toString());
                limparCamposAdicionar();
                initializeHoraTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeletarAction() {
        try {
            Hora h = tableHoras.getSelectionModel().getSelectedItem();
            if (h != null)
                horaDAO.delete(h.getHora_inicio());
            initializeHoraTable();
            btnDeletar.setDisable(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnEditarAction() {
        try {
            Hora horaBD = horaDAO.find(dpHora.getTime().toString());

            if(dpHora.getTime().getHour() == 0)
                lblStatus.setText("Escolha um Horário!");
            else if(horaBD != null)
                lblStatus.setText("Hora já existente!");
            else {
                horaDAO.delete(horaEditing.getHora_inicio());
                horaDAO.add(dpHora.getTime().toString());
                limparCamposAdicionar();
                activeBtnAdicionar();
                initializeHoraTable();
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
        dpHora.setTime(LocalTime.MIN);
    }
}
