package UserModels.Hibernate;

import javax.persistence.*;

@Entity
@Table(name = "userid", schema = "mysql", catalog = "")
public class UseridEntity {
    private int id;
    private String name;
    private String password;
    private String email;
    private String timeOfStartSession;
    private Byte register;
    private Byte isValid;
    private Byte isOnline;

    public UseridEntity(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.timeOfStartSession = "какое - то время";
        this.register = 1;
        this.isValid = 1;
        this.isOnline = 1;

    }

    public UseridEntity() {
    }

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name", nullable = true, length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Password", nullable = true, length = 40)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "Email", nullable = true, length = 40)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "timeOfStartSession", nullable = true, length = 40)
    public String getTimeOfStartSession() {
        return timeOfStartSession;
    }

    public void setTimeOfStartSession(String timeOfStartSession) {
        this.timeOfStartSession = timeOfStartSession;
    }

    @Basic
    @Column(name = "register", nullable = true)
    public Byte getRegister() {
        return register;
    }

    public void setRegister(Byte register) {
        this.register = register;
    }

    @Basic
    @Column(name = "isValid", nullable = true)
    public Byte getIsValid() {
        return isValid;
    }

    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }

    @Basic
    @Column(name = "isOnline", nullable = true)
    public Byte getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Byte isOnline) {
        this.isOnline = isOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UseridEntity that = (UseridEntity) o;


        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);

        return result;
    }
}
