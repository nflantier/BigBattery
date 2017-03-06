package noelflantier.bigbattery.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.handlers.ModProperties.CasingType;
import noelflantier.bigbattery.common.handlers.ModProperties.ConductiveType;
import noelflantier.bigbattery.common.handlers.ModProperties.PropertyCasingType;
import noelflantier.bigbattery.common.handlers.ModProperties.PropertyConductiveType;
import noelflantier.bigbattery.common.items.ItemBlockConductive;
import noelflantier.bigbattery.common.tiles.ITileHaveMaster;
import noelflantier.bigbattery.common.tiles.TileCasing;
import noelflantier.bigbattery.common.tiles.TileConductive;

public class BlockConductive extends ABlockBBStructure {

    public static final PropertyDirection FACING = BlockDirectional.FACING;
	public static final PropertyConductiveType CONDUCTIVE_TYPE = PropertyConductiveType.create("type");
    
	public BlockConductive(Material materialIn) {
		super(materialIn);
		setRegistryName(Ressources.UL_NAME_BLOCK_CONDUCTIVE);
        setUnlocalizedName(Ressources.UL_NAME_BLOCK_CONDUCTIVE);
        setDefaultState(blockState.getBaseState().withProperty(ISSTRUCT, false).withProperty(FACING, EnumFacing.NORTH).withProperty(CONDUCTIVE_TYPE, ConductiveType.COPPER));
		setHarvestLevel("pickaxe",1);
		setHardness(2.0F);
		setResistance(20.0F);
        GameRegistry.register(new ItemBlockConductive(this), getRegistryName());
	}
	
	@Override
    public int damageDropped(IBlockState state)
    {
		int m = getMetaFromState(state);
        return m % 2 != 0 ? m - 1 : m;
    }
    
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return getStateFromMeta(meta).getValue(ISSTRUCT) == true ? new TileConductive() : null;
	}
	
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
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
