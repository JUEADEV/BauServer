package net.playnayz.bauserver.command;

import net.playnayz.bauserver.BauServer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GmCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length == 0) {
            HelpCommand.sendHelp(1, player);
        } else if (args.length == 1) {
            if (!player.hasPermission("Gm.Own")) {
                player.sendMessage(BauServer.NoPerm);
                return true;
            }
            GameMode gameMode = GameMode.SURVIVAL;
            if (args[0].equalsIgnoreCase("0")) {
                gameMode = GameMode.SURVIVAL;
            } else if (args[0].equalsIgnoreCase("1")) {
                gameMode = GameMode.CREATIVE;
            } else if (args[0].equalsIgnoreCase("2")) {
                gameMode = GameMode.ADVENTURE;
            } else if (args[0].equalsIgnoreCase("3")) {
                gameMode = GameMode.SPECTATOR;
            } else {
                HelpCommand.sendHelp(1, player);
            }
            player.setGameMode(gameMode);
            player.sendMessage(BauServer.Prefix + "§7Dein GameMode wurde auf §a"
                    + gameMode.toString() + " §7gesetzt!");
        } else if (args.length == 2) {
            if (!player.hasPermission("Gm.Target")) {
                player.sendMessage(BauServer.NoPerm);
                return true;
            }
            if (Bukkit.getPlayer(args[1]) == null) {
                player.sendMessage(BauServer.Prefix + "§cDer Spieler ist nicht online!");
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            GameMode gameMode = GameMode.SURVIVAL;
            if (args[0].equalsIgnoreCase("0")) {
                gameMode = GameMode.SURVIVAL;
            } else if (args[0].equalsIgnoreCase("1")) {
                gameMode = GameMode.CREATIVE;
            } else if (args[0].equalsIgnoreCase("2")) {
                gameMode = GameMode.ADVENTURE;
            } else if (args[0].equalsIgnoreCase("3")) {
                gameMode = GameMode.SPECTATOR;
            } else {
                HelpCommand.sendHelp(1, player);
            }
            target.setGameMode(gameMode);
            target.sendMessage(BauServer.Prefix + "§7Dein GameMode wurde auf §a"
                    + gameMode.toString() + " §7gesetzt!");
            player.sendMessage(BauServer.Prefix + "§7Der GameMode von §e" + target.getName()
            + " §7wurde auf §a" + gameMode.toString() + " §7gesetzt!");
        } else {
            HelpCommand.sendHelp(1, player);
        }

        return false;
    }
}
