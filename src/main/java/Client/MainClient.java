package Client;

import Server.StartServer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class MainClient extends Application {

    //TODO добавить геттеры
    static ControllerClient controllerClient;

    private Stage stage;

    public Stage getStage(){
        return stage;
    }

    @Override
    public void stop() throws Exception {


    }
    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader();
        GridPane root = fxmlLoader.load(getClass().getClassLoader().getResource("sampleClient.fxml").openStream());
        controllerClient = fxmlLoader.getController();
        primaryStage.setTitle("MyChat");
        primaryStage.setScene(new Scene(root, 364, 428));

        TextField clientInputField = (TextField) root.lookup("#clientInput");

        controllerClient.createLoginMenu(controllerClient.clientFlag());

        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }


}
