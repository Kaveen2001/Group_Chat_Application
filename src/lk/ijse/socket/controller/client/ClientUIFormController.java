package lk.ijse.socket.controller.client;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.socket.controller.LoginClientUIFormController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientUIFormController extends Thread{
    public AnchorPane ClientUIFormContext;
    public TextField txtMessage;
    public Text txtClientName;
    public Pane emogiPane;
    public Button btnSendEmoji;
    public Button btnSendAttachment;
    public Button btnSendMessage;
    public Button btnClientLogOut;
    public TextArea txtMessageArea;
    public ImageView imageConnectClient;

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    BufferedReader in;
    PrintWriter writer;
    int filesize = 6022386; // filesize temporary hardcoded
    public static ArrayList<User> users = new ArrayList<>();
    public static String userName;

    long start = System.currentTimeMillis();
    int bytesRead;
    int current = 0;
    private LoginClientUIFormController loginClientUIFormController;
    public static final int BUFFER_SIZE = 500102;

    public void initialize() {
        connectSocket();
        txtClientName.setText(LoginClientUIFormController.userName);
    }

    private void connectSocket() {
        try {
            socket = new Socket("localhost", 5000);
            System.out.println("Connect with server...!");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            writer.println(LoginClientUIFormController.userName + " Added...!");

            this.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                String msg = in.readLine();
                txtMessageArea.appendText(msg + "\n");
                {
                    if (msg.endsWith("jpg")) {
                        rece(msg);
                        System.out.println("image in");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void emoji1OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\uDE03");
    }

    public void emoji2OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83E\uDD17");
    }

    public void emoji3OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("❤️❤️");
    }

    public void emoji4OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\uDE32");
    }

    public void emoji5OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\uDE02");
    }

    public void emoji6OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\uDE25");
    }

    public void emoji7OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\uDE2B");
    }

    public void emoji8OnAction(MouseEvent mouseEvent) {
        txtMessage.appendText("\uD83D\uDE24");
    }


    public void btnSendEmojiOnAction(ActionEvent actionEvent) {
        if (!emogiPane.isVisible()) {
            emogiPane.setVisible(true);
        } else {
            emogiPane.setVisible(false);
        }
    }

    public void btnSendAttachmentOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) btnSendAttachment.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            txtMessage.setText(selectedFile.getAbsolutePath());
        }
        send(txtMessage.getText());
    }

    public void send(String file_name) throws IOException {
        File myFile = new File(file_name);
        byte[] mybytearray = new byte[(int) myFile.length()];
        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(mybytearray, 0, mybytearray.length);
        OutputStream os = socket.getOutputStream();
        System.out.println("Sending...");
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();
    }

    private void se(String fileName) throws IOException {
        int flag = 0, i;
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        String extn = "";
        for (i = 0; i < fileName.length(); i++) {
            if (fileName.charAt(i) == '.' || flag == 1) {
                flag = 1;
                extn += fileName.charAt(i);
            }
        }

        if (extn.equals(".jpg") || extn.equals(".png")) {
            try {

                File file = new File(fileName);
                FileInputStream fin = new FileInputStream(file);
                dataOutputStream.writeUTF(fileName);
                System.out.println("Sending image...");
                byte[] readData = new byte[1024];

                while ((i = fin.read(readData)) != -1) {
                    dataOutputStream.write(readData, 0, i);
                }
                System.out.println("Image sent");
                txtMessageArea.appendText("\nImage Has Been Sent");
                fin.close();
            } catch (IOException ex) {
                System.out.println("Image ::" + ex);
            }
        }
    }

    static int i = 0;

    private void r(String fileName) throws IOException {
        String str;
        int flag = 0;
        str = dataInputStream.readUTF();
        if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
            File file = new File("RecievedImage" + str);
            FileOutputStream fout = new FileOutputStream(file);

            //receive and save image from client
            byte[] readData = new byte[1024];
            while ((i = dataInputStream.read(readData)) != -1) {
                fout.write(readData, 0, i);
                if (flag == 1) {
                    System.out.println("Image Has Been Received");
                    flag = 0;
                }
            }
            fout.flush();
            fout.close();
        }
    }

    public void rece(String file) throws IOException {
        byte[] mybytearray = new byte[filesize];
        InputStream is = socket.getInputStream();
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bytesRead = is.read(mybytearray, 0, mybytearray.length);
        current = bytesRead;
        do {
            bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
            if (bytesRead >= 0) current += bytesRead;
        } while (bytesRead > -1);

        bos.write(mybytearray, 0, current);
        bos.flush();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        bos.close();
    }

    public void btnSendMessageOnAction(ActionEvent actionEvent) {
        sendMessage(txtMessage.getText());
        for (User user : LoginClientUIFormController.users) {
            System.out.println(user.name + " btn Sent");
        }
    }

    public void sendMessage(String msg) {
        if (!msg.trim().equalsIgnoreCase("")) {
            writer.println(LoginClientUIFormController.userName + ": " + msg);
            txtMessage.setText("");
            if (msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
                System.exit(0);
            }
        }
    }

    public void btnClientLogOutOnAction(ActionEvent actionEvent) {
        sendMessage("logout...!");
        Stage stage = (Stage) btnClientLogOut.getScene().getWindow();
        stage.close();
    }

    public void imgConnectClient(MouseEvent mouseEvent) {

    }

//    public void imgConnectClient(MouseEvent mouseEvent) {
//    }
}
