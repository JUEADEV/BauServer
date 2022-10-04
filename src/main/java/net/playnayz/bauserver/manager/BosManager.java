package net.playnayz.bauserver.manager;

import net.playnayz.bauserver.BauServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

public class BosManager {

    public static void setScore(Player player) {
        ScoreboardManager smanager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = smanager.getNewScoreboard();
        Objective obj = scoreboard.registerNewObjective("aaa", "bbb");

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§eINVINCIBLEMC §8| §aBauServer");

        obj.getScore("§a ").setScore(20);
        obj.getScore("§7Spieler Online§8:").setScore(19);
        obj.getScore(getTeam("Online", "§e" + Bukkit.getOnlinePlayers().size() + " Spieler",
                "", scoreboard, ChatColor.AQUA)).setScore(18);
        obj.getScore("§b ").setScore(17);
        obj.getScore("§7Dein GameMode§8:").setScore(16);
        obj.getScore(getTeam("GameMode", "§a" + player.getGameMode().toString(),
                "", scoreboard, ChatColor.BLACK)).setScore(15);
        obj.getScore("§c ").setScore(14);

        player.setScoreboard(scoreboard);


    }

    public static void updateScore() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.getScoreboard() == null) {
                setScore(all);
            }
            Scoreboard scoreboard = all.getScoreboard();
            Objective obj = scoreboard.getObjective("aaa");

            obj.getScore(getTeam("Online", "§e" + Bukkit.getOnlinePlayers().size() + " Spieler",
                    "", scoreboard, ChatColor.AQUA)).setScore(18);

            obj.getScore(getTeam("GameMode", "§a" + all.getGameMode().toString(),
                    "", scoreboard, ChatColor.BLACK)).setScore(15);

        }
        new BukkitRunnable() {

            @Override
            public void run() {
                updateScore();
            }
        }.runTaskLater(BauServer.getPlugin(BauServer.class), 20);
    }

    public static String getTeam(String Teams, String Prefix, String Suffix, Scoreboard scoreboard, ChatColor chatColor) {
        Team team = scoreboard.getTeam(Teams);
        if (scoreboard.getTeam(Teams) == null) {
            team = scoreboard.registerNewTeam(Teams);
        }
        team.setPrefix(Prefix);
        team.setSuffix(Suffix);
        team.addEntry(chatColor.toString());
        return chatColor.toString();
    }

}
