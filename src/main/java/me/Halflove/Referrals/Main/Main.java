package me.Halflove.Referrals.Main;

import me.Halflove.Referrals.Managers.*;
import me.Halflove.Referrals.me.Halflove.Referrals.Utils.SignMenuFactory;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public DatabaseManager database = DatabaseManager.instance;
    private SignMenuFactory signMenuFactory;

    public void onEnable(){
        ConfirmInviteGUI.signMenuFactory = new SignMenuFactory(this);
        //database.setupSQLDatabase();
        database.setupLocalDatabase((Plugin) this);
        registerCommands();
        registerEvents();
    }

    public void registerEvents(){
        Bukkit.getPluginManager().registerEvents((Listener) new ReferralGUI(), (Plugin) this);
        Bukkit.getPluginManager().registerEvents((Listener) new JoinListener(), (Plugin) this);
        Bukkit.getPluginManager().registerEvents((Listener) new ConfirmInviteGUI(), (Plugin) this);
    }

    public void registerCommands(){
        getCommand("referrals").setExecutor((CommandExecutor) new ReferralCommand());
    }

}
