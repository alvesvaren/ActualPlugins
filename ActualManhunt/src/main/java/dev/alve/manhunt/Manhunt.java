package dev.alve.manhunt;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.*;

public final class Manhunt extends JavaPlugin implements Listener {
    String[] commandNames = { "track", "untrack" };

    public HashMap<Player, HashSet<Player>> trackings = new HashMap<>();
    ManhuntCommandExecutor executor;

    @Override
    public void onEnable() {
        executor = new ManhuntCommandExecutor(this);
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("ActualManhunt enabled!");

        for(String commandName : commandNames) {
            PluginCommand command = getCommand(commandName);
            if (command != null)
                command.setExecutor(executor);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("ActualManhunt disabled.");
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (!event.getPlayer().getInventory().contains(Material.COMPASS) && (trackings.containsKey(event.getPlayer()) && !trackings.get(event.getPlayer()).isEmpty())) {
            event.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (!trackings.containsKey(event.getEntity()) || trackings.get(event.getEntity()).isEmpty()) {
            return;
        }
        for (ItemStack itemStack : event.getDrops()) {
            if (itemStack.getType() == Material.COMPASS) {
                event.getDrops().remove(itemStack);
                break;
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (trackings.containsKey(event.getPlayer()) && !trackings.get(event.getPlayer()).isEmpty()) {
            if (event.getMaterial() == Material.COMPASS && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
                Player player = null;
                for (Player tmpPlayer : trackings.getOrDefault(event.getPlayer(), new HashSet<>())) {
                    if (tmpPlayer.isDead()) {
                        continue;
                    }
                    if (player == null ||
                            event.getPlayer().getLocation().distanceSquared(tmpPlayer.getLocation()) <
                                    event.getPlayer().getLocation().distanceSquared(player.getLocation())) {
                        player = tmpPlayer;
                    }
                }
                if (player != null) {
                    ItemStack compass = event.getItem();
                    if (compass != null) {
                        CompassMeta compassData = (CompassMeta) compass.getItemMeta();
                        if (compassData != null) {
                            compassData.setDisplayName("§rCompass pointing at " + player.getDisplayName());
                            compassData.setLodestoneTracked(false);
                            if (player.getWorld() == event.getPlayer().getWorld()) {
                                compassData.setLodestone(player.getLocation());
                                event.getPlayer().spigot().sendMessage(
                                        ChatMessageType.ACTION_BAR,
                                        TextComponent.fromLegacyText("§aCompass is now pointing at §r" + player.getDisplayName())
                                );
                            } else {
                                event.getPlayer().spigot().sendMessage(
                                        ChatMessageType.ACTION_BAR,
                                        TextComponent.fromLegacyText("§cCan't find anyone to track")
                                );
                            }
                            compass.setItemMeta(compassData);
                        }
                    }
                } else {
                    event.getPlayer().spigot().sendMessage(
                            ChatMessageType.ACTION_BAR,
                            TextComponent.fromLegacyText("§cCan't find anyone to track")
                    );
                }
            }
        }
    }
}