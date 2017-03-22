package noelflantier.bigbattery.common.container;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import noelflantier.bigbattery.common.tiles.TileInterface;

public class ContainerInterface extends Container {

	public TileInterface tile;
	public int slotId = 0;
	public int nextId(){return this.slotId++;}
	
	public ContainerInterface(InventoryPlayer inventory,TileInterface tile){
		this.tile = tile;
		for(int x = 0 ; x < 9 ; x++){
			addSlotToContainer(new Slot(inventory,x,8+18*x,147));
		}
		
		for(int x = 0 ; x < 9 ; x++)
			for(int y = 0 ; y < 3 ; y++)
				addSlotToContainer(new Slot(inventory,x+y*9+9,8+18*x,89+18*y));
		
		for(int x = 0 ; x < 9 ; x++)
			for(int y = 0 ; y < 2 ; y++)
				addSlotToContainer(new MaterialSlot(tile.inventory,nextId(),8+18*x,16+18*y));
		
		addSlotToContainer(new DummySlot(tile.inventory,nextId(),8,56));
	}
	
	private class MaterialSlot extends SlotItemHandler {

		public MaterialSlot(IItemHandler inv, int id,int x, int y) {
			super(inv, id, x, y);
		}
		
		@Override
	    public boolean isItemValid(ItemStack stack)
	    {
	        return ((InventoryInterface)getItemHandler()).isItemValidForSlot(this.getSlotIndex(), stack);
	    }    

		@Override
		public int getSlotStackLimit()
	    {
	        return 64;
	    }
	}
	
	private class DummySlot extends SlotItemHandler {

		public DummySlot(IItemHandler inv, int id,int x, int y) {
			super(inv, id, x, y);
		}
		
		@Override
	    public boolean isItemValid(ItemStack stack)
	    {
			//return super.isItemValid(stack);
			//((InventoryInterface) this.getItemHandler()).getStacks().set(this.getSlotIndex(), ItemHandlerHelper.copyStackWithSize(stack, 1));
	        return true;
	    }
		@Override
	    public boolean canTakeStack(EntityPlayer playerIn)
	    {
	        //((InventoryInterface) this.getItemHandler()).getStacks().set(this.getSlotIndex(), ItemStack.EMPTY);
	        return true;
	    }
		@Override
		public int getSlotStackLimit()
	    {
	        return 1;
	    }    
		@Override
	    @Nonnull
	    public ItemStack decrStackSize(int amount)
	    {
			//((InventoryInterface) this.getItemHandler()).getStacks().set(this.getSlotIndex(), ItemStack.EMPTY);
	        //return ItemStack.EMPTY;
			return super.decrStackSize(amount);
	    }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq(tile.getPos().getX()+0.5F, tile.getPos().getY()+0.5F, tile.getPos().getZ()+0.5F)<=64;
	}

	@Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return !InventoryInterface.slotsFilter.contains(slotIn.getSlotIndex());
    }
    
	@Override
	public boolean canDragIntoSlot(Slot slotIn)
    {
        return !InventoryInterface.slotsFilter.contains(slotIn.getSlotIndex());
    }
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);
    	if(InventoryInterface.slotsFilter.contains(index-36)){
            return ItemStack.EMPTY;
    	}
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 36)
            {
                if (!this.mergeItemStack(itemstack1, 36, this.inventorySlots.size(), false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 36, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection)
    {
        boolean flag = false;
        int i = startIndex;

        if (reverseDirection)
        {
            i = endIndex - 1;
        }

        if (stack.isStackable())
        {
            while (!stack.isEmpty())
            {
            		
                if (reverseDirection)
                {
                    if (i < startIndex)
                    {
                        break;
                    }
                }
                else if (i >= endIndex)
                {
                    break;
                }
            	if(InventoryInterface.slotsFilter.contains(i-36)){

            	}else{
	                Slot slot = (Slot)this.inventorySlots.get(i);
	                ItemStack itemstack = slot.getStack();
	
	                if (!itemstack.isEmpty() && itemstack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack))
	                {
	                    int j = itemstack.getCount() + stack.getCount();
	                    int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());
	
	                    if (j <= maxSize)
	                    {
	                        stack.setCount(0);
	                        itemstack.setCount(j);
	                        slot.onSlotChanged();
	                        flag = true;
	                    }
	                    else if (itemstack.getCount() < maxSize)
	                    {
	                        stack.shrink(maxSize - itemstack.getCount());
	                        itemstack.setCount(maxSize);
	                        slot.onSlotChanged();
	                        flag = true;
	                    }
	                }
            	}
                if (reverseDirection)
                {
                    --i;
                }
                else
                {
                    ++i;
                }
            }
        }

        if (!stack.isEmpty())
        {
            if (reverseDirection)
            {
                i = endIndex - 1;
            }
            else
            {
                i = startIndex;
            }

            while (true)
            {
                if (reverseDirection)
                {
                    if (i < startIndex)
                    {
                        break;
                    }
                }
                else if (i >= endIndex)
                {
                    break;
                }

                Slot slot1 = (Slot)this.inventorySlots.get(i);
                ItemStack itemstack1 = slot1.getStack();

            	if(InventoryInterface.slotsFilter.contains(i-36)){
            	
            	}else{
	                if (itemstack1.isEmpty() && slot1.isItemValid(stack))
	                {
	                    if (stack.getCount() > slot1.getSlotStackLimit())
	                    {
	                        slot1.putStack(stack.splitStack(slot1.getSlotStackLimit()));
	                    }
	                    else
	                    {
	                        slot1.putStack(stack.splitStack(stack.getCount()));
	                    }
	
	                    slot1.onSlotChanged();
	                    flag = true;
	                    break;
	                }
            	}
                if (reverseDirection)
                {
                    --i;
                }
                else
                {
                    ++i;
                }
            }
        }

        return flag;
    }
	
}
