package noelflantier.bigbattery.common.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileConductive extends ATileBB implements ITileHaveMaster{

	public BlockPos master;
	
	public TileConductive(){
		super();
	}

	@Override
	public BlockPos getMasterPos() {
		return master;
	}

	@Override
	public World getWorldForMaster() {
		return this.getWorld();
	}
	
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        writeMasterToNBT(nbt);
        return nbt;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        master = readMasterFromNBT(nbt);
    }

	@Override
	public void setMaster(BlockPos pos) {
		this.master = pos;		
	}
}

