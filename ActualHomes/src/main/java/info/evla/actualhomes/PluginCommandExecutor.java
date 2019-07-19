package info.evla.actualhomes;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PluginCommandExecutor implements CommandExecutor {
	private final Plugin plugin;
	public Homes homes = new Homes();
	public PluginCommandExecutor(Plugin plugin) {
		// TODO Auto-generated constructor stub
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		
		switch(command.getName().toLowerCase()) {
		case "homes":
			return listHomes(sender, command, label, args);
		case "sethome":
			return setHome(sender, command, label, args);
		case "delhome":
			return delHome(sender, command, label, args);
		case "visit":
		case "home":
		case "go":
			return goHome(sender, command, label, args);

		}
		return false;
	}
	
	boolean setHome(CommandSender sender, Command command, String label, String[] args) {
		plugin.getLogger().info("SET HOME! was called");
		
		if (!(sender instanceof Player)) {
			return false;
		} else if (!sender.hasPermission("actualhomes.sethome.self")) {
			return false;
		}
		String homeName;
		if (args.length == 1) {
			homeName = args[0];
		} else {
			homeName = "default";
		}
		Location location = ((Player)sender).getLocation();
		//String tmp = Utils.serializeLoc(location);
		sender.sendMessage("Set home '" + homeName + "' at " + location.toVector());
		homes.setHome(((Player)sender).getUniqueId().toString(), homeName, location);
		return true;
	}
	
	boolean delHome(CommandSender sender, Command command, String label, String[] args) {
		plugin.getLogger().info("DEL HOME! was called");
		return true;
	}
	
	boolean listHomes(CommandSender sender, Command command, String label, String[] args) {
		plugin.getLogger().info("LIST HOMES! was called");
		sender.sendMessage(sender.getName() + "'s homes:");
		for(String home : homes.listHomes(((Player)sender).getUniqueId().toString())) {
			sender.sendMessage(home);
		}
		// sender.sendMessage("Homes: :(");
		return true;
	}
	
	boolean goHome(CommandSender sender, Command command, String label, String[] args) {
		plugin.getLogger().info("GO HOME! was called");
		
		String homeName;
		if (args.length == 1) {
			homeName = args[0];
		} else {
			homeName = "default";
		}
		
		Location location = homes.getHome(((Player)sender).getUniqueId().toString(), homeName);
		if (location == null) {
			sender.sendMessage("The home '"+ homeName +"' does not exist.");
			return false;
		}
		sender.sendMessage("Teleported to home '"+ homeName +"'.");
		((Player)sender).teleport(location);
		new Thread(() -> {
		    //Do whatever
		}).start();
		return true;
	}
	

}
