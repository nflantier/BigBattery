package noelflantier.bigbattery.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import noelflantier.bigbattery.common.tiles.TilePlug;

public class ContainerPlug extends Container {

	public TilePlug tile;
	public ContainerPlug(InventoryPlayer inventory,TilePlug tile){
		this.tile = tile;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq(tile.getPos().getX()+0.5F, tile.getPos().getY()+0.5F, tile.getPos().getZ()+0.5F)<=64;
	}

}
