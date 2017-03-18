package noelflantier.bigbattery.common.blocks;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noelflantier.bigbattery.BigBattery;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.handlers.ModConfig;
import noelflantier.bigbattery.common.handlers.ModGuis;
import noelflantier.bigbattery.common.tiles.ITileMaster;
import noelflantier.bigbattery.common.tiles.TilePlug;

public class BlockPlug extends ABlockBBStructure {

    public static final PropertyDirection FACING = BlockDirectional.FACING;
    public BlockPlug(Material materialIn) {
		super(materialIn);
		setRegistryName(Ressources.UL_NAME_BLOCK_PLUG);
        setUnlocalizedName(Ressources.UL_NAME_BLOCK_PLUG);
        setDefaultState(blockState.getBaseState().withProperty(ISSTRUCT, false).withProperty(FACING, EnumFacing.UP));
		setHarvestLevel("pickaxe",1);
		setHardness(2.0F);
		setResistance(20.0F);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
	}

	/*@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    { 
        worldIn.setBlockState(pos, state.withProperty(FACING,(placer == null) ? EnumFacing.NORTH : EnumFacing.fromAngle(placer.rotationYaw)));
    }*/

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TilePlug();
	}
	
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(worldIn.isRemote || playerIn.isSneaking() || hand == EnumHand.OFF_HAND)
			return true;
		
		if(!state.getValue(ISSTRUCT) && playerIn.getHeldItem(EnumHand.MAIN_HAND) == ItemStack.EMPTY){
			TileEntity te = worldIn.getTileEntity(pos);
			if( te != null && te instanceof ITileMaster && !((ITileMaster)te).getStructure().isStructured){
				((ITileMaster)te).getStructure().batteryCheckAndSetupStructure(worldIn, pos, playerIn);
			}
			return true;
		}
		if(state.getValue(ISSTRUCT) == true){
			playerIn.openGui(BigBattery.instance, ModGuis.guiIDPlug, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
        return false;
    }
    

	@Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
        	//CHECK STRUCTURE
        }
    }
	
	@Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);

        if (!worldIn.isRemote)
        {
        	//RESET STRUCTURE
        }
    }

	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!worldIn.isRemote)
        {
        	//CHECK STRUCTURE
        }
    }

	@Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor){
    	
    }

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING,ISSTRUCT});
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta%6)).withProperty(ISSTRUCT, meta >= 6);
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
        int i = ((EnumFacing)state.getValue(FACING)).getIndex();
        return state.getValue(ISSTRUCT) == true ? i + 6 : i;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
			tooltip.add(I18n.format("tile.blockPlug.desc"));
		}else{
			tooltip.add(I18n.format("item.itemShift.desc", TextFormatting.WHITE + "" + TextFormatting.ITALIC));
		}
    }
	/*@Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

	@Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }*/
}
