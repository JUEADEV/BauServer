package net.playnayz.bauserver.manager;

import net.playnayz.bauserver.BauServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PrefixManager {

    public static void setPrefix(Player player) {
        if (player.isOp()) {
            player.setCustomName("§aBuilder §8| §7" + player.getName());
            player.setPlayerListName(player.getCustomName());
            player.setDisplayName(player.getCustomName());
            BauServer.scoreboard.getTeam("0002Builder").addEntry(player.getName());
        } else {
            player.setCustomName("§7Spieler §8| §7" + player.getName());
            player.setPlayerListName(player.getCustomName());
            player.setDisplayName(player.getCustomName());
            BauServer.scoreboard.getTeam("0003Spieler").addEntry(player.getName());
        }
    }

    public static void updatePrefix() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            setPrefix(all);
        }
        new BukkitRunnable() {

            @Override
            public void run() {
                updatePrefix();
            }
        }.runTaskLater(BauServer.getPlugin(BauServer.class), 20);
    }

}
