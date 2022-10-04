package net.playnayz.bauserver.manager;

import net.playnayz.bauserver.util.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class WorldManager {

    public static HashMap<Player, String> WorldEditor = new HashMap<>();

    public static void createWorld(String WorldName, String Enviroment, boolean flat) {
        PWorld pWorld = new PWorld(WorldName);



        WorldCreator worldCreator = new WorldCreator(WorldName);
        worldCreator.generateStructures(false);
        if (flat) {
            worldCreator.type(WorldType.FLAT);
        } else {
            worldCreator.type(WorldType.NORMAL);
            World.Environment environment = World.Environment.NORMAL;
            if (Enviroment.equalsIgnoreCase("nether")) {
                environment = World.Environment.NETHER;
            } else if (Enviroment.equalsIgnoreCase("end")) {
                environment = World.Environment.THE_END;
            }
            worldCreator.environment(environment);
        }

        worldCreator.createWorld();
        pWorld.create();
    }

    public static void deleteWorld(String WorldName) {
        PWorld pWorld = new PWorld(WorldName);
        World world = Bukkit.getWorld(WorldName);

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        }

        pWorld.delete();
        deleteWorld(world.getWorldFolder());
        unloadWorld(world);

    }

    public static boolean deleteWorld(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }

    public static void unloadWorld(World world) {
        if(!world.equals(null)) {
            Bukkit.getServer().unloadWorld(world, true);
        }
    }


    public static void oenGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 3*9, "§aAlle Welten");

        for (World world : Bukkit.getWorlds()) {
            inventory.addItem(new ItemBuilder(Material.GRASS_BLOCK).setName("§a" + world.getName()).toItemStack());
        }

        player.openInventory(inventory);

    }

    public static void openGUIWorld(Player player) {
        if (WorldEditor.containsKey(player)) {
            String WorldName = WorldEditor.get(player);
            Inventory inventory =
                    Bukkit.createInventory(null, 3*9, "§aWelt §e" + WorldName);

            PWorld pWorld = new PWorld(WorldName);
            pWorld.loadSettings();

            int players = Bukkit.getWorld(WorldName).getPlayers().size();
            String Description = pWorld.getDescription();

            inventory.setItem(12, new ItemBuilder(Material.PAPER).setName("§aInfo")
                    .setLore(Arrays.asList("§7Spieler Onine§8: §a" + players + " Spieler",
                            "§7Beschreibung§8: §a" + Description)).toItemStack());

            inventory.setItem(14, new ItemBuilder(Material.ENDER_PEARL)
                    .setName("§5teleportieren").toItemStack());

            player.openInventory(inventory);

        }
    }


}
