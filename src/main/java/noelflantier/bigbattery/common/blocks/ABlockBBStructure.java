package noelflantier.bigbattery.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import noelflantier.bigbattery.common.helpers.MultiBlockMessage;
import noelflantier.bigbattery.common.tiles.ITileHaveMaster;

public abstract class ABlockBBStructure  extends ABlockContainerBB {
    public static final PropertyBool ISSTRUCT = PropertyBool.create("isstruct");
    protected ABlockBBStructure(Material materialIn) {
		super(materialIn);
	}

    public abstract TileEntity createNewTileEntity(World worldIn, int meta);

    public void neighborChangedBB(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos){
		IBlockState st = worldIn.getBlockState(fromPos);
		if(!worldIn.isRemote && state.getPropertyKeys().contains(ISSTRUCT) && state.getValue(ISSTRUCT) == true){
			TileEntity te = worldIn.getTileEntity(pos);
			if( te != null && te instanceof ITileHaveMaster){
				if(st.getBlock() instanceof ABlockBBStructure == false){
					if( !((ITileHaveMaster)te).isMasterStructured() )
						worldIn.setBlockState(pos, state.withProperty(ISSTRUCT, false));
					return;
				}
				if( !((ITileHaveMaster)te).isMasterStillHere())
					worldIn.setBlockState(pos, state.withProperty(ISSTRUCT, false));
			}
		}
    }
    
    public void breakBlockBB(World worldIn, BlockPos pos, IBlockState state)
    {  
    	if (!worldIn.isRemote)
	    {
	    	//worldIn.notifyNeighborsOfStateChange(pos, state.getBlock(), true);
			TileEntity te = worldIn.getTileEntity(pos);
			if( te != null && te instanceof ITileHaveMaster){
				((ITileHaveMaster)te).toMaster(pos, MultiBlockMessage.CHECK);
			}
	    }
        super.breakBlock(worldIn, pos, state);
    }
    
}
