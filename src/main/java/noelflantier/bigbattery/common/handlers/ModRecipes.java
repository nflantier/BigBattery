package noelflantier.bigbattery.common.handlers;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
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
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemChloreFilter,1,0),  "PPP", "IFI", "PPP", 'P', Items.PAPER, 'I', Items.IRON_INGOT, 'F', Items.FLINT));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemDustCalcium,16,0), new ItemStack(Items.DYE,1,15), new ItemStack(Items.DYE,1,15), new ItemStack(Items.DYE,1,15), new ItemStack(Items.DYE,1,15), new ItemStack(Items.DYE,1,15), new ItemStack(Items.DYE,1,15), new ItemStack(Items.DYE,1,15), new ItemStack(Items.DYE,1,15),new ItemStack(Items.WATER_BUCKET,1,0)));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemDustCalcium,2,0), new ItemStack(Items.DYE,1,15),new ItemStack(Items.WATER_BUCKET,1,0)));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.itemIngotSteel,3,0), new ItemStack(Items.COAL,1,0),new ItemStack(Items.IRON_INGOT,1,0),new ItemStack(Items.IRON_INGOT,1,0),new ItemStack(Items.IRON_INGOT,1,0)));
		GameRegistry.addSmelting(new ItemStack(Items.COAL,1,0), new ItemStack(ModItems.itemIngotGraphite,1,0), 0);
		GameRegistry.addSmelting(new ItemStack(Items.IRON_INGOT,1,0), new ItemStack(ModItems.itemIngotSteel,1,0), 0);
	}
}
