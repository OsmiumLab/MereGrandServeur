package fr.osmium.meregrand.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDB {
    private static ConfigDB instance;

    public static ConfigDB getInstance(){
        if(instance==null){
            instance=new ConfigDB();
        }
        return instance;
    }

    public Connection getConnection(){
        Connection connect=null;
        try{
            connect= DriverManager.getConnection("jdbc:sqlite:src/main/resources/DBLittleRedHood.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }
}
