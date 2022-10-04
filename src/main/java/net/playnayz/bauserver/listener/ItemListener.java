package net.playnayz.bauserver.listener;

import net.playnayz.bauserver.manager.PWorld;
import net.playnayz.bauserver.manager.WorldManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemListener implements Listener {

    @EventHandler
    public void handleItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (player.getOpenInventory().getTitle().equals("§aAlle Welten")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.getOpenInventory().getTitle().equals("§aAlle Welten")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == Material.GRASS_BLOCK) {
                String Name = event.getCurrentItem().getItemMeta().getDisplayName().replace("§a", "");
                WorldManager.WorldEditor.put(player, Name);
                WorldManager.openGUIWorld(player);
            }
        }
        if (player.getOpenInventory().getTitle().startsWith("§aWelt")) {
            event.setCancelled(true);
            if (event.getRawSlot() == 14) {
                PWorld pWorld = new PWorld(WorldManager.WorldEditor.get(player));
                pWorld.loadSettings();
                player.teleport(pWorld.getSpawn());
            }
        }
    }


}
