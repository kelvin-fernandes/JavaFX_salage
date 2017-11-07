package javafx_salage.controller;

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
    void btnDisciplinasAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/PDCDisciplinas.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initOwner(Main.primaryStage.getOwner());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/appicon.png")));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setTitle("SALAGE - Disciplinas");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnHorasAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/PDCHoras.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initOwner(Main.primaryStage.getOwner());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/appicon.png")));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setTitle("SALAGE - Horas");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnProfessoresAction() {
       try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/PDCProfessores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initOwner(Main.primaryStage.getOwner());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/appicon.png")));
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("SALAGE - Professores");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    void btnSalasAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/PDCSalas.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initOwner(Main.primaryStage.getOwner());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("../images/appicon.png")));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.setTitle("SALAGE - Salas");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnUsuariosAction() {
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
            stage.setTitle("SALAGE - Usuários");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
