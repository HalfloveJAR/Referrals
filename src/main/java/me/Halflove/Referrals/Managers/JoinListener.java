package me.Halflove.Referrals.Managers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        DatabaseManager.updateReferrals(player.getUniqueId(), 0);
        if(player.hasPlayedBefore() && !DatabaseManager.hasPlayedBefore(player.getUniqueId()))
            DatabaseManager.updateHasPlayedBefore(player.getUniqueId(), true);
    }

}
