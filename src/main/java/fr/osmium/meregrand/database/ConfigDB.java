package fr.osmium.meregrand.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDB {

    private static ConfigDB instance;

    public Connection getConnection(){
        Connection connect = null;
        try{
            connect = DriverManager.getConnection("jdbc:sqlite:DBLittleRedHood.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }

    public static ConfigDB getInstance() {
        if (instance == null) instance = new ConfigDB();
        return instance;
    }

}
