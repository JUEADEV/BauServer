package net.playnayz.bauserver;

import net.playnayz.bauserver.command.*;
import net.playnayz.bauserver.listener.*;
import net.playnayz.bauserver.manager.*;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public final class BauServer extends JavaPlugin {

    public static String Prefix = "§aBauServer §8» ";
    public static String NoPerm = Prefix + "§cDafür hast du keine Rechte!";

    public static Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

    @Override
    public void onEnable() {

        SpawnManager.loadAndCreate();
        PWorld.loadAndCreate();

        Bukkit.getPluginManager().registerEvents(new WeatherListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new LogListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemListener(), this);

        getCommand("speed").setExecutor(new SpeedCommand());
        getCommand("help").setExecutor(new HelpCommand());
        getCommand("Fly").setExecutor(new FlyCommand());
        getCommand("warp").setExecutor(new SpawnCommand());
        getCommand("world").setExecutor(new WorldCommand());
        getCommand("gm").setExecutor(new GmCommand());
        getCommand("gamemode").setExecutor(new GmCommand());
        getCommand("skull").setExecutor(new SkullCommand());
        getCommand("edit").setExecutor(new InvEditCommand());

        time();
        timer();
        registerTeams();
        BosManager.updateScore();
        PrefixManager.updatePrefix();

    }

    public static void time() {
        for (World world : Bukkit.getWorlds()) {
            world.setTime(0);
            world.setMonsterSpawnLimit(0);
            world.setAmbientSpawnLimit(0);
            world.setAnimalSpawnLimit(0);
        }
        new BukkitRunnable() {

            @Override
            public void run() {
                time();
            }
        }.runTaskLater(BauServer.getPlugin(BauServer.class), 20);
    }

    public static void registerTeams() {
        Team admin = scoreboard.registerNewTeam("0001Admin");
        Team builder = scoreboard.registerNewTeam("0002Builder");
        Team spieler = scoreboard.registerNewTeam("0003Spieler");

        admin.setPrefix("§4Admin §8| §7");
        builder.setPrefix("§aBuilder §8| §7");
        spieler.setPrefix("§7Spieler §8| §7");
    }

    @Override
    public void onDisable() {
    }

    private void timer() {
        for (World world : PWorld.WorldTime.keySet()) {
            world.setTime(PWorld.WorldTime.get(world));
        }
        new BukkitRunnable() {

            @Override
            public void run() {
              timer();
            }
        }.runTaskLater(BauServer.getPlugin(BauServer.class), 5);
    }
}
