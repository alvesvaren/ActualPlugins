package info.evla.actualsleep.actualsleep;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class ActualSleep extends JavaPlugin {
    SleepListener sleepListener = new SleepListener(this);

    @Override
    public void onEnable() {
        long startTime = System.nanoTime();
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(sleepListener, this);
        long duration = (System.nanoTime() - startTime)/1000000;
        getLogger().info("Done! (" + duration + "ms)");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll(sleepListener);
    }
}
