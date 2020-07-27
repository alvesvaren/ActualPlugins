package dev.alve.actualmanhunt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.HashSet;

public class ManhuntCommandExecutor implements CommandExecutor {
    ActualManhunt plugin;

    public ManhuntCommandExecutor(ActualManhunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName()) {
            case "track":
                plugin.trackings.putIfAbsent((Player) sender, new HashSet<>());
                Player playerToTrack = Bukkit.getPlayer(args[0]);
                if (playerToTrack != null && playerToTrack.isOnline() && playerToTrack != sender) {
                    plugin.trackings.get(sender).add(playerToTrack);
                    if (!((Player) sender).getInventory().contains(Material.COMPASS)) {
                        ((Player) sender).getInventory().addItem(new ItemStack(Material.COMPASS));
                    }
                    sender.sendMessage("§aNow tracking §r" + playerToTrack.getDisplayName());
                } else {
                    sender.sendMessage("§cThat player is not online, not a real player or yourself");
                    return false;
                }
                break;
            case "untrack":
                plugin.trackings.remove((Player) sender);
                /* for (ItemStack itemStack : ((Player) sender).getInventory()){
                    if (itemStack.getType() == Material.COMPASS) {
                        CompassMeta compassData = (CompassMeta) itemStack.getItemMeta();
                        if (compassData != null && !compassData.isLodestoneTracked()) {
                           compassData.setLodestone(null);
                           compassData.setDisplayName(null);
                           itemStack.setItemMeta(compassData);
                        }
                    }
                } */
                sender.sendMessage("§aTracking disabled for you.");
                break;
            default:
                return false;
        }
        return true;
    }
}
