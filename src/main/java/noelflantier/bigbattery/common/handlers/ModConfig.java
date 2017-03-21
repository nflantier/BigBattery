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
	public static int amountFluidInterfaceCapacity;
	public static int maximumEnrichedClayVainSize;
	public static int chanceSpawningEnrichedClayPerChunk;
	public static int numberOfIonicAgentPerFSE;
	public static boolean areBatteryalwaysGenerating;

	
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
			maxSizeBattery = config.get(Configuration.CATEGORY_GENERAL, "battery side size", 100, "Maximun size in block of any side for battery.").getInt();
			rangeChlore = config.get(Configuration.CATEGORY_GENERAL, "chlore radius", 4, "Range of block that will no longer contains chlore around the block clicked.").getInt();
			tickChlore = config.get(Configuration.CATEGORY_GENERAL, "chlore ticks", 1000, "Ticks while the block clicked is out of chlore.").getInt();
			amountFluidInterfaceCapacity = config.get(Configuration.CATEGORY_GENERAL, "tank capacity interface", 10000, "The tank capacity of the electrolyte interface").getInt();
			maximumEnrichedClayVainSize = config.get(Configuration.CATEGORY_GENERAL, "max enriched clay vain size", 18, "The maximun number of enriched clay you can find in a vain.").getInt();
			chanceSpawningEnrichedClayPerChunk = config.get(Configuration.CATEGORY_GENERAL, "chance spawn enriched clay", 10, "The maximun number of enriched clay you can find in a vain.").getInt();
			numberOfIonicAgentPerFSE = config.get(Configuration.CATEGORY_GENERAL, "number ionic agent per fermented spider eye", 12, "The number of ionic agent you get by smelting one fermented spider eyes in a furnace.").getInt();
			areBatteryalwaysGenerating = config.get(Configuration.CATEGORY_GENERAL, "battery always generating power", false, "If set to true battery will generate power and consume materials even if there is nothing using the power").getBoolean();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (config.hasChanged()) config.save();
		}
	}
}
