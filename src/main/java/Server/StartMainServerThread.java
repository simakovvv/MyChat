package Server;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

import static Server.MainServer.controllerServer;

public class StartMainServerThread extends Thread  implements Runnable {

// Этот метод выполняется в параллельном потоке и всё время слушает новые подлючения.
//Если слышит, создаёт поток - демон
    public void run() {

        ServerSocket serverSocket = null;

        try {
            try {
                int i = 0; // Счётчик подключений
                // Подключение сокета к local host
                InetAddress inetAddress = InetAddress.getByName("localhost");
                serverSocket = new ServerSocket(Server.getPort(), 0, inetAddress);

                System.out.println("Server started\n\n");
                controllerServer.setText("Server " + ControllerServer.Time() + ": Server started\n\n");

                while(true){
                    //Ожидание подключения

                    Socket socket = serverSocket.accept();

                    System.err.println("Server " + ControllerServer.Time() + "Client Accepted");
                    controllerServer.setText("Server " + ControllerServer.Time() + "Client Accepted\n");
                    //Запускаем отдельный поток для работы с клиентом
                    new Server().setSocket(i++, socket);


                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            try{
                if(serverSocket != null){
                    serverSocket.close();


                }else
                    System.out.println("dfff");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

}
