package lk.ijse.socket.controller.server;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import lk.ijse.socket.controller.LoginClientUIFormController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerUIFormController {
    public AnchorPane ServerSideChatContent;
    public TextArea txtMessageArea;
    public Button btnClientConnect;
    public Button btnAppSetting;
    public Button btnClientLogOut;

    static ServerSocket serverSocket;
    static Socket socket;
    static DataInputStream dataInputStream;
    static PrintWriter writer;
    static ArrayList<ClientHandler> client = new ArrayList<>();
    String messageIn = "";

    public void initialize() {

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(5000);
                System.out.println("Server Started");
                txtMessageArea.appendText("Server Started...!\n");

                while (true) {

                    socket = serverSocket.accept();
                    System.out.println("socket--> "+socket);
                    txtMessageArea.appendText("Client Accepted!\n");
                    System.out.println("Client Accepted!");

                    ClientHandler cliendHandler = new ClientHandler(socket, client);
                    client.add(cliendHandler);
                    System.out.println(client.toString());
                    cliendHandler.start();
                }

            } catch (IOException e) {

            }
        }).start();
    }

    public void btnClientConnectOnAction(ActionEvent actionEvent) {
    }

    public void btnSettingOnAction(ActionEvent actionEvent) {
    }

    public void btnClientLogOutOnAction(ActionEvent actionEvent) {
    }
}
