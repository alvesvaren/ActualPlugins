package info.evla.actualhomes;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Home {
	public String name;
	public String location;
	public String playerUuid;
	
	public Home(String playerUuid, String name, String location) {
		this.playerUuid = playerUuid;
		this.name = name;
		this.location = location;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		Location tmp = Utils.deserializeLoc(location);
		Vector tmp2 = tmp.toVector();
		
		String tmp3 = String.valueOf(Math.round(tmp2.getX())) + ", " + String.valueOf(Math.round(tmp2.getY())) + ", " + String.valueOf(Math.round(tmp2.getZ()));
		
		return "  " + name + " @ " + tmp3 + " (" + tmp.getWorld().getName() + ")";  // Bukkit.getPlayer(UUID.fromString(playerUuid)).getName() + ":" + 
	}
}
