package dev.alve.actualteleport.actualteleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class Completer implements TabCompleter {
    ActualTeleport plugin;

    public Completer(ActualTeleport plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // switch (command.getName()) {
        //     case "bed":
        // }
        return null;
    }
}
