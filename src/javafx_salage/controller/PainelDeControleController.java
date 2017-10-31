package javafx_salage.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx_salage.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author kelvin-fernandes
 */

public class PainelDeControleController implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void btnDisciplinasAction(ActionEvent event) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/PDCDisciplinas.fxml"));
//            Parent root = fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.initOwner(Main.primaryStage.getOwner());
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/appicon.png")));
//            stage.setScene(new Scene(root));
//            stage.setResizable(false);
//            stage.centerOnScreen();
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void btnHorasAction(ActionEvent event) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/PDCHoras.fxml"));
//            Parent root = fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.initOwner(Main.primaryStage.getOwner());
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/appicon.png")));
//            stage.setScene(new Scene(root));
//            stage.setResizable(false);
//            stage.centerOnScreen();
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void btnProfessoresAction(ActionEvent event) {
//       try {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/PDCProfessores.fxml"));
//        Parent root = fxmlLoader.load();
//        Stage stage = new Stage();
//        stage.initOwner(Main.primaryStage.getOwner());
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/appicon.png")));
//        stage.setScene(new Scene(root));
//        stage.setResizable(false);
//        stage.centerOnScreen();
//        stage.show();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
    }

    @FXML
    void btnSalasAction(ActionEvent event) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/PDCSalas.fxml"));
//            Parent root = fxmlLoader.load();
//            Stage stage = new Stage();
//            stage.initOwner(Main.primaryStage.getOwner());
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/appicon.png")));
//            stage.setScene(new Scene(root));
//            stage.setResizable(false);
//            stage.centerOnScreen();
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void btnUsuariosAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/PDCUsuarios.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initOwner(Main.primaryStage.getOwner());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/appicon.png")));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
