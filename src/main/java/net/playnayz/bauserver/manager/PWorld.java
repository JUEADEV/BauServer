package net.playnayz.bauserver.manager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.management.timer.TimerMBean;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PWorld {

    public static HashMap<World, Integer> WorldTime = new HashMap<>();

    public static File file = new File("plugins//BauServer//world.yml");
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
            List<String> worldlist = new ArrayList<>();
            for (World worlds : Bukkit.getWorlds()) {
                worldlist.add(worlds.getName());

                cfg.set("World." + worlds.getName() + ".Description", "Das ist eine Weltbeschreibung!");
                cfg.set("World." + worlds.getName() + ".AllowAnimals", false);
                cfg.set("World." + worlds.getName() + ".AllowMobs", false);
                cfg.set("World." + worlds.getName() + ".Time", 0);
                cfg.set("World." + worlds.getName() + ".Spawn", worlds.getSpawnLocation());
                cfg.set("World." + worlds.getName() + ".GameMode", GameMode.SURVIVAL.toString());

                save();

            }
            cfg.set("Worlds", worldlist);
            save();
        } else {
            try {
                cfg.load(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }
            for (World world : Bukkit.getWorlds()) {
                PWorld pWorld = new PWorld(world.getName());
                if (pWorld.exist()) {
                    pWorld.loadSettings();
                    WorldTime.put(world, pWorld.getTime());
                }
            }
        }
    }

    private String worldname, description;

    private boolean allowanimals, allowmobs;

    private GameMode gamemode;

    private int time;

    private Location spawn;


    public PWorld(String WorldName) {
        worldname = WorldName;
    }

    public void create() {
        cfg.set("World." + worldname + ".Description", "Das ist eine Weltbeschreibung!");
        cfg.set("World." + worldname + ".AllowAnimals", false);
        cfg.set("World." + worldname + ".AllowMobs", false);
        cfg.set("World." + worldname + ".Time", 0);
        cfg.set("World." + worldname + ".Spawn", Bukkit.getWorld(worldname).getSpawnLocation());
        cfg.set("World." + worldname + ".GameMode", GameMode.SURVIVAL.toString());
        save();
        List<String> worlds = cfg.getStringList("Worlds");
        worlds.add(worldname);
        cfg.set("Worlds", worlds);
        save();
    }

    public void delete() {
        cfg.set("World." + worldname, null);
        save();
        List<String> worlds = cfg.getStringList("Worlds");
        worlds.remove(worldname);
        cfg.set("Worlds", worlds);
        save();
    }

    public String getDescription() {
        return description;
    }

    public boolean isAllowanimals() {
        return allowanimals;
    }

    public boolean isAllowmobs() {
        return allowmobs;
    }

    public GameMode getGamemode() {
        return gamemode;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setDescription(String Description) {
        description = Description;
        cfg.set("World." + worldname + ".Description", description);
        save();
    }

    public void setAllowAnimals(boolean Allow) {
        allowanimals = Allow;
        cfg.set("World." + worldname + ".AllowAnimals", allowanimals);
        save();
    }

    public void setAllowMobs(boolean Allow) {
        allowmobs = Allow;
        cfg.set("World." + worldname + ".AllowMobs", allowmobs);
        save();
    }

    public void setGameMode(GameMode Gamemode) {
        gamemode = Gamemode;
        cfg.set("World." + worldname + ".GameMode", gamemode.toString());
        save();
    }

    public void setTime(int Time) {
        time = Time;
        cfg.set("World." + worldname + ".Time", time);
        save();
        WorldTime.put(Bukkit.getWorld(worldname), time);
    }

    public int getTime() {
        return time;
    }

    public boolean exist() {
        return (cfg.contains("World." + worldname));
    }

    public void loadSettings() {
        description = cfg.getString("World." + worldname + ".Description");
        allowanimals = cfg.getBoolean("World." + worldname + ".AllowAnmials");
        allowmobs = cfg.getBoolean("World." + worldname + ".AllowMobs");
        gamemode = GameMode.valueOf(cfg.getString("World." + worldname + ".GameMode"));
        time = cfg.getInt("World." + worldname + ".Time");
        spawn = (Location) cfg.get("World." + worldname + ".Spawn");
    }

}
