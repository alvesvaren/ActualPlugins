package info.evla.actualhomes;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class PluginTabCompleter implements TabCompleter {
	Plugin plugin;
	public PluginTabCompleter(Plugin plugin) {
		// TODO Auto-generated constructor stub
		this.plugin = plugin;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		// TODO Auto-generated method stub
		String playerUuid = ((Player)sender).getUniqueId().toString();
		if (plugin.executor.homes.homes.containsKey(playerUuid)) {
			if (!(plugin.executor.homes.listHomeNames(playerUuid).size() > 0)) {
				return null;
			}
		} else {
			return null;
		}
		
		switch(command.getName().toLowerCase()) {
		case "home":
		case "delhome":
			return plugin.executor.homes.listHomeNames(((Player)sender).getUniqueId().toString());
		case "sethome":
			return Arrays.asList(new String[] {"default"});
		case "homes":
			return null;
		}
		
		return null;
	}

}
