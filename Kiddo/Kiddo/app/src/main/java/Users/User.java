package Users;

import UI.Conversation;
import java.sql.* ;
import javax.sql.DataSource;

/**
 * Created by Jason Qian on 13/11/2016.
 */

public abstract class User {

    public static Connection con;
    private String username;
    private String email;
    private int id;

    public User(String username, String email){
        this.username = username;
        this.email = email;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException e) {
        } catch (IllegalAccessException e){
        } catch (InstantiationException e){
        }
        String host = "jdbc:mysql://35.160.73.118";
        //ec2-35-160-73-118.us-west-2.compute.amazonaws.com
        String user = "guest";
        String pass = "guest";
        try {
            con = DriverManager.getConnection(host, user, pass);
        } catch (SQLException e) {
        }
    }

    public void sendMessage (Conversation conversation, User sentBy, User sentTo, String contents, byte[] file){
        conversation.sendMessage(sentBy, sentTo, contents, file);
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public int getID(){
        return this.id;
    }

    public void setID(int id){
        this.id = id;
    }

    public static class UserDoesNotExistException extends Exception{
        public UserDoesNotExistException(){
            super();
        }
    }
}
