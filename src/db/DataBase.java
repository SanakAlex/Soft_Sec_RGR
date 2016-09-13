package db;

import object.User;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Sanak Alex on 27.05.2016.
 */
public class DataBase {
    private ArrayList<User> users;
    private User currentUser;

    public DataBase(){
        users = new ArrayList<>();
        currentUser = null;
    }

    public void addUser(String name, double s, double m){
        User user = new User(name, s, m);
        users.add(user);
    }
    public User searchUser(String name){
        for (User user : users) {
            if (user.getLogin().equals(name)){
                return user;
            }
        }
        return null;
    }
    public boolean checkLogin(String login){
        for (User user:users){
            if (user.getLogin().equals(login)) return true;
        }
        return false;
    }
    public void setCurrentUser(User user){
        currentUser = user;
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public void updateInfo(double s, double m){
        User user = searchUser(currentUser.getLogin());
        user.setM(m);
        user.setS(s);
        currentUser = null;
    }
    public void readFromFile() throws IOException {
        File file = new File("src\\db\\users.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
        String str, login;
        int beginIndex;
        int endIndex;
        double s,m;

        while ((str = in.readLine()) != null){
            endIndex = str.indexOf(" ");
            login = str.substring(0, endIndex);

            beginIndex = endIndex + 1;
            endIndex = str.indexOf(" ", beginIndex);
            s = Double.valueOf(str.substring(beginIndex, endIndex));

            beginIndex = endIndex + 1;
            endIndex = str.length();
            m = Double.valueOf(str.substring(beginIndex, endIndex));

            User user = new User(login, s, m);
            users.add(user);
        }
        in.close();
    }
    public void writeInDb() throws IOException {
        File file = new File("src\\db\\users.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        PrintWriter out = new PrintWriter(file.getAbsoluteFile());
        for (User user : users) {
            out.println(user.getLogin() + " " + user.getS() + " " + user.getM());
        }
        out.close();
    }
}
