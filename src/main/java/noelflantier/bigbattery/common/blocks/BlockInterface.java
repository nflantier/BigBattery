package noelflantier.bigbattery.common.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noelflantier.bigbattery.BigBattery;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.handlers.ModConfig;
import noelflantier.bigbattery.common.handlers.ModGuis;
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if(worldIn.isRemote || playerIn.isSneaking() || hand == EnumHand.OFF_HAND)
			return true;
		if(playerIn.getHeldItem(hand)!= null){
			IFluidHandler fhitem = FluidUtil.getFluidHandler(playerIn.getHeldItem(hand));
			if(fhitem!=null){
	    		TileEntity t = worldIn.getTileEntity(pos);
	    		if (t != null && t.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing))
	            {
	    			IFluidHandler fhtile = t.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
		    		if(fhtile!=null){
		    			FluidActionResult far = FluidUtil.tryEmptyContainerAndStow(playerIn.getHeldItemMainhand(), fhtile, null, ModConfig.amountFluidInterfaceCapacity, playerIn);
		    			return !far.isSuccess();
		    		}
	            }
    		}
		}
		playerIn.openGui(BigBattery.instance, ModGuis.guiIDInterface, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
    
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileInterface(getStateFromMeta(meta).getValue(INTERFACE_TYPE));
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
	
	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
		if(te != null){
			harvesters.set(player);
			
			ItemStack itemStack = new ItemStack(getItemDropped(state, null, 0), 1, damageDropped(state));
			NBTTagCompound compound = new NBTTagCompound();
			compound.setTag("BlockEntityTag", te.writeToNBT(new NBTTagCompound()));
		    itemStack.setTagCompound(itemStack.writeToNBT(compound));
			List<ItemStack> l = new ArrayList<ItemStack>(){{
				add(itemStack);
			}};

            net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(l, worldIn, pos, state, 0, 1.0F, false, harvesters.get());
            for (ItemStack item : l)
            {
                if (worldIn.rand.nextFloat() <= 1.0F)
                {
                    spawnAsEntity(worldIn, pos, item);
                }
            }
	        harvesters.set(null);
		}else
			super.harvestBlock(worldIn, player, pos, state, te, stack);
    }

}
