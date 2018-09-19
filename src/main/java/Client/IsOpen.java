package Client;



import java.io.IOException;
import java.net.*;

public class IsOpen {


    public static boolean isConnected() {
        boolean connected = false;
        //TODO Переделать на глобальные переменные
        Socket socket = null;
        String ip = "localhost";
        int port = 6666;
        while (true) {
            try {
                socket = new Socket(ip, port);
                connected = true;
            } catch (UnknownHostException ex) {
                connected = false;
            } catch (IOException ex) {
                connected = false;
            }
            if (connected) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    // pofigu ...
                }
                // System.exec(...);
                break;
            } else {
                try {
                    Thread.sleep(500); // 0.5 sec
                    break;
                } catch (InterruptedException ex) {
                    break;
                }
            }
        }
        return connected;
    }
}

