package noelflantier.bigbattery.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noelflantier.bigbattery.BigBattery;

public class BlockFluid extends BlockFluidClassic{
	public BlockFluid(Fluid fluid, Material material, String ulname) {
		super(fluid, material);
		setRegistryName(ulname);
        setUnlocalizedName(ulname);
		this.setCreativeTab(BigBattery.bbTabs);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
	}
}
