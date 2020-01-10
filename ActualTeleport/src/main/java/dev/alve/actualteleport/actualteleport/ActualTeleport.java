package dev.alve.actualteleport.actualteleport;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class ActualTeleport extends JavaPlugin {
    String[] commandNames = { "bed", "spawn", "death" };
    Commands commands;
    Completer completer;

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
}
