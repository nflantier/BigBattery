package noelflantier.bigbattery.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import noelflantier.bigbattery.common.tiles.TilePlug;

public class ContainerPlug extends Container {

	public TilePlug tplug;
	public ContainerPlug(InventoryPlayer inventory,TilePlug tile){
		this.tplug = tile;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq(tplug.getPos().getX()+0.5F, tplug.getPos().getY()+0.5F, tplug.getPos().getZ()+0.5F)<=64;
	}

}
