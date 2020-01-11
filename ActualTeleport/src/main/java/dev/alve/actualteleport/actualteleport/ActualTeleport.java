package dev.alve.actualteleport.actualteleport;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Array;
import java.util.*;

public final class ActualTeleport extends JavaPlugin {
    String[] commandNames = { "bed", "spawn", "tpa", "tpyes", "debug" };
    Commands commands;
    Completer completer;

    Map<UUID, Set<UUID>> tpaWaiters = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        long startTime = System.nanoTime();
        commands = new Commands(this);
        completer = new Completer(this);

        for(String commandName : commandNames) {
            getCommand(commandName).setExecutor(commands);
            getCommand(commandName).setTabCompleter(completer);
        }

        long duration = (System.nanoTime() - startTime)/1000000;
        getLogger().info("Done! (" + Long.toString(duration) + "ms)");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String serializeTpaWaiters() {
        String result = "";
        for (UUID toPlayer : tpaWaiters.keySet()) {
            result += toPlayer.toString() + "(" + getServer().getPlayer(toPlayer).getName() + "): \n";
            for (UUID fromPlayer : tpaWaiters.get(toPlayer)) {
                result += "    " + fromPlayer.toString() + "(" + getServer().getPlayer(fromPlayer).getName() + ")\n";
            }
        }
        return result;
    }
}
