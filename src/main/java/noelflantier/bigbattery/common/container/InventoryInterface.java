package noelflantier.bigbattery.common.container;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;
import noelflantier.bigbattery.common.handlers.ModProperties.InterfaceType;
import noelflantier.bigbattery.common.materials.MaterialsHandler;

public class InventoryInterface extends ItemStackHandler{
	
   	public InterfaceType type = null;
	public static final int slotFilter1 = 18;
	public static final int slotFilter2 = 19;
	public boolean change = false;
	public static final List<Integer> slotsFilter = new ArrayList<Integer>(){{add(slotFilter1);}};
   	
   	public InventoryInterface() {
		super();
	}
	public InventoryInterface(int i) {
		super(i);
	}

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack)
    {
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
   		return slotsFilter.contains(slot) ? 1 : 64;
   	}
   	
	@Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if(stack.isEmpty())
            return ItemStack.EMPTY;
        return isItemValidForSlot(slot, stack) ? super.insertItem(slot, stack, simulate) : stack;
    }
	
	public NonNullList<ItemStack> getStacks(){
		return stacks;
	}
	
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slotsFilter.contains(slot))
			return true;
		if(type == null)
			return false;
        if(slotsFilter.stream().anyMatch(i->!getStackInSlot(i).isEmpty()))
        	if(slotsFilter.stream().noneMatch(i->MaterialsHandler.areItemStackSameOre(getStackInSlot(i),stack, false)))
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

    protected void onContentsChanged(int slot)
    {
    	change = true;
    }
}
