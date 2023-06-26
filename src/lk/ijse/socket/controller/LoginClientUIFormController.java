package lk.ijse.socket.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.socket.controller.client.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

public class LoginClientUIFormController {
    public AnchorPane ClientLoginFormContext;
    public ImageView imageView;
    public TextField txtClientName;
    public Button btnClientConnect;

    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    public static ArrayList<User> users = new ArrayList<>();
    public static HashSet<String> LoggedInUsers = new HashSet<>();
    public static String userName;


    public void btnClientConnectOnAction(ActionEvent actionEvent) {
        boolean checkUserResult = checkUser(txtClientName.getText());
        System.out.println(txtClientName.getText() + " txtid ******  " + checkUserResult);
        if (checkUserResult) {
            loadChat();
        } else {
            new Alert(Alert.AlertType.ERROR, "User Name Already exsist..!").show();
            txtClientName.setText("");
        }
        for (User user : users) {
            System.out.println("onnnn " + user.name);
        }
    }

    private boolean checkUser(String username) {
        System.out.println("loop out");

        for (User user : users) {
            System.out.println("loop in");
            System.out.println(user.name + ": ");

            if (user.name.equalsIgnoreCase(username)) {
                return false;
            }
        }
        userName = username;
        users.add(new User(userName));
        return true;
    }

    private void loadChat() {
        try {
            Stage exitstage = (Stage) btnClientConnect.getScene().getWindow();
            URL resource = this.getClass().getResource("/lk/ijse/socket/view/ClientUIForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Scene scene = new Scene(load);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
            exitstage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
