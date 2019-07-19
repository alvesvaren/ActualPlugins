package info.evla.actualhomes;

import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {
	public PluginCommandExecutor executor;
	public PluginTabCompleter completer;
	String[] commandNames = {"homes",  "sethome", "delhome", "home"};
	@Override
	public void onEnable() {
		long startTime = System.nanoTime();
		executor = new PluginCommandExecutor(this);
		completer = new PluginTabCompleter(this);
		// getLogger().info("onEnable has been invoked!");
		for(String commandName : commandNames) {
			getCommand(commandName).setExecutor(executor);
			getCommand(commandName).setTabCompleter(completer);
		}
		
		long duration = (System.nanoTime() - startTime)/1000000;
		getLogger().info("Done! (" + Long.toString(duration) + "ms)");
	}
	
	@Override
	public void onDisable() {
		// getLogger().info("onDisable has been invoked!");
	}
	
	
}
