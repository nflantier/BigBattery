package noelflantier.bigbattery.common.handlers;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.items.ItemBB;
import noelflantier.bigbattery.common.items.ItemChloreFilter;
import noelflantier.bigbattery.common.items.ItemManual;

public class ModItems {

	public static Item itemIngotSilver;
	public static Item itemIngotLead;
	public static Item itemIngotPlatinium;
	public static Item itemIngotCopper;
	public static Item itemIngotGraphite;
	public static Item itemIngotNickel;
	public static Item itemIngotZinc;
	public static Item itemIngotTin;
	public static Item itemIngotAluminium;
	public static Item itemIngotSteel;

	public static Item itemDustChlore;
	public static Item itemDustLithium;
	public static Item itemDustCalcium;
	public static Item itemChloreFilter;
	public static Item itemIonicAgent;

	public static Item itemManual;
	
	public static void preInitCommon() {
		itemIngotSilver = new ItemBB().setRegistryName(Ressources.UL_NAME_ITEM_INGOT_SILVER).setUnlocalizedName(Ressources.UL_NAME_ITEM_INGOT_SILVER);
		GameRegistry.register(itemIngotSilver);
		itemIngotLead = new ItemBB().setRegistryName(Ressources.UL_NAME_ITEM_INGOT_LEAD).setUnlocalizedName(Ressources.UL_NAME_ITEM_INGOT_LEAD);
		GameRegistry.register(itemIngotLead);
		itemIngotPlatinium = new ItemBB().setRegistryName(Ressources.UL_NAME_ITEM_INGOT_PLATINIUM).setUnlocalizedName(Ressources.UL_NAME_ITEM_INGOT_PLATINIUM);
		GameRegistry.register(itemIngotPlatinium);
		itemIngotCopper = new ItemBB().setRegistryName(Ressources.UL_NAME_ITEM_INGOT_COPPER).setUnlocalizedName(Ressources.UL_NAME_ITEM_INGOT_COPPER);
		GameRegistry.register(itemIngotCopper);
		itemIngotGraphite = new ItemBB().setRegistryName(Ressources.UL_NAME_ITEM_INGOT_GRAPHITE).setUnlocalizedName(Ressources.UL_NAME_ITEM_INGOT_GRAPHITE);
		GameRegistry.register(itemIngotGraphite);
		itemIngotNickel = new ItemBB().setRegistryName(Ressources.UL_NAME_ITEM_INGOT_NICKEL).setUnlocalizedName(Ressources.UL_NAME_ITEM_INGOT_NICKEL);
		GameRegistry.register(itemIngotNickel);
		itemIngotZinc = new ItemBB().setRegistryName(Ressources.UL_NAME_ITEM_INGOT_ZINC).setUnlocalizedName(Ressources.UL_NAME_ITEM_INGOT_ZINC);
		GameRegistry.register(itemIngotZinc);
		itemIngotTin = new ItemBB().setRegistryName(Ressources.UL_NAME_ITEM_INGOT_TIN).setUnlocalizedName(Ressources.UL_NAME_ITEM_INGOT_TIN);
		GameRegistry.register(itemIngotTin);
		itemIngotAluminium = new ItemBB().setRegistryName(Ressources.UL_NAME_ITEM_INGOT_ALUMINIUM).setUnlocalizedName(Ressources.UL_NAME_ITEM_INGOT_ALUMINIUM);
		GameRegistry.register(itemIngotAluminium);
		itemIngotSteel = new ItemBB().setRegistryName(Ressources.UL_NAME_ITEM_INGOT_STEEL).setUnlocalizedName(Ressources.UL_NAME_ITEM_INGOT_STEEL);
		GameRegistry.register(itemIngotSteel);
		

		itemDustChlore = new ItemBB().setRegistryName(Ressources.UL_NAME_ITEM_DUST_CHLORE).setUnlocalizedName(Ressources.UL_NAME_ITEM_DUST_CHLORE);
		GameRegistry.register(itemDustChlore);
		itemDustLithium = new ItemBB().setRegistryName(Ressources.UL_NAME_ITEM_DUST_LITHIUM).setUnlocalizedName(Ressources.UL_NAME_ITEM_DUST_LITHIUM);
		GameRegistry.register(itemDustLithium);
		itemDustCalcium = new ItemBB().setRegistryName(Ressources.UL_NAME_ITEM_DUST_CALCIUM).setUnlocalizedName(Ressources.UL_NAME_ITEM_DUST_CALCIUM);
		GameRegistry.register(itemDustCalcium);
		
		itemIonicAgent = new ItemBB().setRegistryName(Ressources.UL_NAME_ITEM_IONIC_AGENT).setUnlocalizedName(Ressources.UL_NAME_ITEM_IONIC_AGENT);
		GameRegistry.register(itemIonicAgent);
		itemChloreFilter = new ItemChloreFilter().setRegistryName(Ressources.UL_NAME_ITEM_CHLORE_FILTER).setUnlocalizedName(Ressources.UL_NAME_ITEM_CHLORE_FILTER);
		GameRegistry.register(itemChloreFilter);
		
		itemManual = new ItemManual().setRegistryName(Ressources.UL_NAME_ITEM_MANUAL).setUnlocalizedName(Ressources.UL_NAME_ITEM_MANUAL);
		GameRegistry.register(itemManual);
		
	}
	
    @SideOnly(Side.CLIENT)
	public static void preInitClient() {
    	
    	ModelLoader.setCustomModelResourceLocation(itemIngotSilver, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_INGOT_SILVER, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(itemIngotLead, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_INGOT_LEAD, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(itemIngotPlatinium, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_INGOT_PLATINIUM, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(itemIngotCopper, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_INGOT_COPPER, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(itemIngotGraphite, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_INGOT_GRAPHITE, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(itemIngotNickel, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_INGOT_NICKEL, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(itemIngotZinc, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_INGOT_ZINC, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(itemIngotTin, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_INGOT_TIN, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(itemIngotAluminium, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_INGOT_ALUMINIUM, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(itemIngotSteel, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_INGOT_STEEL, "inventory"));

    	ModelLoader.setCustomModelResourceLocation(itemDustChlore, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_DUST_CHLORE, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(itemDustLithium, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_DUST_LITHIUM, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(itemDustCalcium, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_DUST_CALCIUM, "inventory"));
    	
    	ModelLoader.setCustomModelResourceLocation(itemIonicAgent, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_IONIC_AGENT, "inventory"));
    	ModelLoader.setCustomModelResourceLocation(itemChloreFilter, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_CHLORE_FILTER, "inventory"));
	
    	ModelLoader.setCustomModelResourceLocation(itemManual, 0, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_ITEM_MANUAL, "inventory"));
    	
    }

    @SideOnly(Side.CLIENT)
	public static void initClient() {

    }

    @SideOnly(Side.CLIENT)
	public static void postInitClient() {	
		
	}

}
