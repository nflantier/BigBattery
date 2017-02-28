package noelflantier.bigbattery.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import noelflantier.bigbattery.common.handlers.ModBlocks;
import noelflantier.bigbattery.common.handlers.ModConfig;
import noelflantier.bigbattery.common.handlers.ModEvents;
import noelflantier.bigbattery.common.handlers.ModFluids;
import noelflantier.bigbattery.common.handlers.ModItems;
import noelflantier.bigbattery.common.handlers.ModTiles;
import noelflantier.bigbattery.common.helpers.Materials;

public class CommonProxy {
	
	static {
		FluidRegistry.enableUniversalBucket(); // Must be called before preInit
	}
	
	public void preInit(FMLPreInitializationEvent event) {

		ModConfig.preInitCommon(event);
		ModEvents.preInitCommon();
        MinecraftForge.EVENT_BUS.register(ModEvents.INSTANCE);
        
    	ModBlocks.preInitCommon();
    	ModFluids.preInitCommon();
    	ModItems.preInitCommon();
    	ModTiles.preInitCommon();
	}
	
	public void init(FMLInitializationEvent event) {
	}

	public void postinit(FMLPostInitializationEvent event) {

		Materials.initList();
	}
}
