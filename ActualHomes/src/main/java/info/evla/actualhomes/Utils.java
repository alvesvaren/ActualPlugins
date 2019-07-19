package info.evla.actualhomes;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class Utils {
    public static String serializeLoc(Location loc){
        return loc.getWorld().getName()+","+loc.getX()+","+loc.getY()+","+loc.getZ()+","+loc.getPitch()+","+loc.getYaw();
    }
     
    public static Location deserializeLoc(String str){
        String[] loc = str.split(",");
        return new Location(Bukkit.getWorld(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Double.parseDouble(loc[3]), Float.parseFloat(loc[4]), Float.parseFloat(loc[5]));
    }
}