package noelflantier.bigbattery.common.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noelflantier.bigbattery.BigBattery;

public abstract class ABlockContainerBB extends BlockContainer {
	
	protected ABlockContainerBB(Material materialIn) {
		super(materialIn);
		this.setCreativeTab(BigBattery.bbTabs);
	}

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state){
        return true;
    }
    public abstract TileEntity createNewTileEntity(World worldIn, int meta);
}
