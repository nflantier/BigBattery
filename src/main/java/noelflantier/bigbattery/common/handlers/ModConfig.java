package noelflantier.bigbattery.common.handlers;

import java.io.File;

import net.minecraft.init.Blocks;
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
	public static boolean metalGenOres;
	public static boolean clayGenOres;
	public static boolean genAluminium;
	public static boolean genCopper;
	public static boolean genLead;
	public static boolean genNickel;
	public static boolean genPlatinium;
	public static boolean genSilver;
	public static boolean genTin;
	public static boolean genZinc;
	
	public static final String CATGENORES = "ores";

	
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
			metalGenOres = config.get(Configuration.CATEGORY_GENERAL, "generating metalic ores", true, "If set to true metalic ores will be generated in the world depending on their own configuration, if set to false metalic ores will not be generated overriding their own configuration").getBoolean();
			clayGenOres = config.get(Configuration.CATEGORY_GENERAL, "generating enriched clay", true, "If set to true enriched clay will be generated in the world").getBoolean();
			
			genAluminium = config.get(CATGENORES, "generating aluminium", true, "If set to true and generating metalic ores is set to true aluminium will be generated in the world").getBoolean();
			genCopper = config.get(CATGENORES, "generating copper", true, "If set to true and generating metalic ores is set to true copper will be generated in the world").getBoolean();
			genLead = config.get(CATGENORES, "generating lead", true, "If set to true and generating metalic ores is set to true lead will be generated in the world").getBoolean();
			genNickel = config.get(CATGENORES, "generating nickel", true, "If set to true and generating metalic ores is set to true nickel will be generated in the world").getBoolean();
			genPlatinium = config.get(CATGENORES, "generating platinium", true, "If set to true and generating metalic ores is set to true platinium will be generated in the world").getBoolean();
			genSilver = config.get(CATGENORES, "generating silver", true, "If set to true and generating metalic ores is set to true silver will be generated in the world").getBoolean();
			genTin = config.get(CATGENORES, "generating tin", true, "If set to true and generating metalic ores is set to true tin will be generated in the world").getBoolean();
			genZinc = config.get(CATGENORES, "generating zinc", true, "If set to true and generating metalic ores is set to true zinc will be generated in the world").getBoolean();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (config.hasChanged()) config.save();
		}
	}
}
