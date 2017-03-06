package noelflantier.bigbattery.common.handlers;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.tiles.TileCasing;
import noelflantier.bigbattery.common.tiles.TileConductive;
import noelflantier.bigbattery.common.tiles.TilePlug;

public class ModTiles {
	
	public static void preInitCommon() {
		GameRegistry.registerTileEntity(TileConductive.class, Ressources.MODID + "_TileConductive");
		GameRegistry.registerTileEntity(TileCasing.class, Ressources.MODID + "_TileCasing");
		GameRegistry.registerTileEntity(TilePlug.class, Ressources.MODID + "_TilePlug");
	}
	
    @SideOnly(Side.CLIENT)
	public static void preInitClient() { 	
    
    }
    
    @SideOnly(Side.CLIENT)
	public static void initClient() {
	
    }

    @SideOnly(Side.CLIENT)
	public static void postInitClient() {	
		
	}
}
