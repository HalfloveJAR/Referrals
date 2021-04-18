package me.Halflove.Referrals.Main;

import me.Halflove.Referrals.Managers.DatabaseManager;
import me.Halflove.Referrals.Managers.JoinListener;
import me.Halflove.Referrals.Managers.ReferralCommand;
import me.Halflove.Referrals.Managers.ReferralGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public DatabaseManager database = DatabaseManager.instance;

    public void onEnable(){
        //database.setupSQLDatabase();
        database.setupLocalDatabase((Plugin) this);
        registerCommands();
        registerEvents();
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents((Listener) new ReferralGUI(), (Plugin) this);
        Bukkit.getPluginManager().registerEvents((Listener) new JoinListener(), (Plugin) this);
    }

    public void registerCommands(){
        getCommand("referrals").setExecutor((CommandExecutor) new ReferralCommand());
    }

}
