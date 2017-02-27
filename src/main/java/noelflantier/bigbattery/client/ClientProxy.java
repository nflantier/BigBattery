package noelflantier.bigbattery.client;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import noelflantier.bigbattery.common.CommonProxy;
import noelflantier.bigbattery.common.handlers.ModBlocks;
import noelflantier.bigbattery.common.handlers.ModItems;
import noelflantier.bigbattery.common.handlers.ModTiles;

public class ClientProxy extends CommonProxy{

	@Override
	public void preInit(FMLPreInitializationEvent event){
		super.preInit(event);
		ModBlocks.preInitClient();
		ModItems.preInitClient();
    	ModTiles.preInitClient();
	}
	
	@Override
	public void init(FMLInitializationEvent event){
		super.init(event);
		ModBlocks.initClient();
	}

	@Override
	public void postinit(FMLPostInitializationEvent event) {
		super.postinit(event);
	}
}
