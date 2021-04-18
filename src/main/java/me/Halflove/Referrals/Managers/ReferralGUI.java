package me.Halflove.Referrals.Managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class ReferralGUI implements Listener {

    public static void openGui(Player player){
        Inventory menu = Bukkit.createInventory(null, 27, "Referrals");

        ItemStack confirmInvite = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta confirmInviteMeta = confirmInvite.getItemMeta();
        confirmInviteMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lConfirm Referral"));
        List<String> confirmInviteLore = new ArrayList<String>();
        confirmInviteLore.add("");
        confirmInviteLore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to reward the person"));
        confirmInviteLore.add(ChatColor.translateAlternateColorCodes('&', "&7who invited you."));
        if(DatabaseManager.hasPlayedBefore(player.getUniqueId())){
            confirmInviteLore.add("");
            confirmInviteLore.add(ChatColor.translateAlternateColorCodes('&', "&cNew Players Only"));
        }
        confirmInviteMeta.setLore(confirmInviteLore);
        confirmInvite.setItemMeta(confirmInviteMeta);

        ItemStack referStats = new ItemStack(Material.MAP);
        ItemMeta referStatsMeta = referStats.getItemMeta();
        referStatsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lTotal Invites"));
        List<String> referStatsLore = new ArrayList<String>();
        referStatsLore.add("");
        referStatsLore.add(ChatColor.translateAlternateColorCodes('&',
                "&7People Referred&8: &6" + DatabaseManager.getTotalReferrals(player.getUniqueId())));
        referStatsLore.add("");
        referStatsMeta.setLore(referStatsLore);
        referStats.setItemMeta(referStatsMeta);

        menu.setItem(12, confirmInvite);
        menu.setItem(14, referStats);
        player.openInventory(menu);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equals("Referrals")) {
            if(event.getCurrentItem() != null) {
                if(event.getCurrentItem().getType() == Material.WRITABLE_BOOK) {
                    if(!DatabaseManager.hasPlayedBefore(player.getUniqueId())) {
                        ConfirmInviteGUI.openConfirmationGUI(player);
                        event.setCancelled(true);
                    }else{
                        player.sendMessage("You can't do this, you've played before.");
                        event.setCancelled(true);
                    }
                }else{
                    event.setCancelled(true);
                }
            }
        }
    }

}
