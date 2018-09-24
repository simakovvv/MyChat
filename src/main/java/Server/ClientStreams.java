package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientStreams {
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private int clientNum;
    private String clientName;


    public void setClientData(int clientNum, String clientName) {
        this.clientNum = clientNum;
        this.clientName = clientName;
    }
    public void setClientStreams(ObjectOutputStream oos, ObjectInputStream ois) {
        this.oos = oos;
        this.ois = ois;
    }
    public int getClientNum() {
        return clientNum;
    }

    public void setClientNum(int clientNum) {
        this.clientNum = clientNum;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }



    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }
}
