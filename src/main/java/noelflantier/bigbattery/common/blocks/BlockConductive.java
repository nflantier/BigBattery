package noelflantier.bigbattery.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.handlers.ModProperties.ConductiveType;
import noelflantier.bigbattery.common.handlers.ModProperties.PropertyConductiveType;
import noelflantier.bigbattery.common.items.ItemBlockConductive;
import noelflantier.bigbattery.common.tiles.ITileHaveMaster;
import noelflantier.bigbattery.common.tiles.TileConductive;

public class BlockConductive extends ABlockBBStructure {

    public static final PropertyDirection FACING = BlockDirectional.FACING;
	public static final PropertyConductiveType CONDUCTIVE_TYPE = PropertyConductiveType.create("type");
    
	public BlockConductive(Material materialIn) {
		super(materialIn);
		setRegistryName(Ressources.UL_NAME_BLOCK_CONDUCTIVE);
        setUnlocalizedName(Ressources.UL_NAME_BLOCK_CONDUCTIVE);
        setDefaultState(blockState.getBaseState().withProperty(ISSTRUCT, false).withProperty(FACING, EnumFacing.DOWN).withProperty(CONDUCTIVE_TYPE, ConductiveType.COPPER));
		setHarvestLevel("pickaxe",1);
		setHardness(3.0F);
		setResistance(100.0F);
        GameRegistry.register(new ItemBlockConductive(this), getRegistryName());
	}
	
	@Override
    public int damageDropped(IBlockState state)
	{
		int m = getMetaFromState(state);
        return m % 2 != 0 ? m - 1 : m;
    }
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
		if(state.getValue(ISSTRUCT) == false)
			return state;
		TileEntity te = worldIn.getTileEntity(pos);
		if(te!=null && te instanceof ITileHaveMaster){
			EnumFacing f = ((ITileHaveMaster)te).getFacingFromPlug(pos);
			if(f != null)
				state = state.withProperty(FACING, f);
		}
        return state;
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return getStateFromMeta(meta).getValue(ISSTRUCT) == true ? new TileConductive() : null;
	}
	
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
		neighborChangedBB(state, worldIn, pos, blockIn, fromPos);
    }

	@Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {        
		breakBlockBB(worldIn, pos, state);
    }
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {ISSTRUCT, FACING, CONDUCTIVE_TYPE});
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(ISSTRUCT, meta % 2 != 0).withProperty(CONDUCTIVE_TYPE, ConductiveType.getType((int) Math.floor(meta/2)));
    }
	
	@Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(CONDUCTIVE_TYPE).ordinal() * 2 + (state.getValue(ISSTRUCT) == true ? 1 : 0 );
    }

}
