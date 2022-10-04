package net.playnayz.bauserver.listener;

import net.playnayz.bauserver.BauServer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveListener implements Listener {

    public static ArrayList<Location> locs = new ArrayList<>();

    @EventHandler
    public void handlePlayerMovw(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getName().startsWith("Gal")) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.spawnParticle(Particle.HEART, player.getLocation(), 3);
            }
        }
        if (player.getName().startsWith("Chi")) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.spawnParticle(Particle.HEART, player.getLocation(), 3);
            }
        }

        Location flor = player.getLocation().subtract(0, 1, 0);

        if (flor.getBlock().getType() == Material.GOLD_BLOCK) {
            player.sendMessage("§aDu bist ein Schwanz!");
            Location loc1 = new Location(player.getWorld(), -41, 5, 153);
            Location loc2 = new Location(player.getWorld(), -40, 5, 153);
            Location loc3 = new Location(player.getWorld(), -42, 5, 153);
            Location loc4 = new Location(player.getWorld(), -41, 6, 153);
            Location loc5 = new Location(player.getWorld(), -41, 7, 153);

            loc1.getBlock().setType(Material.DIRT);
            loc2.getBlock().setType(Material.DIRT);
            loc3.getBlock().setType(Material.DIRT);
            loc4.getBlock().setType(Material.DIRT);
            loc5.getBlock().setType(Material.DIRT);

        }

        Location location1 = new Location(player.getWorld(), 29, 20, 531);
        Location location2 = new Location(player.getWorld(), -34, 80, 467);

        if (isPlayerBetweenLocations(player, location1, location2, 30)) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.hidePlayer(player);
                if (isPlayerBetweenLocations(all, location1, location2, 30)) {
                    player.hidePlayer(all);
                }
            }
        } else {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.showPlayer(player);
                if (isPlayerBetweenLocations(all, location1, location2, 30)) {
                    player.hidePlayer(all);
                } else {
                    player.showPlayer(all);
                }
            }
        }






    }

    public boolean isPlayerBetweenLocations(Player p, Location a, Location b, double tolerance) {
        Location playerLoc = p.getLocation();
        if (!playerLoc.getWorld().equals(a.getWorld()) || !playerLoc.getWorld().equals(b.getWorld())) {
            return false;
        }
        Location middle = new Location(a.getWorld(), (a.getX() + b.getX()) / 2.0, (a.getY() + b.getY()) / 2.0, (a.getZ() + b.getZ()) / 2.0);
        return middle.distance(playerLoc) <= tolerance;
    }

}
