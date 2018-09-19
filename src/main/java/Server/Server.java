package Server;



import Client.ClientModel;
import Server.Hibernate.Services.UserService;

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
    private UserService checkUser = new UserService();



    public static int getPort() {
        return port;
    }

    public Server() {    }


    public void setSocket(int num, Socket socket){
        // определение значений
        this.num = num;
        this.socket = socket;

        //Открытие deamon - потока

        setDaemon(true);

        setPriority(NORM_PRIORITY);

        start();

    }

    public void run(){
        try{


            // Входной и выходной потоки для обмена данными с клиентами
           /* InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();
            DataInputStream dis = new DataInputStream(sin);
            DataOutputStream dos = new DataOutputStream(sout);*/

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());

            String line = null;

            Object msg = null;


            while(true){
                //line = dis.readUTF();
                try {
                    msg = oin.readObject();
                    System.out.println(msg.toString());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                if(msg instanceof ClientModel){


                    if(checkUser.isRegister((ClientModel) msg) && !((ClientModel) msg).getRegister()){
                        ((ClientModel) msg).setValid(true);
                    }
                    if(!checkUser.isRegister((ClientModel) msg) && ((ClientModel) msg).getRegister()){
                        checkUser.saveUser((ClientModel) msg);
                        ((ClientModel) msg).setValid(true);
                    }

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

