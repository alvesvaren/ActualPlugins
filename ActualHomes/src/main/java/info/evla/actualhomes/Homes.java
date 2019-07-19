package info.evla.actualhomes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Homes {
	Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
	Map<String, PlayerHomes> homes = new HashMap<>();
	
	public Homes() {
		
	}
	
	public boolean setHome(String playerUuid, String name, Location location) {
		if (homes.containsKey(playerUuid)) {
			if (homes.get(playerUuid).isHome(name)) {
				return false;  // The home already exists, please delete first
			}
		} else {
			addPlayer(playerUuid);
		}
		homes.get(playerUuid).setHome(name, Utils.serializeLoc(location));
		return true;
	}
	
	public boolean removeHome(String playerUuid, String name) {
		if (homes.containsKey(playerUuid)) {
			if (!(homes.get(playerUuid).isHome(name))) {
				return false;  // The home already exists, please delete first
			}
		} else {
			addPlayer(playerUuid);
		}
		homes.remove(playerUuid + ":" + name);
		return true;
	}
	
	public Location getHome(String playerUuid, String name) {
		Bukkit.getPlayer(UUID.fromString(playerUuid)).sendMessage(homes.toString());
		// Bukkit.getPlayer(UUID.fromString(playerUuid)).sendMessage(homes.get(playerUuid).listHomes());
		if (homes.containsKey(playerUuid)) {
			if (!(homes.get(playerUuid).isHome(name))) {
				return null;  // There is no home and can't return the location of the home
			}
		} else {
			addPlayer(playerUuid);
			
		}
		return Utils.deserializeLoc(homes.get(playerUuid).getHome(name).location);
	}
	
	public String[] listHomes(String playerUuid) {
		Home[] tmp = homes.get(playerUuid).listHomes();
		String[] tmp2 = new String[tmp.length];
		for(int i = 0; i < tmp.length; i++) {
			tmp2[i] = tmp[i].toString();
		}
		return tmp2;
	}
	
	public List<String> listHomeNames(String playerUuid) {
		Home[] tmp = homes.get(playerUuid).listHomes();
		String[] tmp2 = new String[tmp.length];
		for(int i = 0; i < tmp.length; i++) {
			tmp2[i] = tmp[i].name;
		}
		return Arrays.asList(tmp2);
		
	}
	
	public boolean addPlayer(String playerUuid) {
		homes.put(playerUuid, new PlayerHomes(playerUuid));
		return true;
	}
}
