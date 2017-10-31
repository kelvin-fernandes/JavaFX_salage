package javafx_salage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author kelvin-fernandes
 */
public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        try{
            this.primaryStage = stage;
            Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("images/appicon.png")));
            stage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
