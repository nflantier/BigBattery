package noelflantier.bigbattery.common.container;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import noelflantier.bigbattery.common.handlers.ModItems;
import noelflantier.bigbattery.common.handlers.ModProperties.InterfaceType;
import noelflantier.bigbattery.common.helpers.MultiBlockBattery.MaterialsBattery;
import noelflantier.bigbattery.common.materials.MaterialsHandler;

public class InventoryInterface extends ItemStackHandler{
	
   	public InterfaceType type = null;
	public static final int slotFilter = 18;
   	
   	public InventoryInterface() {
		super();
	}
	public InventoryInterface(int i) {
		super(i);
	}

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack)
    {
    	if(slot==slotFilter){
    		
    	}else
    		super.setStackInSlot(slot, stack);
    	
    }
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
    	return super.extractItem(slot, amount, simulate);
    }
   	@Override
   	public int getSlotLimit(int slot)
   	{
   		return slot == slotFilter ? 1 : 64;
   	}
   	
	@Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if(stack.isEmpty())
            return ItemStack.EMPTY;

        if(!getStackInSlot(slotFilter).isEmpty()){
        	if(!getStackInSlot(slotFilter).isItemEqualIgnoreDurability(stack))
        		return stack;
        }
        return isItemValidForSlot(slot, stack) ? super.insertItem(slot, stack, simulate) : stack;
    }
	
	public NonNullList<ItemStack> getStacks(){
		return stacks;
	}
	
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == slotFilter)
			return true;
		if(type == null)
			return false;
		switch(type){
			case ELECTRODE : 
				return MaterialsHandler.anyMatchElectrode(stack);
			case ELECTROLYTE :
				return MaterialsHandler.anyMatchElectrolyte(stack);
			default:
				break;
		}
		return false;
	}
}
