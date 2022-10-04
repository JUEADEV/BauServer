package net.playnayz.bauserver.manager;

import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpawnManager {

    public static File file = new File("plugins//BauServer//spawn.yml");
    public static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public static void save() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadAndCreate() {
        if (!file.exists()) {
            cfg.set("Spawns", new ArrayList<String>());
            save();
        } else {
            try {
                cfg.load(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<String> getSpawns() {
        return cfg.getStringList("Spawns");
    }

    public static void setSpawn(String Name, Location location) {
        cfg.set("Spawn." + Name, location);
        save();

        if (!getSpawns().contains(Name)) {
            List<String> list = getSpawns();
            list.add(Name);
            cfg.set("Spawns", list);
            save();
        }
    }

    public static Location getSpawn(String Name) {
        return (Location) cfg.get("Spawn." + Name);
    }


}
