package Server;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;


public class MainServer extends Application {

    static ControllerServer controllerServer;




    @Override
    public void stop() throws Exception {



    }
    @Override
    public void start(Stage primaryStage) throws Exception{



        FXMLLoader fxmlLoader = new FXMLLoader();
        //Parent root = fxmlLoader.load(getClass().getResource("sampleClient.fxml"));
        GridPane root = fxmlLoader.load(getClass().getClassLoader().getResource("sampleServer.fxml").openStream());
        controllerServer = fxmlLoader.getController();
        primaryStage.setTitle("MyChat");
        primaryStage.setScene(new Scene(root, 364, 428));

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                StartServer.interrupted();

                System.exit(0);

            }
        });

        TextArea serverTextArea = (TextArea) root.lookup("#serverOutput");

        serverTextArea.setVisible(false);

        primaryStage.show();





    }


    public static void main(String[] args) {

        launch(args);//start GUI


    }

}
