package noelflantier.bigbattery.common.container;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
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
			super.isItemValid(stack);
	        ((InventoryInterface) this.getItemHandler()).getStacks().set(this.getSlotIndex(), ItemHandlerHelper.copyStackWithSize(stack, 1));
	        return false;
	    }    
		@Override
	    public boolean canTakeStack(EntityPlayer playerIn)
	    {
	        ((InventoryInterface) this.getItemHandler()).getStacks().set(this.getSlotIndex(), ItemStack.EMPTY);
	        return super.canTakeStack(playerIn);
	    }
		@Override
		public int getSlotStackLimit()
	    {
	        return 1;
	    }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq(tile.getPos().getX()+0.5F, tile.getPos().getY()+0.5F, tile.getPos().getZ()+0.5F)<=64;
	}

}
