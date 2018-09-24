package Client;


import javafx.application.Platform;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static Client.MainClient.controllerClient;

public class Client extends Thread {
    private static final int serverPort = 6666;
    private static final String locahost = "127.0.0.1";
    private TextField clientInputField;
    private ObjectOutputStream oos = null;
    private ObjectInputStream oin = null;


    public void startClient(TextField clientInputField){
        setDaemon(true);

        setPriority(NORM_PRIORITY);

        start();
        this.clientInputField = clientInputField;
    }




    public void run() {


       // Stage stage = new MainClient().getStage();

        Socket socket = null;



        try{
            try{

                System.out.println("Client side is started\n" +
                        "Connecting to the server\n\t" +
                        "(IP address " + locahost +
                        ", port " + serverPort + ")");

                controllerClient.setText(controllerClient.getName() + controllerClient.Time()+ ": "
                        + "Client side is started\n" +
                        "Connecting to the server " +
                        "(IP address " + locahost +
                        ", port " + serverPort + ")\n");


                InetAddress ipAddress = InetAddress.getByName(locahost);
                socket = new Socket(ipAddress,serverPort);
                System.out.println("The connection is established.");
                controllerClient.setText(controllerClient.getName() + controllerClient.Time()+ ": "
                        +"The connection is established.\n");

                System.out.println("\tLocalPort = " + socket.getLocalPort() +
                        "\n\tInetAddress.HostAddress = " + socket.getInetAddress() +
                        "\n\tReseiveBufferSize (SO_RCVBUF) = " + socket.getReceiveBufferSize());
                controllerClient.setText(controllerClient.getName() + controllerClient.Time()+ ": "
                        +"\n\tLocalPort = " + socket.getLocalPort() +
                        "\n\tInetAddress.HostAddress = " + socket.getInetAddress() +
                        "\n\tReseiveBufferSize (SO_RCVBUF) = " + socket.getReceiveBufferSize()+"\n");

                // Создаём входной и выходной потоки дя обмена сообщениями с сервером
               /* InputStream sin = socket.getInputStream();
                OutputStream sout = socket.getOutputStream();
                DataInputStream in = new DataInputStream(sin);
                DataOutputStream out = new DataOutputStream(sout);*/


                // Создаём входной и выходной потоки дя обмена объектами с сервером

                oos = new ObjectOutputStream(socket.getOutputStream());

                oin = new ObjectInputStream(socket.getInputStream());



                // Создаём поток для чтения с клавиатуры
                //InputStreamReader isr = new InputStreamReader(System.in);
                //BufferedReader keyboard = new BufferedReader(isr);
                String line = null;
                Object msg = null;
                System.out.println("Type in something and press Enter");
                controllerClient.setText(controllerClient.getName() + controllerClient.Time()+ ": "
                        +"Type in something and press Enter\n");
                System.out.println();

                while (true){

                    // Обработка ввода пользователя
                    //line = keyboard.readLine();


                    if(!controllerClient.getClientStack().empty()) {

                        msg = controllerClient.getClientStack().pop();
                        oos.writeObject(msg);
                        oos.flush();

                        try {

                            msg = oin.readObject();

                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                        if (msg instanceof ClientModel) {
                            // В  этом месте отлавливаем пользователей на авторизацию
                            if (((ClientModel) msg).getValid() && !((ClientModel) msg).getRegister()) {
                                controllerClient.getThisUser().setValid(true);
                                controllerClient.setText("Server " + controllerClient.Time() + "Login successful\n");
                            }
                            //В этом на регистрацию
                            if (((ClientModel) msg).getValid() && ((ClientModel) msg).getRegister()) {
                                controllerClient.getThisUser().setValid(true);
                                controllerClient.setText("Server " + controllerClient.Time() + "Registration successful\n");
                            }

                        }
                        if (msg instanceof String) {
                            //смотрим, сообщения, если они строковые
                            line = (String) msg;
                            oos.writeObject(line);// отсылаем строку серверу
                            oos.flush(); // завершаем поток
                            try {
                                msg = oin.readObject();// Ждём ответ от сервера
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }

                            if (msg.toString().endsWith("quit")) {
                                Platform.exit();

                            } else {
                                System.out.println("The server sent me this line: \n\t" + line);
                                controllerClient.setText("Server " + controllerClient.Time() + ": "
                                        + line + "\n");
                            }
                        }


                    }
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try{
                if(socket != null) {
                    oos.close();
                    oin.close();
                    socket.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

