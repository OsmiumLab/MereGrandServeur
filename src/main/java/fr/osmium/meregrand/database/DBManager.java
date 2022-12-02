package fr.osmium.meregrand.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public void insertInfos(String mail, String pwd) {
        mail = Sha256.hash(mail);
        pwd = Sha256.hash(pwd);
        String request = "INSERT INTO USER(email,password) VALUES(?,?)";
        try (PreparedStatement statement = connect.prepareStatement(request)) {
            statement.setString(1, mail);
            statement.setString(2, pwd);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmail(String newMail, String oldMail) {
        newMail = Sha256.hash(newMail);
        oldMail = Sha256.hash(oldMail);
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
        mail = Sha256.hash(mail);
        String request = "UPDATE User SET password=? WHERE email=?";
        try (PreparedStatement statement = connect.prepareStatement(request)) {
            statement.setString(1, pwd);
            statement.setString(2, mail);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String mail) {
        mail = Sha256.hash(mail);
        String request = "DELETE FROM User WHERE email=?";
        try (PreparedStatement statement = connect.prepareStatement(request)) {
            statement.setString(1, mail);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
