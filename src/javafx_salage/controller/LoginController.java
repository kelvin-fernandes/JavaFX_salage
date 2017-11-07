package javafx_salage.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx_salage.DAO.LoginDAO;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author kelvin-fernandes
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane paneLogin;

    @FXML
    private JFXTextField txtLogin;

    @FXML
    private JFXPasswordField txtSenha;

    @FXML
    private Label lblEsqueceuSenha;//TODO

    @FXML
    private Label lblStatus;

    private LoginDAO loginDAO = new LoginDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDepthManager.setDepth(paneLogin, 2);
    }    
    
    @FXML
    private void closeLogin(){
        Platform.exit();
    }

    @FXML
    public void btnEntrarAction (ActionEvent event){
        try {
            if(loginDAO.isLogin(txtLogin.getText(), txtSenha.getText())){
                try {
                    ((Node)event.getSource()).getScene().getWindow().hide();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/Dashboard.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.DECORATED);
                    stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/appicon.png")));
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.centerOnScreen();
                    stage.setTitle("SALAGE - Dashboard");
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
