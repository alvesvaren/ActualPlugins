package info.evla.actualsleep.actualsleep;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class SleepListener implements Listener {
    private ActualSleep plugin;
    private short TIME_TO_WAKE = 23458;
    private boolean waitingForDay = false;

    SleepListener(ActualSleep plugin) {
        this.plugin = plugin;
    }

    private void setToDay(PlayerBedEnterEvent event) {
        waitingForDay = false;
        boolean someoneSleeps = false;
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.isSleeping()) {
                someoneSleeps = true;
            }
        }
        if (!someoneSleeps) return;
        event.getBed().getWorld().setTime(TIME_TO_WAKE);  // Set the time to morning
        event.getBed().getWorld().setStorm(false);  // Set the weather to clear
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event)
    {
        if (event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)) {

            Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + " is now sleeping, the sun will soon rise!");
            if (!waitingForDay) {
                waitingForDay = true;
                Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> setToDay(event), 100L);
            }
        }
    }
}
