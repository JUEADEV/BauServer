package net.playnayz.bauserver.command;

import net.playnayz.bauserver.BauServer;
import net.playnayz.bauserver.manager.InvincibleEdit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class InvEditCommand implements CommandExecutor {

    public static HashMap<String, Location> SetupLocs = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (args[0].equals("pos1")) {
            SetupLocs.put(player.getName() + "-1", player.getLocation());
            player.sendMessage(BauServer.Prefix + "§aErste Location gesetzt!");
        } else if (args[0].equals("pos2")) {
            SetupLocs.put(player.getName() + "-2", player.getLocation());
            player.sendMessage(BauServer.Prefix + "§aZweite Location gesetzt!");
        } else if (args[0].equals("save")) {
            if (args.length == 2) {
                String Name = args[1];
                if (!SetupLocs.containsKey(player.getName() + "-1")) {
                    player.sendMessage(BauServer.Prefix + "§cDu musst 2 Locations auswählen!");
                    return true;
                }
                if (!SetupLocs.containsKey(player.getName() + "-2")) {
                    player.sendMessage(BauServer.Prefix + "§cDu musst 2 Locations auswählen!");
                    return true;
                }
                Location loc1 = SetupLocs.get(player.getName() + "-1");
                Location loc2 = SetupLocs.get(player.getName() + "-2");

                InvincibleEdit.saveCreation(Name, loc1, loc2, player.getLocation());
                player.sendMessage(BauServer.Prefix + "§7Das Objekt §a" + Name + " §7wurde gespeichert!");
            } else {
                return true;
            }
        } else if (args[0].equals("paste")) {
            if (args.length == 2) {
                String Name = args[1];
                player.sendMessage(BauServer.Prefix + "§7Das Objekt wird gesetzt..");
                InvincibleEdit.setCreation(Name, player.getLocation());
                player.sendMessage(BauServer.Prefix + "§7Das Objekt §a" + Name + " §7wurde gesetzt!");
            } else {
                return true;
            }
        }


        return false;
    }
}
