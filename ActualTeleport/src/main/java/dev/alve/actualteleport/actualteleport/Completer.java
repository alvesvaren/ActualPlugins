package dev.alve.actualteleport.actualteleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class Completer implements TabCompleter {
    ActualTeleport plugin;

    public Completer(ActualTeleport plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length > 1) {
            return new ArrayList<>();
        }
        switch (command.getName()) {
            case "tpa":
                List<String> playerNames = new ArrayList<String>();
                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    if (player != sender && !(plugin.tpaWaiters.containsKey(player.getUniqueId()) && plugin.tpaWaiters.get(player.getUniqueId()).contains(((Player) sender).getUniqueId())))
                        playerNames.add(player.getName());
                }
                return playerNames;
            case "tpyes":
                Player playerSender = (Player) sender;
                if (plugin.tpaWaiters.containsKey(playerSender.getUniqueId()) && plugin.tpaWaiters.get(playerSender.getUniqueId()).size() > 0) {
                    List<String> playerWaiterNames = new ArrayList<String>();
                    for (UUID playerUuid : plugin.tpaWaiters.get(playerSender.getUniqueId())) {
                        Player waiter = plugin.getServer().getPlayer(playerUuid);
                        if (waiter != null && waiter.isOnline()) {
                            playerWaiterNames.add(waiter.getName());
                        }
                    }
                    return playerWaiterNames;
                }
        }
        return null;
    }
}
