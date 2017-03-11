package noelflantier.bigbattery.common.handlers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {

	public static void loadRecipes() {

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockAluminium,1,0),  "iii", "iii", "iii", 'i', "ingotAluminium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockCopper,1,0),  "iii", "iii", "iii", 'i', "ingotCopper"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockGraphite,1,0),  "iii", "iii", "iii", 'i', "ingotGraphite"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockLead,1,0),  "iii", "iii", "iii", 'i', "ingotLead"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockNickel,1,0),  "iii", "iii", "iii", 'i', "ingotNickel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockPlatinium,1,0),  "iii", "iii", "iii", 'i', "ingotPlatinium"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockSilver,1,0),  "iii", "iii", "iii", 'i', "ingotSilver"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockTin,1,0),  "iii", "iii", "iii", 'i', "ingotTin"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockZinc,1,0),  "iii", "iii", "iii", 'i', "ingotZinc"));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockPlug,1,0),  "SSS", "RDR", "SSS", 'R',Items.REDSTONE ,'S', "ingotSteel", 'D', Items.DIAMOND));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockCasing,4,0),  "SSS", "SGS", "SSS", 'G',Blocks.GLASS ,'S', "ingotSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockCasing,8,2),  "SSS", "SIS", "SSS", 'I',Blocks.IRON_BLOCK ,'S', "ingotSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockInterface,1,0),  "SDS", "SCS", "SDS",'S', "ingotSteel", 'D', Blocks.HOPPER, 'C', Blocks.CAULDRON));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.blockInterface,1,0), new ItemStack(ModBlocks.blockInterface,1,4)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.blockInterface,1,2), new ItemStack(ModBlocks.blockInterface,1,0)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.blockInterface,1,4), new ItemStack(ModBlocks.blockInterface,1,2)));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockConductive,2,0), "SSS","SSS", "AAA", 'A',"ingotAluminium",'S', "ingotSteel"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockConductive,2,2), "SSS","SSS", "AAA", 'A',"ingotCopper",'S', "ingotSteel"));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemChloreFilter,1,0),  "IPI", "CFC", "IPI", 'P',Items.PAPER ,'C', Items.COAL, 'I', Items.IRON_INGOT, 'F', Items.FLINT));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemDustCalcium,16,0), new ItemStack(Items.DYE,1,15), new ItemStack(Items.DYE,1,15), new ItemStack(Items.DYE,1,15), new ItemStack(Items.DYE,1,15), new ItemStack(Items.DYE,1,15), new ItemStack(Items.DYE,1,15), new ItemStack(Items.DYE,1,15), new ItemStack(Items.DYE,1,15),new ItemStack(Items.WATER_BUCKET,1,0)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemDustCalcium,2,0), new ItemStack(Items.DYE,1,15),new ItemStack(Items.WATER_BUCKET,1,0)));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.blockIonicCalcium,2,0), new ItemStack(ModItems.itemIonicAgent,1,0), new ItemStack(Items.WATER_BUCKET,1,0), new ItemStack(ModItems.itemDustCalcium,1,0), new ItemStack(ModItems.itemDustCalcium,1,0)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.blockIonicChlore,2,0), new ItemStack(ModItems.itemIonicAgent,1,0), new ItemStack(Items.WATER_BUCKET,1,0), new ItemStack(ModItems.itemDustChlore,1,0), new ItemStack(ModItems.itemDustChlore,1,0)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.blockIonicLithium,2,0), new ItemStack(ModItems.itemIonicAgent,1,0), new ItemStack(Items.WATER_BUCKET,1,0), new ItemStack(ModItems.itemDustLithium,1,0), new ItemStack(ModItems.itemDustLithium,1,0)));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockCalcium,1,0),  "SSS", "SSS", "SSS", 'S', new ItemStack(ModItems.itemDustCalcium,1,0)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockLithium,1,0),  "SSS", "SSS", "SSS", 'S', new ItemStack(ModItems.itemDustLithium,1,0)));
		;
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModFluids.fluidChlore), new ItemStack(Items.WATER_BUCKET,1,0), new ItemStack(ModItems.itemDustChlore,1,0), new ItemStack(ModItems.itemDustChlore,1,0)));
		
		//PotionUtils.addPotionToItemStack(itemIn, potionIn)PotionTypes.WATER
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemIngotSteel,3,0), new ItemStack(Items.COAL,1,0),new ItemStack(Items.IRON_INGOT,1,0),new ItemStack(Items.IRON_INGOT,1,0),new ItemStack(Items.IRON_INGOT,1,0)));
		GameRegistry.addSmelting(new ItemStack(Items.COAL,1,0), new ItemStack(ModItems.itemIngotGraphite,1,0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.IRON_INGOT,1,0), new ItemStack(ModItems.itemIngotSteel,1,0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.FERMENTED_SPIDER_EYE,1,0), new ItemStack(ModItems.itemIonicAgent,8,0), 0);
	}
}
