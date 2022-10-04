package net.playnayz.bauserver.command;

import net.playnayz.bauserver.BauServer;
import net.playnayz.bauserver.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkullCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(BauServer.Prefix + "§cNutze /skull <Name>");
            player.sendBlockChange(player.getLocation(), Material.GRASS_BLOCK, (byte) 0);
            return true;
        }

        String Name = args[0];

        player.getInventory().addItem(new ItemBuilder(Material.LEGACY_SKULL_ITEM, 1, (byte) 3)
                .setSkullOwner(Name).setName("§7Kopf von §a" + Name).toItemStack());

        return false;
    }
}
