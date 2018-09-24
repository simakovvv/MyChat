package Client;



import UserModels.ClientModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

public class ControllerClient {




    @FXML
    private Button addChat;




    @FXML
    private Text clientName;

    @FXML
    private TabPane tabPaneBoard;

    @FXML
    private Tab serverPageFlag;


    @FXML
    private TextField clientInput;

    @FXML
    private Button sendButton;



    private TextArea clientOutput;
    private TextField clientNameField;
    private PasswordField clientPswdField;
    private TextField emailField;
    private ClientModel thisUser;
    private Label sysMsg;
    private Label label;






    private Stack clientStack = new Stack();

    private List<Tab> tabList = new ArrayList<Tab>();

    public ControllerClient() {
    }


    public ClientModel getThisUser() {
        return thisUser;
    }

    public void setThisUser(ClientModel thisUser) {
        this.thisUser = thisUser;
    }

    public Stack getClientStack(){
        return clientStack;
    }

    public void setText(String data){
        clientOutput.appendText(data);
    }
    public void setSysMsg(String data){
        sysMsg.setText(data);
    }

    public  String getName(){
        return clientName.getText();
    }




    public Tab clientFlag(){
        return serverPageFlag;
    }

    public static String Time(){
        long curTime = System.currentTimeMillis();


        Date curDate = new Date(curTime);

        String curStringDate = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(curTime);

        return "[" + curStringDate + "] ";
    }

    //меню добавления собеседника
    @FXML
    private void addNewChatButton(ActionEvent event) {

        //TODO Здесь будет реализация общеня p2p

        Tab tab = new Tab();
        tabPaneBoard.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        tab.setClosable(true);
        tab.setText("new tab");
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);//перенос текста
        textArea.setVisible(false);
        tabPaneBoard.getTabs().add(tab);
        tabPaneBoard.getSelectionModel().select(tab);

