package noelflantier.bigbattery.common.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import noelflantier.bigbattery.Ressources;

public class ModConfig {
	public static Configuration config;
	public static File configDirectory;

	
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
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (config.hasChanged()) config.save();
		}
	}
}
