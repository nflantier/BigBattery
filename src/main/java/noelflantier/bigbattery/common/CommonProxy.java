package noelflantier.bigbattery.common;

import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noelflantier.bigbattery.BigBattery;
import noelflantier.bigbattery.common.handlers.ModBlocks;
import noelflantier.bigbattery.common.handlers.ModBrewing;
import noelflantier.bigbattery.common.handlers.ModConfig;
import noelflantier.bigbattery.common.handlers.ModEvents;
import noelflantier.bigbattery.common.handlers.ModFluids;
import noelflantier.bigbattery.common.handlers.ModGuis;
import noelflantier.bigbattery.common.handlers.ModItems;
import noelflantier.bigbattery.common.handlers.ModOreDict;
import noelflantier.bigbattery.common.handlers.ModRecipes;
import noelflantier.bigbattery.common.handlers.ModTiles;
import noelflantier.bigbattery.common.materials.MaterialsHandler;
import noelflantier.bigbattery.common.network.ModMessages;
import noelflantier.bigbattery.common.world.ModOreGen;

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
    	ModBrewing.preInitCommon();
    	ModOreDict.preInitCommon();
    	ModMessages.preInitCommon();
    	ModOreGen.init();
    	GameRegistry.registerWorldGenerator(new ModOreGen(), 100);
	}
	
	public void init(FMLInitializationEvent event) {
    	NetworkRegistry.INSTANCE.registerGuiHandler(BigBattery.instance, new ModGuis());
	}

	public void postinit(FMLPostInitializationEvent event) {
		ModEvents.postInitCommon();
		ModRecipes.loadRecipes();
    	MaterialsHandler.getInstance().loadRecipes();
	}
	
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity.getServer();
	}
}
