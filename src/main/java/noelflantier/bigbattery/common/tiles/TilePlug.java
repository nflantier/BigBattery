package noelflantier.bigbattery.common.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import noelflantier.bigbattery.common.helpers.MultiBlockBattery;
import noelflantier.bigbattery.common.helpers.MultiBlockMessage;
import noelflantier.bigbattery.common.network.PacketHandler;
import noelflantier.bigbattery.common.network.messages.PacketPlug;

public class TilePlug extends ATileBBTicking implements ITileMaster, IEnergyStorage{

	public MultiBlockBattery mbb = new MultiBlockBattery();
    public EnergyStorage energyStorage = new EnergyStorage(0);//capacity equal the amount of rf generated in one tick from the current tick structure
	public int capacity = 0;
	public int lastEnergyStoredAmount = -1;
	public boolean isGenerating = false;
	
	public TilePlug(){
		super();
	}

	@Override
	public void update() {
		if(this.world.isRemote)
			return;
		if(!getStructure().isStructured)
			return;

		energyStorage.extractEnergy(1000000, false);
		if(energyStorage.getEnergyStored()>=energyStorage.getMaxEnergyStored())
			return;
		int rf = (int) mbb.materialsB.generateEnergy();
		int truerf = energyStorage.receiveEnergy(rf,true);
		if(truerf>0 && rf>0){
			energyStorage.receiveEnergy(rf,false);
			mbb.materialsB.handleMaterials(getWorld(), (float)truerf/(float)rf);
		}		
    	if(energyStorage.getEnergyStored()!=this.lastEnergyStoredAmount)
    		PacketHandler.sendToAllAround(new PacketPlug(this.getPos(), energyStorage.getEnergyStored(), this.lastEnergyStoredAmount),this);
    	
		lastEnergyStoredAmount= energyStorage.getEnergyStored();
	}


	@Override
	public void setEnergyCapacity() {
		capacity = (int) mbb.materialsB.generateEnergy();
		energyStorage = new EnergyStorage(capacity);	
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return false;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if (capability == CapabilityEnergy.ENERGY){
            return (T)energyStorage;
        }
        return super.getCapability(capability, facing);
    }
    
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        getStructure().writeToNBT(nbt);
        nbt.setInteger("capacity", capacity);
        //nbt.setInteger("energy", energyStorage.getEnergyStored());
        nbt.setTag("capabilityEnergy",CapabilityEnergy.ENERGY.getStorage().writeNBT(CapabilityEnergy.ENERGY , getCapability(CapabilityEnergy.ENERGY,null), null));
        return nbt;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        getStructure().readFromNBT(nbt);
        capacity = nbt.getInteger("capacity");
        energyStorage = new EnergyStorage(capacity);
        //energyStorage.receiveEnergy(nbt.getInteger("energy"), false);
        CapabilityEnergy.ENERGY.getStorage().readNBT(CapabilityEnergy.ENERGY, getCapability(CapabilityEnergy.ENERGY,null), null, nbt.getTag("capabilityEnergy"));
    }

	@Override
	public MultiBlockBattery getStructure() {
		return mbb;
	}

	@Override
	public void fromPleb(BlockPos fromPos, MultiBlockMessage message) {
		switch(message){
			case CHECK :
				if(getStructure().isStructured && getStructure().isBlockPartOfStructure(fromPos)){
					getStructure().batteryCheckAndSetupStructure(world, getPos(), null);
				}
				break;
			default:
				break;
		}
		
	}
	
	public void setEnergy(int energy){
		energyStorage.receiveEnergy(Math.abs(energyStorage.getEnergyStored()-energy), false);
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return energyStorage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored() {
		return energyStorage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored() {
		return energyStorage.getMaxEnergyStored();
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return false;
	}
}
