package noelflantier.bigbattery.common.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import noelflantier.bigbattery.common.helpers.MultiBlockMessage;

public interface ITileHaveMaster {
	void setMaster(BlockPos pos);
    BlockPos getMasterPos();
    World getWorldForMaster();
    default boolean toMaster(BlockPos from, MultiBlockMessage message){
	    if(getWorldForMaster()==null || getMasterPos()==null)
			return false;
		TileEntity t = getWorldForMaster().getTileEntity(getMasterPos());
		if(t!=null && t instanceof ITileMaster){
			((ITileMaster)t).fromPleb(from, message);
			return true;
		}
		return false;
    }
    
    default NBTTagCompound writeMasterToNBT(NBTTagCompound nbt){
    	if(getMasterPos()!=null){
	        nbt.setInteger("masterx", getMasterPos().getX());
	        nbt.setInteger("mastery", getMasterPos().getY());
	        nbt.setInteger("masterz", getMasterPos().getZ());
        	nbt.setBoolean("hasmaster", true);
        }else
        	nbt.setBoolean("hasmaster", false);
    	return nbt;
    }
    
    default BlockPos readMasterFromNBT(NBTTagCompound nbt){
    	if(nbt.getBoolean("hasmaster"))
    		return new BlockPos(nbt.getInteger("masterx"),nbt.getInteger("mastery"),nbt.getInteger("masterz"));	
    	return null;
    }
}
