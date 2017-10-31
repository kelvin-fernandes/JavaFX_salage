package javafx_salage.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
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

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Usuario> data = null;
        try {
            data = usuarioDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        colLogin.setCellValueFactory(new PropertyValueFactory<Usuario, String>("LoginDAO"));
        colSenha.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Senha"));
        colAcesso.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("Acesso"));

        tableUsuarios.setItems(data);
    }
}
