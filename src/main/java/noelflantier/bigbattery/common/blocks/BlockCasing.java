package noelflantier.bigbattery.common.blocks;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.helpers.MultiBlockMessage;
import noelflantier.bigbattery.common.tiles.ITileHaveMaster;
import noelflantier.bigbattery.common.tiles.TileCasing;

public class BlockCasing extends ABlockBBStructure {

    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    
    public static final PropertyCasingType TYPE = PropertyCasingType.create("type");
    
    public enum CasingType implements IStringSerializable{
    	BASIC("basic"),
    	ADVANCED("advanced");

    	public String name;
    	
    	private CasingType(String name) {
    		this.name = name;
		}
    	
		@Override
		public String getName() {
			return name;
		}
    }
    
	public static class PropertyCasingType extends PropertyEnum<CasingType>{

		protected PropertyCasingType(String name, Collection<CasingType> allowedValues) {
			super(name, CasingType.class, allowedValues);
		}
		public static PropertyCasingType create(String name)
	    {
	        return create(name, Predicates.<CasingType>alwaysTrue());
	    }
	    public static PropertyCasingType create(String name, Predicate<CasingType> filter)
	    {
	        return create(name, Collections2.<CasingType>filter(Lists.newArrayList(CasingType.values()), filter));
	    }
	    public static PropertyCasingType create(String name, Collection<CasingType> values)
	    {
	        return new PropertyCasingType(name, values);
	    }
	}
	public BlockCasing(Material materialIn) {
		super(materialIn);
		setRegistryName(Ressources.UL_NAME_BLOCK_CASING);
        setUnlocalizedName(Ressources.UL_NAME_BLOCK_CASING);
        setDefaultState(blockState.getBaseState().withProperty(ISSTRUCT, false).withProperty(UP, false).withProperty(DOWN, false).withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false));
		setHarvestLevel("pickaxe",1);
		setHardness(2.0F);
		setResistance(20.0F);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
	}

    
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return meta == 1 ? new TileCasing() : null;
	}

	@Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(!worldIn.isRemote){
			//worldIn.setBlockState(pos, state.withProperty(ISSTRUCT, !state.getValue(ISSTRUCT)));
		}
        return false;
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
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
        	//worldIn.notifyNeighborsOfStateChange(pos, state.getBlock(), true); 
        }
    }

	@Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        if (!worldIn.isRemote)
        {
        	worldIn.notifyNeighborsOfStateChange(pos, state.getBlock(), true); 
        }
    }

	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
		if(!worldIn.isRemote && state.getValue(ISSTRUCT) == true){
			TileEntity te = worldIn.getTileEntity(pos);
			if( te != null && te instanceof ITileHaveMaster){
				if(! ((ITileHaveMaster)te).toMaster(fromPos, MultiBlockMessage.CHECK) )
					worldIn.setBlockState(pos, state.withProperty(ISSTRUCT, false));
			}
		}
    }

	@Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor){
    	
    }

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {ISSTRUCT, NORTH, EAST, SOUTH, WEST, UP, DOWN});
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return meta == 1 ? this.getDefaultState().withProperty(ISSTRUCT, true) : this.getDefaultState().withProperty(ISSTRUCT, false);
    }
	//this.getDefaultState().withProperty(ISSTRUCT, false).withProperty(UP, true).withProperty(DOWN, true).withProperty(NORTH, true).withProperty(SOUTH, true).withProperty(EAST, true).withProperty(WEST, true)
	
	@Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(ISSTRUCT) == true ? 1 : 0;
    }

	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
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
        return false;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
		 if(blockState.getValue(ISSTRUCT) == false)
			 return true;
		
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
