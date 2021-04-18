package me.Halflove.Referrals.Managers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class DatabaseManager {
    public static DatabaseManager instance = new DatabaseManager();
    static FileConfiguration data;
    static File datafile;
    static FileConfiguration players;
    static File playerlibrary;

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

        //Creates a local player data file (players.yml) if one doesn't exist
        playerlibrary = new File(plugin.getDataFolder(), "players.yml");
        if(!playerlibrary.exists())
            try {
                playerlibrary.createNewFile();
            } catch (IOException exception){
                Bukkit.getServer().getLogger().severe("Could not create Local Database (players.yml) " + exception);
            }
        players = (FileConfiguration) YamlConfiguration.loadConfiguration(playerlibrary);
    }

    public static List<String> getPlayerList(){
        return getPlayers().getStringList("players");
    }

    public static void addPlayerList(String player){
        List<String> playerList = getPlayerList();
        playerList.add(player);
        getPlayers().set("players", playerList);
    }

    public static boolean hasPlayedBefore(UUID uuid){
        return getData().getBoolean("players."+uuid.toString()+".hasplayedbefore");
    }

    public static int getTotalReferrals(UUID uuid){
        return getData().getInt("players."+uuid.toString()+".referrals");
    }

    public static void updateHasPlayedBefore(UUID uuid, boolean value){
        getData().set("players."+uuid.toString()+".hasplayedbefore", value);
        saveData();
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

    //Get local players data file
    public static FileConfiguration getPlayers(){ return players; }

    //Save local players data file
    public static void savePlayers(){
        try {
            players.save(playerlibrary);
        } catch (IOException exception) {
            Bukkit.getServer().getLogger().severe("Could not save players.yml! " + exception);
        }
    }

    //Reload local players data file
    public static void reloadPlayers(){ players = (FileConfiguration) YamlConfiguration.loadConfiguration(playerlibrary); }

}
