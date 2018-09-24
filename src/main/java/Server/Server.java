package Server;



import UserModels.ClientModel;
import UserModels.Hibernate.Services.UserService;
import UserModels.ClientStreams;

import java.io.*;
import java.net.*;


import static Server.MainServer.controllerServer;

public class Server extends Thread {
    private static int port = 6666;
    private String IP = "localhost";
    private String TEMP_MSG = "The client '%d' sent me message : ";
    private String TEMP_CONN = "The client '%d' closed the connection";

    private Socket socket;
    private int num;
    //переменная для сохранения и проверки данных логинящихся пользователей
    private UserService checkUser = new UserService();
    // Переменная для хранения входных - выходных потоков подключенных клиентов
    private ClientStreams clientStream = new ClientStreams();



    public static int getPort() {
        return port;
    }

    public Server() {    }


    public void setSocket(int num, Socket socket){
        // определение значений номера соединения и сокета
        this.num = num;
        this.socket = socket;

        // Запись номера клиента в массив доступа к клиентам
        clientStream.setClientNum(num);
        //Открытие deamon - потока

        setDaemon(true);

        setPriority(NORM_PRIORITY);

        start();


    }

    public void run(){
        try{


            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());

            // Запись клиентских потоков ввода- вывода в массив доступа к клиентам
            clientStream.setClientStreams(oos,oin);
            String line = null;

            Object msg = null;


            while(true){

                    try {

                        msg = oin.readObject();


                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }



                if(msg instanceof ClientModel){


                    // отбор пользователей для авторизации
                    if(checkUser.isRegister((ClientModel) msg) && !((ClientModel) msg).getRegister()){
                        ((ClientModel) msg).setValid(true);
                    }
                    // отбор пользователей для регистрации
                    if(!checkUser.isRegister((ClientModel) msg) && ((ClientModel) msg).getRegister()){
                        checkUser.saveUser((ClientModel) msg);
                        ((ClientModel) msg).setValid(true);
                    }
                    //  Запись имени клиента в массив доступа к клиентам
                    clientStream.setClientName(((ClientModel) msg).getName());
                    StartMainServerThread.addUser(clientStream);


                    oos.writeObject(msg);
                    oos.flush();
                }
                if(msg instanceof String) {
                    line = (String) msg;
                    System.out.println(String.format(TEMP_MSG, num) + line);
                    controllerServer.setText("Server " + ControllerServer.Time() + String.format(TEMP_MSG, num) + line + "\n");

                    // System.out.println("Server " + ControllerServer.Time() + " " + " sending back ...\n");
                    controllerServer.setText("Server " + ControllerServer.Time() + " " + " sending back ...\n");


                    // отсылаем клиенту ту же строку
                    oos.writeObject("Server recieve text: " + line);
                    // Завершаем передачу пакета
                    oos.flush();
                    System.out.println();
                    if (line.equalsIgnoreCase("quit")) {
                        socket.close();
                        System.out.println(String.format(TEMP_CONN, num));
                        controllerServer.setText("Server " + ControllerServer.Time() + " " + String.format(TEMP_CONN, num) + "\n");

                        break;
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("Exeption : " + e);
        }
    }





}

