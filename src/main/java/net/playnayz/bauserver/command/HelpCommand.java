package net.playnayz.bauserver.command;

import net.playnayz.bauserver.BauServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length == 0) {
            sendHelp(1 ,player);
        } else {
            sendHelp(1, player);
        }

        return false;
    }

    public static void sendHelp(int Page, Player player) {
        if (Page == 1) {
            player.sendMessage("");
            player.sendMessage(BauServer.Prefix + "§7Help §8(§aSeite 1§8):");
            player.sendMessage("");
            player.sendMessage("§a/gm <0-3> §7Wechsel deinen GameMode!");
            player.sendMessage("§a/fly §7Aktiviere / Deaktiviere deinen Flugmodus!");
            player.sendMessage("§a/speed <1-5> §7Verschneller deine Bewegung! (1 = Standart)");
            player.sendMessage("§a/tp <Name> §7Teleportiere dich zu einem Spieler!");
            player.sendMessage("§a/tphere <Name> §7Teleportiere einene Spieler zu dir!");
            player.sendMessage("§a/help <1-2> §Zeige alle Hilfsseiten");
            player.sendMessage("");
        } else if (Page == 2) {
            player.sendMessage("");
            player.sendMessage(BauServer.Prefix + "§7Help §8(§aSeite 1§8):");
            player.sendMessage("");
            player.sendMessage("§a/world list §7Zeige alle Welten!");
            player.sendMessage("§a/world <Name> create <NORMAL | NETHER | END> <true/false> §7Erstelle eine Welt!");
            player.sendMessage("§a/world <Name> delete §7lösche eine Welt!");
            player.sendMessage("§a/world <Name> tp §7Teleportiere dich zu einer Welt!");
            player.sendMessage("§a/help <1-2> §7Zeige alle Hilfsseiten");
            player.sendMessage("");
        }
    }
}
