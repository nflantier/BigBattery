package noelflantier.bigbattery.common.tiles;

import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import noelflantier.bigbattery.common.blocks.BlockInterface;
import noelflantier.bigbattery.common.container.InventoryInterface;
import noelflantier.bigbattery.common.handlers.ModConfig;
import noelflantier.bigbattery.common.handlers.ModProperties;
import noelflantier.bigbattery.common.handlers.ModProperties.InterfaceType;
import noelflantier.bigbattery.common.network.PacketHandler;
import noelflantier.bigbattery.common.network.messages.PacketInterface;

public class TileInterface extends TileSimpleHM implements ITickable{
	
	public InventoryInterface inventory = new InventoryInterface(19);
	public class DummyInventoryInterface extends InventoryInterface{

        public DummyInventoryInterface( InventoryInterface stackhandler){
            this.stacks = stackhandler.getStacks();
            this.type = inventory.type;
        }
    	@Override
        @Nonnull
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
        {
            if(stack.isEmpty())
                return ItemStack.EMPTY;
            if(slotsFilter.contains(slot))
            	return stack;
            return super.insertItem(slot, stack, simulate);
        }
    }
	
	public TankInterface tank = new TankInterface(ModConfig.amountFluidInterfaceCapacity);
	
	public TileInterface(){
		super();
	}
	public TileInterface(InterfaceType value) {
		super();
		inventory.type = value;
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return false;
    }
	
	@Override
	public void update() {
		if(world.isRemote)
			return;
		if(inventory.type == null)
			inventory.type = world.getBlockState(getPos()).getValue(BlockInterface.INTERFACE_TYPE);
		
		ITileMaster t = getTileMaster();
		if(t == null || t.getStructure() == null || !t.getStructure().isStructured){
		}else{
			for( int i = 0 ; i < inventory.getStacks().size() ; i++){
				if(InventoryInterface.slotsFilter.contains(i))
					continue;
				if(inventory.getStacks().get(i).isEmpty())
					continue;
				ActionResult<ItemStack> ar = t.getStructure().handleConsumeStack(getWorld(), inventory.getStacks().get(i), inventory.type);
				if(ar.getType() == EnumActionResult.SUCCESS){
					inventory.getStacks().set(i, ar.getResult());
					break;
				}
			}
			if(inventory.type == ModProperties.InterfaceType.ELECTROLYTE){
				if(tank.getFluid()!=null && tank.getFluid().amount>0){
					ActionResult<FluidStack> ar = t.getStructure().handleConsumeFluidStack(getWorld(), tank.getFluid(), inventory.type);
					if(ar.getType() == EnumActionResult.SUCCESS){
						tank.setFluid(ar.getResult());
					}
				}
			}
		}	
		
    	if(inventory.type == ModProperties.InterfaceType.ELECTROLYTE && tank.getFluid() != null && tank.getFluid().getFluid()!=null)
    		PacketHandler.sendToAllAround(new PacketInterface(this.getPos(), tank.getFluidAmount(), tank.getFluid().getFluid().getName()),this);
    	else
    		PacketHandler.sendToAllAround(new PacketInterface(this.getPos(), tank.getFluidAmount()),this);
	}
	
	public boolean recieveFluid(FluidStack stack){
		return false;
	}
	
	@Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            if(facing==null)
            	return (T) inventory;
            return (T) new DummyInventoryInterface( inventory );
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T)tank;
        return super.getCapability(capability, facing);
    }
    
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setTag("capabilityInventory",CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY , getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), null));
        nbt.setTag("capabilityTank",CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY , getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,null), null));
        nbt.setInteger("typeinterface", inventory.type.ordinal());
        return nbt;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if(nbt.getTag("capabilityInventory") != null)
        	CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY , getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ,null), null, nbt.getTag("capabilityInventory"));
        if(nbt.getTag("capabilityTank") != null)
        	CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY , getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ,null), null, nbt.getTag("capabilityTank"));
        inventory.type = ModProperties.InterfaceType.values()[nbt.getInteger("typeinterface")]; 
    }

}
