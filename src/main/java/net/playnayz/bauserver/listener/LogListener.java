package net.playnayz.bauserver.listener;

import net.playnayz.bauserver.BauServer;
import net.playnayz.bauserver.manager.BosManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LogListener implements Listener {

    @EventHandler
    public void handePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        int MaxPlayer = Bukkit.getMaxPlayers();
        int SizePlayer  = Bukkit.getOnlinePlayers().size();

        event.setJoinMessage(BauServer.Prefix + "§7Der Spieler §a" + player.getName()
                + " §7ist dem Bauserver beigetreten §8(§a" + SizePlayer + "§8/§7" + MaxPlayer + "§8)");


        player.setPlayerListHeaderFooter("\n§eInvicibleMC.com §8| §aBauServer\n\n" +
                "§7Willkommen §a" + player.getName() + "\n", "\n§7Server §8| §aBauServer-1\n" +
                "        §7Teamspeak §8| §aInvincibleMC.com        \n");

        BosManager.setScore(player);


    }


    @EventHandler
    public void handePlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        int MaxPlayer = Bukkit.getMaxPlayers();
        int SizePlayer  = Bukkit.getOnlinePlayers().size();

        event.setQuitMessage(BauServer.Prefix + "§7Der Spieler §a" + player.getName()
                + " §7het den BauServer verlassen §8(§c" + SizePlayer + "§8/§7" + MaxPlayer + "§8)");



    }



}
