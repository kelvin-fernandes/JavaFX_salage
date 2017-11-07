package javafx_salage.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx_salage.DAO.UsuarioDAO;
import javafx_salage.model.Usuario;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PDCUsuariosController implements Initializable {

    @FXML
    private TableView<Usuario> tableUsuarios;

    @FXML
    private TableColumn<Usuario, String> colLogin;

    @FXML
    private TableColumn<Usuario, String> colSenha;

    @FXML
    private TableColumn<Usuario, Integer> colAcesso;

    @FXML
    private JFXTextField txtLogin, txtSenha;

    @FXML
    private JFXCheckBox cbAdm;

    @FXML
    private JFXButton btnAdicionar, btnEditar, btnCancelar, btnDeletar;

    @FXML
    private Label lblStatus;

    private UsuarioDAO usuarioDAO;
    private static Usuario userEditing;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usuarioDAO = new UsuarioDAO();
        initializeUsuarioTable();
        disableBtnEditar();
        setRowToEdit();
        btnDeletar.setDisable(true);
    }

    private void initializeUsuarioTable(){
        try {
            tableUsuarios.getItems().clear();
            colLogin.setCellValueFactory(new PropertyValueFactory<>("login_usu"));
            colSenha.setCellValueFactory(new PropertyValueFactory<>("senha_usu"));
            colAcesso.setCellValueFactory(new PropertyValueFactory<>("id_ace"));

            ObservableList<Usuario> usuarios = usuarioDAO.getAll();
            addTableListener();
            tableUsuarios.setItems(usuarios);

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
        ObservableList<Usuario> selectedCells = tableUsuarios.getSelectionModel().getSelectedItems();
        selectedCells.addListener((ListChangeListener<Usuario>) c -> btnDeletar.setDisable(false));
    }

    private void setRowToEdit(){
        tableUsuarios.setRowFactory( tv -> {
            TableRow<Usuario> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Usuario rowData = row.getItem();
                    setEditForm(rowData);
                }
            });
            return row ;
        });
    }

    private void setEditForm(Usuario user){
        userEditing = user;
        txtLogin.setText(user.getLogin_usu());
        txtSenha.setText(user.getSenha_usu());
        if(user.getId_ace() == 1)
            cbAdm.setSelected(true);
        else
            cbAdm.setSelected(false);
        activeBtnEditar();
    }

    @FXML
    void btnAdicionarAction() {
        try {
            int isAdm;
            if(cbAdm.isSelected())
                isAdm = 1;
            else
                isAdm = 2;

            if(usuarioDAO.isUsuario(txtLogin.getText()))
                lblStatus.setText("Usuário já existente!");
            else if(txtLogin.getText().isEmpty() || txtSenha.getText().isEmpty())
                lblStatus.setText("Preencha os campos login e senha!");
            else{
                usuarioDAO.add(txtLogin.getText(), txtSenha.getText(), isAdm);
                limparCamposAdicionar();
                initializeUsuarioTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeletarAction() {
        try {
            Usuario u = tableUsuarios.getSelectionModel().getSelectedItem();
            if (u != null)
                usuarioDAO.delete(u.getLogin_usu());
            initializeUsuarioTable();
            btnDeletar.setDisable(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnEditarAction() {
        try {
            int isAdm;
            if(cbAdm.isSelected())
                isAdm = 1;
            else
                isAdm = 2;

            Usuario newUser = new Usuario(txtLogin.getText(), txtSenha.getText(), isAdm);
            Usuario userBD = usuarioDAO.find(txtLogin.getText());

            if( txtLogin.getText().isEmpty() || txtSenha.getText().isEmpty())
                lblStatus.setText("Preencha os campos login e senha!");
            else if((newUser.getLogin_usu().equals(userEditing.getLogin_usu()) &&
                     newUser.getSenha_usu().equals(userEditing.getSenha_usu()) &&
                     newUser.getId_ace() == (userEditing.getId_ace())) ||
                    (userBD != null &&
                    (!userBD.getLogin_usu().equals(userEditing.getLogin_usu()))))
                lblStatus.setText("Usuário já existente!");
            else if(!userEditing.equals(usuarioDAO.find(txtLogin.getText()))){
                usuarioDAO.delete(userEditing.getLogin_usu());
                usuarioDAO.add(txtLogin.getText(), txtSenha.getText(), isAdm);
                limparCamposAdicionar();
                activeBtnAdicionar();
                initializeUsuarioTable();
            }
            else{
                usuarioDAO.update(txtLogin.getText(), txtSenha.getText(), isAdm);
                limparCamposAdicionar();
                activeBtnAdicionar();
                initializeUsuarioTable();
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
        txtLogin.setText("");
        txtSenha.setText("");
        cbAdm.setSelected(false);
    }
}
