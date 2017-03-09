package noelflantier.bigbattery.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noelflantier.bigbattery.common.handlers.ModProperties.CasingType;

public class BlockIonic extends ABlockBB{
	public BlockIonic(Material materialIn, String ulname) {
		super(materialIn);
		setRegistryName(ulname);
        setUnlocalizedName(ulname);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        setSoundType(SoundType.SLIME);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
