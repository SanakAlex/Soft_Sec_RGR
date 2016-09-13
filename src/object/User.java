package object;

/**
 * Created by Sanak Alex on 27.05.2016.
 */
public class User {
    private String login;
    private double s;
    private double m;

    public User(String login, double s, double m) {
        this.login = login;
        this.s = s;
        this.m = m;
    }

    public String getLogin() {
        return login;
    }

    public double getS() {
        return s;
    }
    public void setS(double s) {
        this.s = s;
    }

    public double getM() {
        return m;
    }
    public void setM(double m) {
        this.m = m;
    }

}
