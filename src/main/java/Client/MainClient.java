package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


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
