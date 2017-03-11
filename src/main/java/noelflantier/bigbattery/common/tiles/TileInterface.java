package noelflantier.bigbattery.common.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import noelflantier.bigbattery.common.container.InventoryInterface;
import noelflantier.bigbattery.common.handlers.ModProperties.InterfaceType;
import noelflantier.bigbattery.common.materials.MaterialsHandler;
import noelflantier.bigbattery.common.materials.MaterialsHandler.Electrode;
import noelflantier.bigbattery.common.materials.MaterialsHandler.Electrolyte;

public class TileInterface extends TileSimpleHM implements ITickable{
	
	public InventoryInterface inventory = new InventoryInterface(27);
   	public FluidTank tank = new FluidTank(10000);
	
	public TileInterface(){
		super();
	}
	public TileInterface(InterfaceType value) {
		super();
		inventory.type = value;
	}
	
	@Override
	public void update() {
	}
		
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T)inventory;
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T)tank;
        return super.getCapability(capability, facing);
    }
    
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setTag("capabilityInventory",CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY , getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null), null));
        nbt.setTag("capabilityTank",CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY , getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,null), null));
        
        return nbt;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if(nbt.getTag("capabilityInventory") != null)
        	CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY , getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ,null), null, nbt.getTag("capabilityInventory"));
        if(nbt.getTag("capabilityTank") != null)
        	CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY , getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ,null), null, nbt.getTag("capabilityTank"));
    
    }

}
