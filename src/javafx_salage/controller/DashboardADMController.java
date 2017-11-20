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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx_salage.DAO.EnsalamentoDAO;
import javafx_salage.viewmodel.EnsalamentoVM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author kelvin-fernandes
 */

public class DashboardADMController implements Initializable{

    /**
     * Initializes the controller class.
     */

    @FXML
    private AnchorPane paneConteudo;

    private AnchorPane diario, ensalamento, painelDeControle;

    private JFXButton btnSair;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            diario = FXMLLoader.load(getClass().getResource("../view/Diario.fxml"));
            ensalamento = FXMLLoader.load(getClass().getResource("../view/EnsalamentoADM.fxml"));
            painelDeControle = FXMLLoader.load(getClass().getResource("../view/PainelDeControle.fxml"));
            setNode(diario);
        } catch (IOException ex) {
            Logger.getLogger(DashboardADMController.class.getName()).log(Level.SEVERE, null, ex);
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
    void btnPainelDeControleAction() {
        setNode(painelDeControle);
    }

    @FXML
    public void btnSairAction(ActionEvent event){
        try {
            Alert alertDel = new Alert(Alert.AlertType.CONFIRMATION);
            alertDel.setTitle("Sair do Sistema");
            alertDel.setHeaderText(null);
            alertDel.setContentText("Deseja mesmo sair do sistema?");
            Optional<ButtonType> btnAct = alertDel.showAndWait();
            if(btnAct.get() == ButtonType.OK){
                ((Node)event.getSource()).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                stage.show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
