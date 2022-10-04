package net.playnayz.bauserver.command;

import net.playnayz.bauserver.BauServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length == 0) {
            HelpCommand.sendHelp(1, player);
        } else if (args.length == 1) {
            double Speed = 0;
            if (args[0].equalsIgnoreCase("1")) {
                Speed = 0.2;
            } else if (args[0].equalsIgnoreCase("2")) {
                Speed = 0.4;
            } else if (args[0].equalsIgnoreCase("3")) {
                Speed = 0.6;
            } else if (args[0].equalsIgnoreCase("4")) {
                Speed = 0.8;
            } else if (args[0].equalsIgnoreCase("5")) {
                Speed = 1.0;
            } else {
                HelpCommand.sendHelp(1, player);
                return true;
            }
            player.setWalkSpeed((float) Speed);
            player.setFlySpeed((float) Speed);
            player.sendMessage(BauServer.Prefix + "§7Dein Speed wurde auf §a" + args[0] + " §7gesetzt!");
        } else {
            HelpCommand.sendHelp(1, player);
        }

        return false;
    }
}
