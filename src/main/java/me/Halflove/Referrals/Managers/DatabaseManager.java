package me.Halflove.Referrals.Managers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DatabaseManager {
    public static DatabaseManager instance = new DatabaseManager();
    static FileConfiguration data;
    static File datafile;

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

    public static int getTotalReferrals(UUID uuid){
        return getData().getInt("players."+uuid.toString()+".referrals");
    }

    public static void updateReferrals(UUID uuid,long value){
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
