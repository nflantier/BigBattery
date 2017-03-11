package noelflantier.bigbattery.common.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import noelflantier.bigbattery.Ressources;

public class ModConfig {
	public static Configuration config;
	public static File configDirectory;

	public static int maxSizeBattery;	
	public static int rangeChlore;
	public static int tickChlore;

	
	public static void preInitCommon(FMLPreInitializationEvent event) {
		if( configDirectory == null){
			configDirectory = new File(event.getModConfigurationDirectory(), Ressources.MODID.toLowerCase());
		    if(!configDirectory.exists()) {
		    	configDirectory.mkdir();
		    }
		}
		if (config == null) {
			config = new Configuration(new File(configDirectory, Ressources.NAME+".cfg"));
			syncConfig();
		}
	}
	
	public static void syncConfig() {
		try {
			maxSizeBattery = config.get(Configuration.CATEGORY_GENERAL, "number of blocks", 200, "Maximun size in block of any side for battery.").getInt();
			rangeChlore = config.get(Configuration.CATEGORY_GENERAL, "range of blocks", 4, "Range of block that will no longer contains chlore around the block clicked.").getInt();
			tickChlore = config.get(Configuration.CATEGORY_GENERAL, "number of ticks", 1000, "Ticks while the block clicked is out of chlore.").getInt();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (config.hasChanged()) config.save();
		}
	}
}
