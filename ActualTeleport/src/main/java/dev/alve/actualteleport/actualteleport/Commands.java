package dev.alve.actualteleport.actualteleport;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    ActualTeleport plugin;

    public Commands(ActualTeleport plugin) {
        // TODO Auto-generated constructor stub
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean success = false;
        switch (command.getName()) {
            case "bed":
                success = onSlashBed(sender, command, label, args);
                break;
            case "spawn":
                success = onSlashSpawn(sender, command, label, args);
                break;
            case "death":
                success = onSlashDeath(sender, command, label, args);
                break;
        }
        return success;
    }

    public boolean onSlashBed(CommandSender sender, Command command, String label, String[] args) {
        Location bedSpawnLocation = ((Player) sender).getBedSpawnLocation();
        if (bedSpawnLocation != null) {
            sender.sendMessage("You will now be teleported to your bed.");
            ((Player)sender).teleport(bedSpawnLocation);
        } else {
            sender.sendMessage("Your bed can't be found!");
        }
        return true;
    }

    public boolean onSlashSpawn(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("You will now be teleported to the world spawn.");
        ((Player)sender).teleport(((Player) sender).getWorld().getSpawnLocation());
        return true;
    }

    public boolean onSlashDeath(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Not implemented.");
        return true;
    }
}
