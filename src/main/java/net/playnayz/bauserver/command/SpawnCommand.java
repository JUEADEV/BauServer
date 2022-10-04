package net.playnayz.bauserver.command;

import net.playnayz.bauserver.BauServer;
import net.playnayz.bauserver.manager.SpawnManager;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("");
            player.sendMessage(BauServer.Prefix + "§7Ale Warps§8:");
            player.sendMessage("");
            for (String Warps : SpawnManager.getSpawns()) {
                player.sendMessage("§8- §7" + Warps);
            }
            player.sendMessage("");
        } else if (args.length == 1) {
            String WarpName = args[0];
            if (!SpawnManager.getSpawns().contains(WarpName)) {
                player.sendMessage(BauServer.Prefix + "§cDer Warp existiert nicht!");
                return true;
            }
            player.teleport(SpawnManager.getSpawn(WarpName));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 3, 3);
            player.sendMessage(BauServer.Prefix + "§7Du wurdest zu §a" + WarpName + " §7teleportiert!");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("set")) {
                if (!player.hasPermission("Warp.Set")) {
                    player.sendMessage(BauServer.NoPerm);
                    return true;
                }
                String WarpName = args[1];
                if (WarpName.length() > 12) {
                    player.sendMessage(BauServer.Prefix + "§cDie Zeichenlänge einens WarpNamen muss zwischen 1 und 12 Zeichen sein!");
                    return true;
                }
                SpawnManager.setSpawn(WarpName, player.getLocation());
                player.sendMessage(BauServer.Prefix + "§7Der Warp §a" + WarpName + " §7wurde erstellt!");
            } else {
                HelpCommand.sendHelp(1, player);
            }
        } else {
            HelpCommand.sendHelp(1, player);
        }

        return false;
    }
}
