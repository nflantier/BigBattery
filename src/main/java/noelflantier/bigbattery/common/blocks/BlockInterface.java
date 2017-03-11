package noelflantier.bigbattery.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.handlers.ModProperties.InterfaceType;
import noelflantier.bigbattery.common.handlers.ModProperties.PropertyInterfaceType;
import noelflantier.bigbattery.common.items.ItemBlockInterface;
import noelflantier.bigbattery.common.tiles.TileInterface;

public class BlockInterface extends ABlockBBStructure {

	public static final PropertyInterfaceType INTERFACE_TYPE = PropertyInterfaceType.create("type");
    
	public BlockInterface(Material materialIn) {
		super(materialIn);
		setRegistryName(Ressources.UL_NAME_BLOCK_INTERFACE);
        setUnlocalizedName(Ressources.UL_NAME_BLOCK_INTERFACE);
        setDefaultState(blockState.getBaseState().withProperty(ISSTRUCT, false).withProperty(INTERFACE_TYPE, InterfaceType.ELECTRODE));
		setHarvestLevel("pickaxe",1);
		setHardness(3.0F);
		setResistance(100.0F);
        GameRegistry.register(new ItemBlockInterface(this), getRegistryName());
	}
	
	@Override
    public int damageDropped(IBlockState state)
	{
		int m = getMetaFromState(state);
        return m % 2 != 0 ? m - 1 : m;
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return getStateFromMeta(meta).getValue(ISSTRUCT) == true ? new TileInterface(getStateFromMeta(meta).getValue(INTERFACE_TYPE)) : new TileInterface(getStateFromMeta(meta).getValue(INTERFACE_TYPE));
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
        return new BlockStateContainer(this, new IProperty[] {ISSTRUCT, INTERFACE_TYPE});
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(ISSTRUCT, meta % 2 != 0).withProperty(INTERFACE_TYPE, InterfaceType.getType((int) Math.floor(meta/2)));
    }
	
	@Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(INTERFACE_TYPE).ordinal() * 2 + (state.getValue(ISSTRUCT) == true ? 1 : 0 );
    }

}
