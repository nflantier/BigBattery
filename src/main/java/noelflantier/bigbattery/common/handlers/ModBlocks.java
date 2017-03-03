package noelflantier.bigbattery.common.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.blocks.BlockCasing;
import noelflantier.bigbattery.common.blocks.BlockEnrichedClay;
import noelflantier.bigbattery.common.blocks.BlockIonic;
import noelflantier.bigbattery.common.blocks.BlockMetals;
import noelflantier.bigbattery.common.blocks.BlockOres;
import noelflantier.bigbattery.common.blocks.BlockPlug;

public class ModBlocks {

	public static Block blockCasing;
	public static Block blockPlug;
	

	public static Block blockInterface;
	
	//METALS
	public static Block blockOreSilver;
	public static Block blockOreLead;
	public static Block blockOrePlatinium;
	public static Block blockOreCopper;
	public static Block blockOreNickel;
	public static Block blockOreZinc;
	public static Block blockOreTin;
	public static Block blockOreAluminium;
	
	public static Block blockEnrichedClay;
	
	public static Block blockSilver;
	public static Block blockLead;
	public static Block blockPlatinium;
	public static Block blockCopper;
	public static Block blockGraphite;
	public static Block blockNickel;
	public static Block blockZinc;
	public static Block blockTin;
	public static Block blockAluminium;
	
	/*
	 * 
	 * ENRICHEDCLAY lithiumdust
	 * BONEMEAL calciumdust
	 * Chl:ore filter you right click water you store coord for 100 tick you cant click in thos coord
	 * filtre (paper paper paper
	 * 			flint flint flint
	 *			paper paper paper)
	 * STEEL iron coal
	 * 
	 * */
	
	
	
	//ELECTROLYTES
	public static Block blockIonicChlore;
	public static Block blockIonicLithium;
	public static Block blockIonicCalcium;
	public static Block blockChlore;
	public static Block blockLithium;
	public static Block blockCalcium;
	
	public static void preInitCommon() {
		
		blockCasing = new BlockCasing(Material.ROCK);
		GameRegistry.register(blockCasing);
		
    	blockPlug = new BlockPlug(Material.ROCK);
		GameRegistry.register(blockPlug);
		blockOreSilver = new BlockOres(Material.IRON, Ressources.UL_NAME_BLOCK_ORE_SILVER).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockOreSilver);
		blockOreLead = new BlockOres(Material.IRON, Ressources.UL_NAME_BLOCK_ORE_LEAD).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockOreLead);
		blockOrePlatinium = new BlockOres(Material.IRON, Ressources.UL_NAME_BLOCK_ORE_PLATINIUM).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockOrePlatinium);
		blockOreCopper = new BlockOres(Material.IRON, Ressources.UL_NAME_BLOCK_ORE_COPPER).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockOreCopper);
		blockOreNickel = new BlockOres(Material.IRON, Ressources.UL_NAME_BLOCK_ORE_NICKEL).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockOreNickel);
		blockOreZinc = new BlockOres(Material.IRON, Ressources.UL_NAME_BLOCK_ORE_ZINC).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockOreZinc);
		blockOreTin = new BlockOres(Material.IRON, Ressources.UL_NAME_BLOCK_ORE_TIN).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockOreTin);
		blockOreAluminium = new BlockOres(Material.IRON, Ressources.UL_NAME_BLOCK_ORE_ALUMINIUM).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockOreAluminium);

		blockEnrichedClay = new BlockEnrichedClay(Material.CLAY).setHardness(0.6F).setHardness(1).setResistance(1);
		GameRegistry.register(blockEnrichedClay);
		
		blockSilver = new BlockMetals(Material.IRON, Ressources.UL_NAME_BLOCK_SILVER).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockSilver);
		blockLead = new BlockMetals(Material.IRON, Ressources.UL_NAME_BLOCK_LEAD).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockLead);
		blockPlatinium = new BlockMetals(Material.IRON, Ressources.UL_NAME_BLOCK_PLATINIUM).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockPlatinium);
		blockCopper = new BlockMetals(Material.IRON, Ressources.UL_NAME_BLOCK_COPPER).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockCopper);
		blockGraphite = new BlockMetals(Material.IRON, Ressources.UL_NAME_BLOCK_GRAPHITE).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockGraphite);
		blockNickel = new BlockMetals(Material.IRON, Ressources.UL_NAME_BLOCK_NICKEL).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockNickel);
		blockZinc = new BlockMetals(Material.IRON, Ressources.UL_NAME_BLOCK_ZINC).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockZinc);
		blockTin = new BlockMetals(Material.IRON, Ressources.UL_NAME_BLOCK_TIN).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockTin);
		blockAluminium = new BlockMetals(Material.IRON, Ressources.UL_NAME_BLOCK_ALUMINIUM).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockAluminium);

		blockIonicChlore= new BlockIonic(Material.GOURD, Ressources.UL_NAME_BLOCK_IONIC_CHLORE).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockIonicChlore);
		blockIonicLithium= new BlockIonic(Material.GOURD, Ressources.UL_NAME_BLOCK_IONIC_LITHIUM).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockIonicLithium);
		blockIonicCalcium= new BlockIonic(Material.GOURD, Ressources.UL_NAME_BLOCK_IONIC_CALCIUM).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockIonicCalcium);
		blockChlore= new BlockMetals(Material.IRON, Ressources.UL_NAME_BLOCK_CHLORE).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockChlore);
		blockLithium= new BlockMetals(Material.IRON, Ressources.UL_NAME_BLOCK_LITHIUM).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockLithium);
		blockCalcium= new BlockMetals(Material.IRON, Ressources.UL_NAME_BLOCK_CALCIUM).setHardness(1).setHardness(1).setResistance(1);
		GameRegistry.register(blockCalcium);
    	 	
	}
	
    @SideOnly(Side.CLIENT)
	public static void preInitClient() {
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockCasing), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_CASING_FULL, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockPlug), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_PLUG, "inventory"));
        ModelLoader.setCustomStateMapper(blockPlug, (new StateMap.Builder()).ignore(new IProperty[] {BlockPlug.ISSTRUCT}).build());
        

    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockOreSilver), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_ORE_SILVER, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockOreLead), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_ORE_LEAD, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockOrePlatinium), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_ORE_PLATINIUM, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockOreCopper), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_ORE_COPPER, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockOreNickel), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_ORE_NICKEL, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockOreZinc), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_ORE_ZINC, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockOreTin), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_ORE_TIN, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockOreAluminium), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_ORE_ALUMINIUM, "inventory"));
    	
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockEnrichedClay), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_ENRICHED_CLAY, "inventory"));
    	
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockSilver), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_SILVER, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockLead), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_LEAD, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockPlatinium), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_PLATINIUM, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockCopper), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_COPPER, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockGraphite), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_GRAPHITE, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockNickel), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_NICKEL, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockZinc), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_ZINC, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockTin), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_TIN, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockAluminium), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_ALUMINIUM, "inventory"));

    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockIonicChlore), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_IONIC_CHLORE, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockIonicLithium), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_IONIC_LITHIUM, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockIonicCalcium), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_IONIC_CALCIUM, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockChlore), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_CHLORE, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockLithium), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_LITHIUM, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockCalcium), 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_CALCIUM, "inventory"));
    }

    
    @SideOnly(Side.CLIENT)
	public static void initClient() {
    	
    }

    @SideOnly(Side.CLIENT)
	public static void postInitClient() {	
		
	}

}
