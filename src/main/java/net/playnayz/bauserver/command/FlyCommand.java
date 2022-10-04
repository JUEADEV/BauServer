package net.playnayz.bauserver.command;

import net.playnayz.bauserver.BauServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length == 0) {
            if (!player.hasPermission("fly.own")) {
                player.sendMessage(BauServer.NoPerm);
                return true;
            }
            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.sendMessage(BauServer.Prefix + "§7Dein Flugmodus ist jetzt §cdeaktiviert§7!");
            } else {
                player.setAllowFlight(true);
                player.sendMessage(BauServer.Prefix + "§7Dein Flugmodus ist jetzt §aaktiviert§7!");
            }
        } else if (args.length == 1) {
            if (Bukkit.getPlayer(args[0]) == null) {
                player.sendMessage(BauServer.Prefix + "§cDer Spieler ist nicht online!");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target.getAllowFlight()) {
                target.setAllowFlight(false);
                player.sendMessage(BauServer.Prefix + "§7Der Flugmodus von §e" + target.getName()
                    + " §7wurde §cdeaktiviert!");
                target.sendMessage(BauServer.Prefix + "§7Dein Flugmodus ist jetzt §cdeaktiviert§7!");
            } else {
                target.setAllowFlight(true);
                player.sendMessage(BauServer.Prefix + "§7Der Flugmodus von §e" + target.getName()
                        + " §7wurde §aaktiviert!");
                target.sendMessage(BauServer.Prefix + "§7Dein Flugmodus ist jetzt §aaktiviert§7!");
            }
        } else {
            HelpCommand.sendHelp(1, player);
        }

        return false;
    }
}
