package noelflantier.bigbattery.common.handlers;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class ModBrewing {
	
	public static Random rdm = new Random();
	
	public static void preInitCommon(){
		//BrewingRecipeRegistry.addRecipe(new Recipe());

    	//PotionUtils.get
    	//PotionTypes.WATER
    	//ItemPotion.
	}
	
	public static class Recipe implements IBrewingRecipe{
		
		@Override
		public boolean isInput(ItemStack input) {
			return input.isItemEqualIgnoreDurability(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER));//input.isItemEqualIgnoreDurability(new ItemStack(Items.WATER_BUCKET));
		}

		@Override
		public boolean isIngredient(ItemStack ingredient) {
			return ingredient.isItemEqualIgnoreDurability(new ItemStack(Items.CLAY_BALL)) || ingredient.isItemEqualIgnoreDurability(new ItemStack(Items.DYE,1,15));
		}
		@Override
		public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
			return ingredient.isItemEqualIgnoreDurability(new ItemStack(Items.CLAY_BALL)) ? PotionUtils.addPotionToItemStack(new ItemStack(ModItems.itemDustLithium,rdm.nextInt(10) + 10), PotionTypes.AWKWARD) : PotionUtils.addPotionToItemStack(new ItemStack(ModItems.itemDustCalcium,rdm.nextInt(10) + 10), PotionTypes.AWKWARD) ;
		}
		
	}
}
