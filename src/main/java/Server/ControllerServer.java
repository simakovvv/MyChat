package Server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ControllerServer  {




    @FXML
    private TextArea serverOutput;

    @FXML
    private TextField serverInput;

    @FXML
    private Button sendButton;


    @FXML
    private Tab serverPageFlag;


    List<ClientStreams> clientsStreams = new LinkedList<>();

    public List<ClientStreams> getClientsStreams() {
        return clientsStreams;
    }

    public ControllerServer() {
    }


    public void setText(String data){
        serverOutput.appendText(data);
    }



    public void startServer() {


        StartMainServerThread startServerThread = new StartMainServerThread();
        Thread serverThread = new Thread(startServerThread);
        serverThread.start();
        serverOutput.setVisible(true);

    }
    public static String Time(){
        long curTime = System.currentTimeMillis();


        Date curDate = new Date(curTime);

        String curStringDate = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(curTime);

        return "[" + curStringDate + "] ";
    }



    @FXML
    private void onActionButton(ActionEvent event) {

        if(!serverInput.getText().isEmpty()) {//если поле ввода не пустое
            serverOutput.appendText(serverPageFlag.getText() +  Time()
                    + " : " + serverInput.getText() + "\n");




        } else {
            serverOutput.appendText("Enter the command\n");
        }
        serverInput.setText("");

        serverInput.clear();

    }


    @FXML
    public void textAction(KeyEvent e) {// Отправка по нажатию Enter

        if (e.getCode().equals(KeyCode.ENTER))
            onActionButton(new ActionEvent());

    }
}
