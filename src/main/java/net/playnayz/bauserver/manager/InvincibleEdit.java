package net.playnayz.bauserver.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InvincibleEdit {

    private static File file;
    private static FileConfiguration cfg;

    public static void saveCreation(String Name, Location location1, Location location2, Location ploc) {
        file = new File("plugins//InvincibeEdit//Creations//" + Name + ".yml");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cfg = YamlConfiguration.loadConfiguration(file);
        HashMap<Location, Material> alllocs = blocksFromTwoPoints(location1, location2);
        List<String> registerlocs = new ArrayList<>();
        int i = 0;
        for (Location location : alllocs.keySet()) {
            if (alllocs.get(location) != Material.AIR) {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.sendMessage("+ " + i);
                }
                String distance = getDistaceBetween2Locs(location1, ploc);
                cfg.set("Path." + i + ".Distance", distance);
                cfg.set("Path." + i + ".Material", alllocs.get(location).toString());
                save();
                i++;
            }
        }
    }

    public static void setCreation(String Name, Location location) {
        file = new File("plugins//InvincibeEdit//Creations//" + Name + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        try {
            cfg.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i > -1; i++) {
            if (cfg.contains("Path." + i)) {
                String output = cfg.getString("Path." + i + ".Distance");
                String[] outlist = output.split(";");
                int DistanceX = Integer.valueOf(outlist[0]);
                int DistanceY = Integer.valueOf(outlist[1]);
                int DistanceZ = Integer.valueOf(outlist[2]);

                int X = 0;
                int Y = 0;
                int Z = 0;

                if (DistanceX < 0) {
                    X = location.getBlockX() - DistanceX;
                } else {
                    X = location.getBlockX() + DistanceX;
                }
                if (DistanceY < 0) {
                    Y = location.getBlockY() - DistanceY;
                } else {
                    Y = location.getBlockY() + DistanceY;
                }
                if (DistanceZ < 0) {
                    Z = location.getBlockZ() - DistanceZ;
                } else {
                    Z = location.getBlockZ() + DistanceZ;
                }

                Location newloc = new Location(location.getWorld(), X, Y, Z);
                newloc.getBlock().setType(Material.getMaterial(cfg.getString("Path." + i + ".Material").toString()));


            } else {
                break;
            }
        }
    }

    public static int ret2v3CMD(int Integers) {
        String intg = Integers + "";
        if (Integers < 0) {
            intg = intg.replace("-", "");
        }
        return Integer.valueOf(intg);
    }

    public static String getDistaceBetween2Locs(Location location1, Location location2) {
        int X1 = location1.getBlockX();
        int Y1 = location1.getBlockY();
        int Z1 = location1.getBlockZ();

        int X2 = location2.getBlockX();
        int Y2 = location2.getBlockY();
        int Z2 = location2.getBlockZ();


        int DistanceX = rechner(X1, X2);
        int DistanceY = rechner(Y1, Y2);
        int DistanceZ = rechner(Z1, Z2);

        return DistanceX + ";" + DistanceY + ";" + DistanceZ;
    }


    public static int rechner(int a , int b)
    {
        int res;

        if(a>b){
            res = a-b;
        }

        else{
            res = b-a;
        }

        return res;
    }

    /*



    Umwandlung der alten Location in die neue:







     */

    public static void save() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String LocationToString(Location location) {
        int X = location.getBlockX();
        int Y = location.getBlockY();
        int Z = location.getBlockZ();
        String World = location.getWorld().getName();
        return X + ";" + Y + ";" + Z +  ";" + World + ";";
    }

    public static Location StringToLocatin(String StrLocation) {
        String[] stringout = StrLocation.split(";");
        int X = Integer.valueOf(stringout[0]);
        int Y = Integer.valueOf(stringout[1]);
        int Z = Integer.valueOf(stringout[2]);
        String World = stringout[3];
        return new Location(Bukkit.getWorld(World), X, Y, Z);
    }

    public static HashMap<Location, Material> blocksFromTwoPoints(Location loc1, Location loc2)
    {
        HashMap<Location, Material> blocks = new HashMap<>();

        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);

                    blocks.put(block.getLocation(), block.getType());
                }
            }
        }

        return blocks;
    }

}