        //createLoginMenu(tab);


    }
    // Формирование меню логина
    public void createLoginMenu(Tab tab){
        tabList.add(tab);
        sendButton.setVisible(false);
        clientInput.setVisible(false);
        VBox vBox = new VBox();

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(0,80,0,80));
        Label label = new Label("Login as:");
        label.setFont(Font.font(20));
        label.setTextFill(Paint.valueOf("#f2f1f1"));
        this.label = label;

        Label sysMessage = new Label();
        sysMessage.setFont(Font.font(15));
        sysMessage.setTextFill(Paint.valueOf("#f2f1f1"));
        this.sysMsg = sysMessage;

        TextField textField = new TextField();
        this.clientNameField = textField;
        textField.setPromptText("name");

        PasswordField passwordField = new PasswordField();
        this.clientPswdField = passwordField;
        passwordField.setPromptText("password");
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        Button submit = new Button();
        submit.setOnAction(event -> validation(new ActionEvent()));
        textField.setOnKeyPressed(event -> validationEnter(event));
        passwordField.setOnKeyPressed(event -> validationEnter(event));
        submit.setText("submit");
        Button register = new Button();
        register.setText("register");
        register.setOnAction(event -> registration(new ActionEvent(),tab));
        hBox.getChildren().addAll(submit,register);
        hBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(label,textField,passwordField,hBox,sysMessage);
        tab.setContent(vBox);



    }



    @FXML
    private void onActionButton(ActionEvent event) {

        if(!clientInput.getText().isEmpty()) {//если поле ввода не пустое
            clientOutput.appendText(clientName.getText() + Time()
                    + ": " + clientInput.getText() + "\n");


            clientStack.push(clientInput.getText());//добавление команды в очередь отправки


        } else {
            clientOutput.appendText("Enter the command\n");
        }
        clientInput.setText("");

        clientInput.clear();

    }


    @FXML
    public void textAction(KeyEvent e) {// Отправка сообщения по нажатию Enter

        if (e.getCode().equals(KeyCode.ENTER))
            onActionButton(new ActionEvent());

    }

    //метод проверки логина и правильности ввода
    @FXML
    private void validation(ActionEvent actionEvent){// Начало диалога с сервером
        //TODO получить ответ от сервера

        thisUser = new ClientModel(clientNameField.getText(),clientPswdField.getText(),
                label.getText().equals("Register as:") ? emailField.getText() : null);

        //окно общения с сервером
        TextArea outputField = new TextArea();
        outputField.setWrapText(true);
        clientOutput = outputField;



        //TODO дописать контроль полей ввода
        if(clientNameField.getText().isEmpty() && clientPswdField.getText().isEmpty()) {
            sysMsg.setText("Name and Password is empty.");
        } else if(clientNameField.getText().isEmpty()){
            sysMsg.setText("Name is empty.");
        } else if(clientPswdField.getText().isEmpty()){
            sysMsg.setText("Password is empty.");
        } else if(clientNameField.getText().length() > 40) {
            sysMsg.setText("Name is too large.");
        } else if(clientPswdField.getText().length() > 40) {
            sysMsg.setText("Password is too large.");
        }else if(clientNameField.getText().isEmpty() && clientPswdField.getText().isEmpty() && emailField.getText().isEmpty() && label.getText().equals("Register as:")) {
            sysMsg.setText("Name, Password and E-mail is empty.");
        } else /*if(emailField.getText().isEmpty() && label.getText().equals("Register as:")) {
            sysMsg.setText("E-mail is empty.");
        } else if(emailField.getText().length() > 40 && label.getText().equals("Register as:")) {
            sysMsg.setText("E-mail is too large.");
        }else */if(true) {

            //отправили на проверку профиль, созданный в начале метода
            clientStack.push(thisUser);

            try {
                Client client = new Client();
                client.startClient(clientInput);
                Thread.sleep(3000);
                if (thisUser.getValid()) {
                     setDialogExposition();
                } else {
                    sysMsg.setText(label.getText().equals("Register as:") ? "Such a user exists" :"User does not exsist .");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //TODO вместо sleep вставить анимацию
    }
    //метод формирования окна диалога
    public void setDialogExposition(){

        Tab tab = tabList.get(tabPaneBoard.getSelectionModel().getSelectedIndex());
        tab.getContent().setVisible(false);
        tab.setContent(clientOutput);
        sendButton.setVisible(true);
        clientInput.setVisible(true);

        addChat.setVisible(true);

        clientName.setText(clientNameField.getText());

    }


    @FXML
    public void validationEnter(KeyEvent e) {// Отправка формы регистрации по нажатию Enter

        if (e.getCode().equals(KeyCode.ENTER))
            validation(new ActionEvent());

    }


    @FXML
    private void registration(ActionEvent actionEvent, Tab tab){// Начало диалога с сервером

        tab.getContent().setVisible(false);
        VBox vBox = new VBox();

        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        vBox.setPadding(new Insets(0,80,0,80));
        Label label = new Label("Register as:");
        label.setFont(Font.font(20));
        label.setTextFill(Paint.valueOf("#f2f1f1"));
        this.label = label;

        Label sysMessage = new Label();
        sysMessage.setFont(Font.font(15));
        sysMessage.setTextFill(Paint.valueOf("#f2f1f1"));
        this.sysMsg = sysMessage;

        TextField name = new TextField();
        this.clientNameField = name;
        name.setPromptText("name");

        PasswordField passwordField = new PasswordField();
        this.clientPswdField = passwordField;
        passwordField.setPromptText("password");

        TextField emailField = new TextField();
        this.emailField = emailField;
        emailField.setPromptText("e-mail");

        Button submit = new Button();
        submit.setText("submit");

        submit.setOnAction(event -> validation(new ActionEvent()));
        name.setOnKeyPressed(event -> validationEnter(event));
        passwordField.setOnKeyPressed(event -> validationEnter(event));
        emailField.setOnKeyPressed(event -> validationEnter(event));

        vBox.getChildren().addAll(label,name,passwordField,emailField,submit,sysMessage);
        tab.setContent(vBox);
    }


}
