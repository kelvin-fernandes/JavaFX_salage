package javafx_salage.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx_salage.model.Login;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author kelvin-fernandes
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */


    @FXML
    private AnchorPane paneLogin;

    @FXML
    private JFXTextField txtLogin;

    @FXML
    private JFXPasswordField txtSenha;

    @FXML
    private Label lblEsqueceuSenha;

    @FXML
    private Label lblStatus;

    private Login loginModel = new Login();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDepthManager.setDepth(paneLogin, 2);
    }    
    
    @FXML
    private void closeLogin(MouseEvent event){
        Platform.exit();
    }

    @FXML
    public void btnEntrarAction (ActionEvent event){
        try {
            if(loginModel.isLogin(txtLogin.getText(), txtSenha.getText())){
                try {
                    ((Node)event.getSource()).getScene().getWindow().hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/Dashboard.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.DECORATED);
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.centerOnScreen();
                    stage.show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(txtLogin.getText().isEmpty() && txtSenha.getText().isEmpty())
                lblStatus.setText("Preencha os campos!");
            else
                lblStatus.setText("Usuário e/ou Senha incorretos!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
