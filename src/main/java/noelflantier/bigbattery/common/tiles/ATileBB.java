package noelflantier.bigbattery.common.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class ATileBB  extends TileEntity{
	
	@Override
    public SPacketUpdateTileEntity getUpdatePacket(){
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), writeToNBT(new NBTTagCompound()));
    }
	
    public NBTTagCompound getUpdateTag(){
        return writeToNBT(new NBTTagCompound());
    }
    
	@Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
        readFromNBT(pkt.getNbtCompound());
    }
	
	@Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    	super.writeToNBT(compound);
        return compound;
    }
	
}
