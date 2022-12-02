package fr.osmium.meregrand.database;

import fr.osmium.meregrand.cipher.hash.SHA256;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseVerifier {
    private static DatabaseVerifier instance;
    private Connection connect;

    private DatabaseVerifier(){
        connect=ConfigDB.getInstance().getConnection();
    }

    public static DatabaseVerifier getInstance(){
        if(instance==null){
            instance=new DatabaseVerifier();
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

    public boolean checkPassword(String mail, String pwd){
        if(mailVerif(mail)){
            pwd= SHA256.hash(pwd);
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

