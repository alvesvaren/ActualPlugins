package dev.alve.actualteleport.actualteleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public class Commands implements CommandExecutor {
    ActualTeleport plugin;
    long timeoutDelay = 30L;

    public Commands(ActualTeleport plugin) {
        // TODO Auto-generated constructor stub
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean success = false;
        boolean teleportationCommand = false;
        switch (command.getName()) {
            case "bed":
                teleportationCommand = true;
                success = onSlashBed(sender, command, label, args);
                break;
            case "spawn":
                teleportationCommand = true;
                success = onSlashSpawn(sender, command, label, args);
                break;
            case "tpa":
                teleportationCommand = true;
                success = onSlashTpa(sender, command, label, args);
                break;
            case "tpyes":
                teleportationCommand = false;
                success = onSlashTpyes(sender, command, label, args);
                break;
            case "debug":
                teleportationCommand = false;
                success = true;
                sender.sendMessage(plugin.serializeTpaWaiters());
                break;

        }
        if (success) {

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
        ((Player)sender).teleport(((Player) sender).getServer().getWorlds().get(0).getSpawnLocation());
        return true;
    }

    public boolean onSlashTpa(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }
        UUID senderPlayerUuid = ((Player)sender).getUniqueId();
        Player target = plugin.getServer().getPlayer(args[0]);
        // plugin.getLogger().info(target.getName());
        if (target == null || !target.isOnline()) {
            sender.sendMessage("That player can't be found!");
            return true;
        }

        if (target == sender) {
            sender.sendMessage("You can't teleport to yourself!");
            return true;
        }

        UUID senderWantsToPlayerUuid = target.getUniqueId();
        if (plugin.tpaWaiters.containsKey(senderWantsToPlayerUuid) && plugin.tpaWaiters.get(senderWantsToPlayerUuid).contains(senderPlayerUuid)) {
            sender.sendMessage("You have a pending teleport request for that player. Please try again later.");
            return true;
        } else if (!plugin.tpaWaiters.containsKey(senderWantsToPlayerUuid)) {
            plugin.tpaWaiters.put(senderWantsToPlayerUuid, new HashSet<>());
        }
        sender.sendMessage("Sent a teleportation request to §a" + target.getName() + "§r. They have " + timeoutDelay + " seconds to accept it.");
        target.sendMessage("§a" + sender.getName() + "§r wants to teleport to you. Type \"/tpyes " + sender.getName() + "\" to accept!");
        plugin.tpaWaiters.get(senderWantsToPlayerUuid).add(senderPlayerUuid);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {if (plugin.tpaWaiters.containsKey(senderWantsToPlayerUuid)) {plugin.tpaWaiters.get(senderWantsToPlayerUuid).remove(senderPlayerUuid); sender.sendMessage("Your teleportation request to §a" + target.getName() + "§r timed out.");}}, 20*timeoutDelay);
        return true;
    }

    public boolean onSlashTpyes(CommandSender sender, Command command, String label, String[] args) {
        Player playerSender = (Player)sender;
        if (!plugin.tpaWaiters.containsKey(playerSender.getUniqueId()) || plugin.tpaWaiters.get(playerSender.getUniqueId()).size() == 0) {
            sender.sendMessage("You do not have any pending teleportation requests.");
            return true;
        }
        Player playerThatWantsToSender;
        if (args.length > 0) {
            playerThatWantsToSender = plugin.getServer().getPlayer(args[0]);
        } else {
            if (plugin.tpaWaiters.get(playerSender.getUniqueId()).size() == 1) {
                UUID tmpPlayer = plugin.tpaWaiters.get(playerSender.getUniqueId()).iterator().next();
                playerThatWantsToSender = plugin.getServer().getPlayer(tmpPlayer);
            } else {
                sender.sendMessage("You have more than one teleportation request, please use '/tpyes [player]' instead of just '/tpyes'");
                return true;
            }

        }

        if (playerThatWantsToSender == null || !playerThatWantsToSender.isOnline()) {
            sender.sendMessage("That player can't be found!");
            return true;
        }
        if (plugin.tpaWaiters.get(playerSender.getUniqueId()).contains(playerThatWantsToSender.getUniqueId())) {
            sender.sendMessage("You accepted the teleportation request from §a" + playerThatWantsToSender.getName() + "§r");
            playerThatWantsToSender.sendMessage("§a" + sender.getName() + "§r accepted your teleportation request! You will now be teleported.");
            playerThatWantsToSender.teleport(playerSender);
            plugin.tpaWaiters.get(playerSender.getUniqueId()).remove(playerThatWantsToSender.getUniqueId());
            if (plugin.tpaWaiters.get(playerSender.getUniqueId()).size() == 0) {
                plugin.tpaWaiters.remove(playerSender.getUniqueId());
            }
        }
        return true;
    }
}
