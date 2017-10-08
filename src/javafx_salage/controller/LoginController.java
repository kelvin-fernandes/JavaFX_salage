/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_salage.controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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
import javafx.scene.image.ImageView;
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
    private ImageView iconClose;
    @FXML
    private JFXTextField txtLogin;
    @FXML
    private JFXPasswordField txtSenha;
    @FXML
    private Button btnEntrar;

    private Login loginModel = new Login();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDepthManager.setDepth(paneLogin, 2);
    }    
    
    @FXML
    private void closeLogin(MouseEvent event){
        Platform.exit();
    }

    public void btnEntrarAction (ActionEvent event){
        try {
            if(loginModel.isLogin(txtLogin.getText(), txtSenha.getText())){

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
