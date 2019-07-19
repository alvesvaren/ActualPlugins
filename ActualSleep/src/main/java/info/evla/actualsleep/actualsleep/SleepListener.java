package info.evla.actualsleep.actualsleep;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class SleepListener implements Listener {
    private ActualSleep plugin;
    private long TIME_TO_WAKE = 23458;

    SleepListener(ActualSleep plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event)
    {
        if (event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)) {
            Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + " is now sleeping, the sun will soon rise!");
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> event.getBed().getWorld().setTime(TIME_TO_WAKE), 200L);
        }

    }
}
