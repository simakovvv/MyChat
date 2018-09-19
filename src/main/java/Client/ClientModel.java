package Client;

import java.io.Serializable;

public class ClientModel implements Serializable {
    private String Name;
    private String Pasword;
    private String EMail;
    private String timeOfStartSession;

    private boolean register = false;
    private boolean  isValid = false;
    private boolean isOnline = false;

    public ClientModel(String name, String pasword, String eMail)  {
        this.Name = name;
        this.Pasword = pasword;
        this.EMail = eMail;
        this.timeOfStartSession = ControllerClient.Time();
        if(eMail!= null)
          this.register = true;

    }

    public String getTimeOfStartSession() {
        return timeOfStartSession;
    }

    public void setTimeOfStartSession(String timeOfStartSession) {
        this.timeOfStartSession = timeOfStartSession;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public ClientModel(String name, String pasword) {

        Name = name;
        Pasword = pasword;
        this.EMail = "-";
        this.timeOfStartSession = ControllerClient.Time();
    }


    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public Boolean getRegister() {
        return register;
    }

    public void setRegister(Boolean register) {
        this.register = register;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPasword() {
        return Pasword;
    }

    public void setPasword(String pasword) {
        Pasword = pasword;
    }
}
