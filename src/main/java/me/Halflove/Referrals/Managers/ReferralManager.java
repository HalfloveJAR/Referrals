package me.Halflove.Referrals.Managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReferralManager {

    public static void confirmInvitation(Player player, String target){

        /*Player = Newbie
        Target = Person who invited Newbie*/
        if(Bukkit.getPlayer(target) != null){
            if(!DatabaseManager.hasPlayedBefore(player.getUniqueId())){
                if(!Bukkit.getPlayer(target).equals(player)){
                    Bukkit.broadcastMessage(Bukkit.getPlayer(target).getName() + " invited " + player.getName());
                    DatabaseManager.updateReferrals(player.getUniqueId(), 1);
                    DatabaseManager.updateHasPlayedBefore(player.getUniqueId(), true);
                }else{
                    player.sendMessage("You can't invite yourself.");
                }
            }else{
                player.sendMessage("You can't do this, you've played before.");
            }
        }else {
            player.sendMessage("Player not online");
        }

        if(Bukkit.getOfflinePlayer(target) != null){
            Bukkit.broadcastMessage("FUCK ME");
        }

    }

}
