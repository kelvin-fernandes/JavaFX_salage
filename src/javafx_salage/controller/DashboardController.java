package javafx_salage.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author kelvin-fernandes
 */

public class DashboardController implements Initializable{

    /**
     * Initializes the controller class.
     */

    @FXML
    private AnchorPane paneConteudo;

    private AnchorPane telaPrincipal, painelDeControle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
//             telaPrincipal = FXMLLoader.load(getClass().getResource("../view/TelaPrincipal.fxml"));
             painelDeControle = FXMLLoader.load(getClass().getResource("../view/PainelDeControle.fxml"));
//            setNode(telaPrincipal);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setNode(Node node) {
        paneConteudo.getChildren().clear();
        paneConteudo.getChildren().add(node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    void btnTelaPrincipalAction(ActionEvent event) {
        setNode(telaPrincipal);
    }

    @FXML
    void btnPainelDeControleAction(ActionEvent event) {
        setNode(painelDeControle);
    }

    @FXML
    public void btnSairAction(ActionEvent event){
        try {
            ((Node)event.getSource()).getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}
