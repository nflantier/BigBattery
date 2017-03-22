package noelflantier.bigbattery.common.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.handlers.ModProperties.CasingType;
import noelflantier.bigbattery.common.handlers.ModProperties.PropertyCasingType;
import noelflantier.bigbattery.common.items.ItemBlockCasing;
import noelflantier.bigbattery.common.tiles.TileCasing;

public class BlockCasing extends ABlockBBStructure {

    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyCasingType CASING_TYPE = PropertyCasingType.create("type");

	public BlockCasing(Material materialIn) {
		super(materialIn);
		setRegistryName(Ressources.UL_NAME_BLOCK_CASING);
        setUnlocalizedName(Ressources.UL_NAME_BLOCK_CASING);
        setDefaultState(blockState.getBaseState().withProperty(ISSTRUCT, false).withProperty(CASING_TYPE, CasingType.GLASS).withProperty(UP, false).withProperty(DOWN, false).withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false));
		setHarvestLevel("pickaxe",1);
		setHardness(3.0F);
		setResistance(100.0F);
        GameRegistry.register(new ItemBlockCasing(this), getRegistryName());
	}

	@Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity)
    {
        return state.getValue(CASING_TYPE) == CasingType.GLASS ? SoundType.GLASS : SoundType.METAL;
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return getStateFromMeta(meta).getValue(ISSTRUCT) == true ? new TileCasing() : null;
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
        state = state.withProperty(WEST, this.getConnectedBlock(worldIn, pos, EnumFacing.WEST));
        state = state.withProperty(EAST, this.getConnectedBlock(worldIn, pos, EnumFacing.EAST));
        state = state.withProperty(NORTH, this.getConnectedBlock(worldIn, pos, EnumFacing.NORTH));
        state = state.withProperty(SOUTH, this.getConnectedBlock(worldIn, pos, EnumFacing.SOUTH));
        state = state.withProperty(UP, this.getConnectedBlock(worldIn, pos, EnumFacing.UP));
        state = state.withProperty(DOWN, this.getConnectedBlock(worldIn, pos, EnumFacing.DOWN));
        return state;
    }
    
    private Boolean getConnectedBlock(IBlockAccess worldIn, BlockPos pos, EnumFacing direction)
    {
    	
        BlockPos blockpos = pos.offset(direction);
        IBlockState iblockstate = worldIn.getBlockState(pos.offset(direction));
		return iblockstate.getBlock() == this && iblockstate.getValue(ISSTRUCT) == true;
    }

	@Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {        
		breakBlockBB(worldIn, pos, state);
    }

	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
		neighborChangedBB(state, worldIn, pos, blockIn, fromPos);
    }

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {ISSTRUCT, NORTH, EAST, SOUTH, WEST, UP, DOWN, CASING_TYPE});
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(ISSTRUCT, meta % 2 != 0).withProperty(CASING_TYPE, CasingType.getType((int) Math.floor(meta/2)));
    }
	//this.getDefaultState().withProperty(ISSTRUCT, false).withProperty(UP, true).withProperty(DOWN, true).withProperty(NORTH, true).withProperty(SOUTH, true).withProperty(EAST, true).withProperty(WEST, true)
	
	@Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(CASING_TYPE).ordinal() * 2 + (state.getValue(ISSTRUCT) == true ? 1 : 0 );
    }

	@Override
    public boolean isFullCube(IBlockState state)
    {
        return state.getValue(CASING_TYPE) == CasingType.IRON;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return state.getValue(CASING_TYPE) == CasingType.IRON;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
		 if(blockState.getValue(ISSTRUCT) == false)
			 return true;
		
		 IBlockState s = blockAccess.getBlockState(pos.add(side.getDirectionVec()));
		 if(s.getBlock() == this)
			 return blockState.getValue(CASING_TYPE) != s.getValue(CASING_TYPE);
		 
		 if(side == EnumFacing.UP && blockState.getValue(UP) == true )
			 return false;
		 if(side == EnumFacing.DOWN && blockState.getValue(DOWN) == true )
			 return false;
       	 if(side == EnumFacing.NORTH && blockState.getValue(NORTH) == true )
       		 return false;
       	 if(side == EnumFacing.SOUTH && blockState.getValue(SOUTH) == true )
       		 return false;
       	 if(side == EnumFacing.EAST && blockState.getValue(EAST) == true )
       		 return false;
       	 if(side == EnumFacing.WEST && blockState.getValue(WEST) == true )
       		 return false;
        
       	 return true;
    }
	
}
