package net.playnayz.bauserver.command;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.playnayz.bauserver.BauServer;
import net.playnayz.bauserver.manager.PWorld;
import net.playnayz.bauserver.manager.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if (!player.hasPermission("World.Setup")) {
            player.sendMessage(BauServer.NoPerm);
            return true;
        }

        if (args.length == 0) {
            WorldManager.oenGUI(player);
        } else {


            if (args[0].equalsIgnoreCase("create")) {
                if (args.length != 4) {
                    HelpCommand.sendHelp(2, player);
                    return true;
                }
                String WorldName = args[1];
                PWorld pWorld = new PWorld(WorldName);
                if (pWorld.exist()) {
                    player.sendMessage(BauServer.Prefix + "§cDiese Welt existiert bereits!");
                    return true;
                }
                String enviroment = "";
                if (args[2].equalsIgnoreCase("normal")) {
                    enviroment = args[2];
                } else if (args[2].equalsIgnoreCase("nether")) {
                    enviroment = args[2];
                } else if (args[2].equalsIgnoreCase("end")) {
                    enviroment = args[2];
                } else {
                    HelpCommand.sendHelp(2, player);
                    return true;
                }
                boolean result = false;
                if (args[3].equalsIgnoreCase("true")) {
                    result = true;
                }
                player.sendMessage(BauServer.Prefix + "§7Die Welt §a" + WorldName + " §7wird erstellt..");
                WorldManager.createWorld(WorldName, enviroment, result);
                player.sendMessage(BauServer.Prefix + "§7Die Welt §a" + WorldName + " §7wurde erstellt!");
                pWorld.loadSettings();
                player.teleport(pWorld.getSpawn());
            } else if (args[0].equalsIgnoreCase("list")) {
                if (args.length != 1) {
                    HelpCommand.sendHelp(2, player);
                    return true;
                }

                player.sendMessage("");
                player.sendMessage(BauServer.Prefix + "§7Alle Welten§8:");
                player.sendMessage("");

                for (World world : Bukkit.getWorlds()) {
                    PWorld pWorld = new PWorld(world.getName());
                    pWorld.loadSettings();

                    int players = world.getPlayers().size();
                    String Description = pWorld.getDescription();

                    TextComponent textComponent = new TextComponent("§8- §7" + world.getName() + " §8[§aINFOS§8]");
                    textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder("§7Spieler online§8: §a" + players + " Spieler \n" +
                                    "§7Beschreibung§8: §a" + Description).create()));

                    player.spigot().sendMessage(textComponent);

                }
                player.sendMessage("");
            } else if (args[0].equalsIgnoreCase("delete")) {
                if (args.length != 2) {
                    HelpCommand.sendHelp(2, player);
                    return true;
                }
                String WorldName = args[1];
                PWorld pWorld = new PWorld(WorldName);

                if (!pWorld.exist()) {
                    player.sendMessage(BauServer.Prefix + "§cDiese Welt existiert nicht!");
                    return true;
                }
                player.sendMessage(BauServer.Prefix + "§7Die Welt §a" + WorldName + " §7wird gelöscht..");
                WorldManager.deleteWorld(WorldName);
                player.sendMessage(BauServer.Prefix + "§7Die Welt §a" + WorldName + " §7wurde gelöscht!");
            } else if (args[0].equalsIgnoreCase("tp")) {
                if (args.length != 2) {
                    HelpCommand.sendHelp(2, player);
                    return true;
                }
                String WorldName = args[1];
                PWorld pWorld = new PWorld(WorldName);

                if (!pWorld.exist()) {
                    player.sendMessage(BauServer.Prefix + "§cDie Welt existiert nicht!");
                    return true;
                }
                pWorld.loadSettings();
                player.teleport(pWorld.getSpawn());
                player.setGameMode(pWorld.getGamemode());
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 3, 3);
            } else if (args[0].equalsIgnoreCase("setDesc")) {
                 if (args.length < 2) {
                     HelpCommand.sendHelp(2, player);
                     return true;
                 }
                 String Text = "";
                 for (int i = 2; i < args.length; i++) {
                     Text = Text + args[i] + " ";
                 }
                 String WorldName = args[1];
                 PWorld pWorld = new PWorld(WorldName);
                 if (!pWorld.exist()) {
                     player.sendMessage(BauServer.Prefix + "§cDie Welt existiert nicht!");
                     return true;
                 }
                 if (Text.length() > 20) {
                     player.sendMessage(BauServer.Prefix + "§cDie Beschreibung darf maximal 20 Zeichen haben!");
                     return true;
                 }
                 pWorld.setDescription(Text.replace("§", "&"));
                 player.sendMessage(BauServer.Prefix + "§7Die Beschreibung von §a" + WorldName + " §7wurde auf §e" + Text + " §7gesetzt!");
            } else if (args[0].equalsIgnoreCase("setGM")) {
                if (args.length != 2) {
                    GameMode gameMode = GameMode.SURVIVAL;
                    if (args[2].equalsIgnoreCase("0") ||
                            args[2].equalsIgnoreCase("survival")) {
                        gameMode = GameMode.SURVIVAL;
                    } else if (args[2].equalsIgnoreCase("1") ||
                            args[2].equalsIgnoreCase("creative")) {
                        gameMode = GameMode.CREATIVE;
                    } else if (args[2].equalsIgnoreCase("2") ||
                            args[2].equalsIgnoreCase("adventure")) {
                        gameMode = GameMode.ADVENTURE;
                    } else if (args[2].equalsIgnoreCase("3") ||
                            args[2].equalsIgnoreCase("spectator")) {
                        gameMode = GameMode.SPECTATOR;
                    } else {
                        HelpCommand.sendHelp(2, player);
                    }
                    String WorldName = args[1];
                    PWorld pWorld = new PWorld(WorldName);
                    if (!pWorld.exist()) {
                        player.sendMessage(BauServer.Prefix + "§cDe Welt existiert nicht!");
                        return true;
                    }
                    pWorld.setGameMode(gameMode);
                    player.sendMessage(BauServer.Prefix + "§7Der GameMode von §a" + WorldName
                            + " §7wurde auf §e" + gameMode.toString() + " §7gesetzt!");
                }
            }

        }

        return false;
    }
}
