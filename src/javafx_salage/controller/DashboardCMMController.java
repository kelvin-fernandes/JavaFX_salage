package javafx_salage.controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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

public class DashboardCMMController implements Initializable{

    /**
     * Initializes the controller class.
     */

    @FXML
    private AnchorPane paneConteudo;

    private AnchorPane diario, ensalamento;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            diario = FXMLLoader.load(getClass().getResource("../view/Diario.fxml"));
            ensalamento = FXMLLoader.load(getClass().getResource("../view/EnsalamentoCMM.fxml"));
            setNode(diario);
        } catch (IOException ex) {
            Logger.getLogger(DashboardCMMController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setNode(Node node) {
        paneConteudo.getChildren().clear();
        paneConteudo.getChildren().add(node);

        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    void btnDiarioAction(){ setNode(diario); }

    @FXML
    void btnEnsalamentoAction() {
        setNode(ensalamento);
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
