package noelflantier.bigbattery.common.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.blocks.BlockCasing;
import noelflantier.bigbattery.common.blocks.BlockPlug;

public class ModBlocks {

	public static Block blockCasing;
	public static Block blockPlug;
	
	public static void preInitCommon() {
		
		blockCasing = new BlockCasing(Material.ROCK);
		GameRegistry.register(blockCasing);
		
    	blockPlug = new BlockPlug(Material.ROCK);
		GameRegistry.register(blockPlug);
    	
    	 	
	}
	
    @SideOnly(Side.CLIENT)
	public static void preInitClient() {
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockCasing), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_CASING_FULL, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockPlug), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_PLUG, "inventory"));
        ModelLoader.setCustomStateMapper(blockPlug, (new StateMap.Builder()).ignore(new IProperty[] {BlockPlug.ISSTRUCT}).build());
    }

    
    @SideOnly(Side.CLIENT)
	public static void initClient() {
    	
    }

    @SideOnly(Side.CLIENT)
	public static void postInitClient() {	
		
	}

}
