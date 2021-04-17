package me.Halflove.Referrals.Managers;

import me.Halflove.Referrals.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseManager {
    public static DatabaseManager instance = new DatabaseManager();
    static Plugin plugin;
    static FileConfiguration data;
    static File datafile;
    static Connection connection;

    //Creates a local data file
    public void setupLocalDatabase(Plugin plugin){
        //Creates a local plugin folder for the plugin if one doesn't exist
        if(!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();

        //Creates a local data file (data.yml) if one doesn't exist
        datafile = new File(plugin.getDataFolder(), "data.yml");
        if(!datafile.exists())
            try {
                datafile.createNewFile();
            } catch (IOException exception){
                Bukkit.getServer().getLogger().severe("Could not create Local Database (data.yml) " + exception);
            }
        data = (FileConfiguration) YamlConfiguration.loadConfiguration(datafile);
    }

    //Set up a connection for MySQL
    public void setConnection(Connection connection) {
        connection = connection;
    }

    //Setup MySQL Database
    public void setupSQLDatabase(){
        //Database information
        String host = "localhost";
        int port = 3306;
        String database = "referrals";
        String username = "root";
        String password = "password";

        //Try to set up connection to database
        try {
            synchronized (this) {
                if (connection != null && !connection.isClosed())
                    return;
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        //Try to create sql table
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " +
                    "UUID (UUID VARCHAR(100)," +
                    "HasPlayed BOOLEAN," +
                    "Referrals BIGINT(100)," +
                    "PRIMARY KEY (UUID))");
            ps.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public static boolean getHasPlayed(String uuid, boolean type){
        return false;
    }

    //Method for updating a player's "HasPlayed" value
    public static void updateHasPlayed(UUID uuid,boolean value){

        //Set SQL value
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE UUID SET HasPlayed=? WHERE UUID=?");
            statement.setBoolean(1, value);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        //Set Local value
        getData().set("players."+uuid.toString()+".hasplayed", value);
        saveData();

    }

    public static void updateReferrals(UUID uuid,long value){

        //Set SQL value
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE UUID SET Referrals=? WHERE UUID=?");
            //statement.setLong(1, value);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        //Set Local value
        getData().set("players."+uuid.toString()+".referrals", value + getData().getLong("players."+uuid.toString()+".referrals"));
        saveData();

    }

    //Get local data file
    public static FileConfiguration getData(){ return data; }

    //Save local data file
    public static void saveData(){
        try {
            data.save(datafile);
        } catch (IOException exception) {
            Bukkit.getServer().getLogger().severe("Could not save data.yml! " + exception);
        }
    }

    //Reload local data file
    public static void reloadData(){ data = (FileConfiguration) YamlConfiguration.loadConfiguration(datafile); }

}
