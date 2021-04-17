package me.Halflove.Referrals.Managers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReferralCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String lavel, String[] args){
        if(cmd.getName().equalsIgnoreCase("referrals")){
            if(sender instanceof Player)
                return true;

            Player player = (Player) sender;
            switch(args.length){
                default:
                    referralsMenu(player);
                    break;
                case 1:
                    if(Bukkit.getPlayer(args[0]) != null){
                        ReferralManager.confirmInvitation(player,Bukkit.getPlayer(args[0]));
                    }else{
                        player.sendMessage("Player not online");
                    }
                    break;
            }
        }
        return true;
    }

    public static void referralsMenu(Player player){

    }

}
