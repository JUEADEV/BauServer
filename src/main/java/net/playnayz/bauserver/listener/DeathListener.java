package net.playnayz.bauserver.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void handlePlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
    }

}
