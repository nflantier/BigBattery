package noelflantier.bigbattery.common.handlers;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.blocks.BlockFluids;

public class ModFluids {

	public static Fluid fluidChlore;
	/*public static Fluid fluidLithium;
	public static Fluid fluidCalcium;*/
	public static Block blockAqueousChlore;
	/*public static Block blockAqueousLithium;
	public static Block blockAqueousCalcium;*/
	
	public static void preInitCommon() {
		   
    	fluidChlore = new Fluid(Ressources.UL_NAME_FLUID_CHLORE,new ResourceLocation(Ressources.MODID+":blocks/liquefied_asgardite"),new ResourceLocation(Ressources.MODID+":blocks/liquefied_asgardite_flow")).setDensity(100).setViscosity(500).setTemperature(100);
		FluidRegistry.registerFluid(fluidChlore);
		FluidRegistry.addBucketForFluid(fluidChlore);
		/*fluidLithium = new Fluid(Ressources.UL_NAME_FLUID_LITHIUM,new ResourceLocation(Ressources.MODID+":blocks/liquefied_asgardite"),new ResourceLocation(Ressources.MODID+":blocks/liquefied_asgardite_flow")).setDensity(100).setViscosity(100).setTemperature(100);
		FluidRegistry.registerFluid(fluidLithium);
		FluidRegistry.addBucketForFluid(fluidLithium);
		fluidCalcium = new Fluid(Ressources.UL_NAME_FLUID_CALCIUM,new ResourceLocation(Ressources.MODID+":blocks/liquefied_asgardite"),new ResourceLocation(Ressources.MODID+":blocks/liquefied_asgardite_flow")).setDensity(100).setViscosity(100).setTemperature(100);
		FluidRegistry.registerFluid(fluidCalcium);
		FluidRegistry.addBucketForFluid(fluidCalcium);*/

		blockAqueousChlore = new BlockFluids(fluidChlore, Material.WATER, Ressources.UL_NAME_BLOCK_AQUEOUS_CHLORE);
		GameRegistry.register(blockAqueousChlore);
		/*blockAqueousLithium = new BlockFluids(fluidLithium, Material.WATER, Ressources.UL_NAME_BLOCK_AQUEOUS_LITHIUM);
		GameRegistry.register(blockAqueousLithium);
		blockAqueousCalcium = new BlockFluids(fluidCalcium, Material.WATER, Ressources.UL_NAME_BLOCK_AQUEOUS_CALCIUM);
		GameRegistry.register(blockAqueousCalcium);*/
	}
	
    @SideOnly(Side.CLIENT)
	public static void preInitClient() {
    	registerAndIgnoreState(blockAqueousChlore, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_AQUEOUS_CHLORE, "ignore"));
    	/*registerAndIgnoreState(blockAqueousLithium, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_FLUID_LITHIUM, "ignore"));
    	registerAndIgnoreState(blockAqueousCalcium, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_FLUID_CALCIUM, "ignore"));*/
    }

    @SideOnly(Side.CLIENT)
    public static void registerAndIgnoreState(Block block, ModelResourceLocation model){
    	ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, model);
        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return model;
            }
        };
        ModelLoader.setCustomStateMapper(block, ignoreState);
    }
    
    @SideOnly(Side.CLIENT)
	public static void initClient() {

    }

    @SideOnly(Side.CLIENT)
	public static void postInitClient() {	
		
	}
}
