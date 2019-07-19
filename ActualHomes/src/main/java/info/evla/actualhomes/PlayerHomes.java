package info.evla.actualhomes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

public class PlayerHomes {
	Map<String, Home> playerHomes = new HashMap<>();
	String playerUuid;
	public PlayerHomes(String playerUuid) {
		this.playerUuid = playerUuid;
	}
	
	public Home getHome(String name) {
		return playerHomes.get(name);	
	}
	
	public boolean setHome(String name, String location) {
		playerHomes.put(name, new Home(playerUuid, name, location));
		return true;
	}
	
	public boolean removeHome(String name) {
		playerHomes.remove(name);
		return true;
	}
	
	public Home[] listHomes() {
		
		// Bukkit.getPlayer(UUID.fromString(playerUuid)).sendMessage(playerHomes.toString());;
		Collection<Home> tmp = playerHomes.values();
		return (Home[]) tmp.toArray(new Home[tmp.size()]);
	}
	
	public boolean isHome(String name) {
		if (!playerHomes.containsKey(name)) {
			return false;  // The home does not exist and cannot return true
		}
		return true;
	}
	
	
}
