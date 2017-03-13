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
import noelflantier.bigbattery.common.blocks.BlockFluid;

public class ModFluids {

	public static Fluid fluidChlore;
	public static Block blockAqueousChlore;
	
	public static void preInitCommon() {
		   
    	fluidChlore = new Fluid(Ressources.UL_NAME_FLUID_CHLORE,new ResourceLocation(Ressources.MODID+":blocks/liquefied_asgardite"),new ResourceLocation(Ressources.MODID+":blocks/liquefied_asgardite_flow")).setDensity(100).setViscosity(500).setTemperature(100);
		FluidRegistry.registerFluid(fluidChlore);
		FluidRegistry.addBucketForFluid(fluidChlore);

		blockAqueousChlore = new BlockFluid(fluidChlore, Material.WATER, Ressources.UL_NAME_BLOCK_AQUEOUS_CHLORE);
		GameRegistry.register(blockAqueousChlore);
	}
	
    @SideOnly(Side.CLIENT)
	public static void preInitClient() {
    	registerAndIgnoreState(blockAqueousChlore, new ModelResourceLocation(Ressources.MODID+":"+Ressources.UL_NAME_BLOCK_AQUEOUS_CHLORE, "ignore"));
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
