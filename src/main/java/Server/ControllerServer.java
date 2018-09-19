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

public class ControllerServer  {




    @FXML
    private TextArea serverOutput;

    @FXML
    private TextField clientInput;

    @FXML
    private Button sendButton;

    @FXML
    private Button startServer;

    @FXML
    private Tab serverPageFlag;



    public ControllerServer() {
    }



    public void setText(String data){
        serverOutput.appendText(data);
    }



    public void startServer() {


        StartServer startServerThread = new StartServer();
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



   /* @FXML
    private void onActionButton(ActionEvent event) {

        if(!clientInput.getText().isEmpty()) {//если поле ввода не пустое
            clientOutput.appendText(clientPageFlag.getText() + " [" + Time()
                    + "]" + " : " + clientInput.getText() + "\n");
        } else {
            clientOutput.appendText("Enter the command\n");
        }
        clientInput.setText("");

        clientInput.clear();

    }*/


    /*@FXML
    public void textAction(KeyEvent e) {// Отправка по нажатию Enter

        if (e.getCode().equals(KeyCode.ENTER))
            onActionButton(new ActionEvent());

    }
*/
}
