package fr.osmium.meregrand.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {

    private static DBManager instance;
    private Connection connect;

    private DBManager() {
        connect = ConfigDB.getInstance().getConnection();
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public void insertInfos(String mail, String pwd, String publicKey) {
        pwd = Sha256.hash(pwd);
        String request = "INSERT INTO USER(email,password,publicKey) VALUES(?,?,?)";
        try (PreparedStatement statement = connect.prepareStatement(request)) {
            statement.setString(1, mail);
            statement.setString(2,pwd);
            statement.setString(3,publicKey);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmail(String newMail, String oldMail) {
        String request = "UPDATE User SET email=? WHERE email=?";
        try (PreparedStatement statement = connect.prepareStatement(request)) {
            statement.setString(1, newMail);
            statement.setString(2, oldMail);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(String pwd, String mail) {
        pwd = Sha256.hash(pwd);
        String request = "UPDATE User SET password=? WHERE email=?";
        try (PreparedStatement statement = connect.prepareStatement(request)) {
            statement.setString(1, pwd);
            statement.setString(2, mail);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPublicKey(String mail){
        String request="SELECT publicKey FROM User WHERE email=?";
        try (PreparedStatement statement = connect.prepareStatement(request)) {
            statement.setString(1, mail);
            try(ResultSet result=statement.executeQuery()){
                result.next();
                return result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(String mail) {
        String request = "DELETE FROM User WHERE email=?";
        try (PreparedStatement statement = connect.prepareStatement(request)) {
            statement.setString(1, mail);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateIp(String mail, String ip){
        String request="UPDATE User SET ipAdress=? WHERE email=?";
        try (PreparedStatement statement = connect.prepareStatement(request)) {
            statement.setString(1, ip);
            statement.setString(2, mail);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getIp(String mail){
        String request="SELECT ipAdress FROM User WHERE email=?";
        try (PreparedStatement statement = connect.prepareStatement(request)) {
            statement.setString(1, mail);
            try(ResultSet result=statement.executeQuery()){
                result.next();
                return result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
