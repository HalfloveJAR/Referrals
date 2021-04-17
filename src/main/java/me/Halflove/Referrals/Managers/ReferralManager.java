package me.Halflove.Referrals.Managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReferralManager {

    public static void confirmInvitation(Player player, Player target){

        /*Player = Newbie
        Target = Person who invited Newbie*/
        Bukkit.broadcastMessage(target.getName() + " invited " + player.getName());

    }

}
