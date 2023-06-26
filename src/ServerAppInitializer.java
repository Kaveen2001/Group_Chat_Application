import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerAppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("lk/ijse/socket/view/ServerUIForm.fxml"))));
            primaryStage.setTitle("Home.");
            primaryStage.show();
            primaryStage.setResizable(true);
            primaryStage.setOnCloseRequest(event -> {
                System.exit(0);
            });
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error while loading the Login Client UI : " + e.getLocalizedMessage()).show();
        }
    }
}
