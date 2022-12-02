package fr.osmium.meregrand.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBVerif {
    private static DBVerif instance;
    private Connection connect;

    private DBVerif(){
        connect=ConfigDB.getInstance().getConnection();
    }

    public static DBVerif getInstance(){
        if(instance==null){
            instance=new DBVerif();
        }
        return instance;
    }

    private boolean mailVerif(String mail){
        String request="SELECT email FROM User WHERE email=?";
        try (PreparedStatement statement = connect.prepareStatement(request)) {
            statement.setString(1, mail);
            try(ResultSet result=statement.executeQuery()){
                return result.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean pwdVerif(String mail, String pwd){
        mail=Sha256.hash(mail);
        if(mailVerif(mail)){
            pwd=Sha256.hash(pwd);
            String request="SELECT password FROM User WHERE email=? AND password=?";
            try (PreparedStatement statement = connect.prepareStatement(request)) {
                statement.setString(1, mail);
                statement.setString(2, pwd);
                try(ResultSet result=statement.executeQuery()){
                    return result.next();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
