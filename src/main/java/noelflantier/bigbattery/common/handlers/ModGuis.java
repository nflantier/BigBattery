package noelflantier.bigbattery.common.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import noelflantier.bigbattery.client.gui.GuiInterface;
import noelflantier.bigbattery.client.gui.GuiPlug;
import noelflantier.bigbattery.common.container.ContainerInterface;
import noelflantier.bigbattery.common.container.ContainerPlug;
import noelflantier.bigbattery.common.tiles.TileInterface;
import noelflantier.bigbattery.common.tiles.TilePlug;

public class ModGuis implements IGuiHandler {
	public static final int guiIDPlug = 0;
	public static final int guiIDInterface = 1;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x,y,z));
		if(tile!=null){
			switch(ID){
				case ModGuis.guiIDPlug :
					if(tile instanceof TilePlug){
						return new ContainerPlug(player.inventory, (TilePlug)tile);
					}
					return null;
				case ModGuis.guiIDInterface :
					if(tile instanceof TileInterface){
						return new ContainerInterface(player.inventory, (TileInterface)tile);
					}
					return null;
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x,y,z));
		if(tile!=null){
			switch(ID){
				case ModGuis.guiIDPlug :
					if(tile instanceof TilePlug){
						return new GuiPlug(player.inventory, (TilePlug)tile);
					}
					return null;
				case ModGuis.guiIDInterface :
					if(tile instanceof TileInterface){
						return new GuiInterface(player.inventory, (TileInterface)tile);
					}
					return null;
			}
		}
		return null;
	}
}
