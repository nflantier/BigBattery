package noelflantier.bigbattery.common.container;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import noelflantier.bigbattery.common.handlers.ModProperties.InterfaceType;
import noelflantier.bigbattery.common.helpers.MultiBlockBattery.MaterialsBattery;
import noelflantier.bigbattery.common.materials.MaterialsHandler;

public class InventoryInterface extends ItemStackHandler{
	
   	public InterfaceType type = null;
   	
	public InventoryInterface(int i) {
		super(i);
	}
	
	@Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if(stack.isEmpty())
            return ItemStack.EMPTY;
        return isItemValidForSlot(slot, stack) ? super.insertItem(slot, stack, simulate) : stack;
    }
	
	
	
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
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
