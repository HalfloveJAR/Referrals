package me.Halflove.Referrals.Main;

import me.Halflove.Referrals.Managers.DatabaseManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;

public class Main extends JavaPlugin {

    public DatabaseManager database = DatabaseManager.instance;

    public void onEnable(){
        database.setupLocalDatabase((Plugin) this);
        database.setupSQLDatabase();
    }

}
