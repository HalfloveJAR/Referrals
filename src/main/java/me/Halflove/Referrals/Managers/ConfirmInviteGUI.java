package me.Halflove.Referrals.Managers;

import me.Halflove.Referrals.me.Halflove.Referrals.Utils.SignMenuFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.Arrays;

public class ConfirmInviteGUI implements Listener {

    public static SignMenuFactory signMenuFactory;
    public SignMenuFactory getSignMenuFactory(){
        return this.signMenuFactory;
    }

    public static void openConfirmationGUI(Player target){
        SignMenuFactory.Menu menu = signMenuFactory.newMenu(Arrays.asList("", "", "^^^", "Player name")).response((player, strings) -> {
                    if (!strings[0].equalsIgnoreCase("")) {
                        ReferralManager.confirmInvitation(target, strings[0]);
                        return true;
                    }
                    return true;
                });

        menu.open(target);
    }

}
