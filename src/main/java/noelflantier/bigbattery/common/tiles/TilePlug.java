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
import noelflantier.bigbattery.common.handlers.ModConfig;
import noelflantier.bigbattery.common.helpers.MultiBlockBattery;
import noelflantier.bigbattery.common.helpers.MultiBlockMessage;
import noelflantier.bigbattery.common.network.PacketHandler;
import noelflantier.bigbattery.common.network.messages.PacketPlug;

public class TilePlug extends ATileBBTicking implements ITileMaster{

	public MultiBlockBattery mbb = new MultiBlockBattery();
    public EnergyStoragePlug energyStorage = new EnergyStoragePlug(0);//capacity equal the amount of rf generated in one tick from the current tick structure
	public int capacity = 0;
	public int lastEnergyStoredAmount = -1;
	public int currentRF = -1;
	public boolean isGenerating = false;
	
	public TilePlug(){
		super();
	}

	@Override
	public void update() {
		if(this.world.isRemote)
			return;
		if(mbb.plugPos == null)
			mbb.plugPos = getPos();
		if(!getStructure().isStructured)
			return;
		energyStorage.extractEnergy(100000, false);
		if(energyStorage.getEnergyStored()>=energyStorage.getMaxEnergyStored()){
			//setEnergyCapacity();
			return;
		}
		currentRF = (int) mbb.materialsBattery.generateEnergy();
		int truerf = energyStorage.recieve(currentRF,true);
		if(truerf>0 && currentRF>0){
			int g = energyStorage.recieve(currentRF,false);
			if(ModConfig.areBatteryalwaysGenerating){
				truerf = currentRF;
			}
			mbb.materialsBattery.handleMaterials(getWorld(), (float)truerf/(float)currentRF);
		}else
			setEnergyCapacity();
		sendPacketPlug();
		
		lastEnergyStoredAmount = energyStorage.getEnergyStored();
	}

	public void sendPacketPlug(){
    	PacketHandler.sendToAllAround(new PacketPlug(getPos(), energyStorage.getEnergyStored(), lastEnergyStoredAmount, currentRF, mbb.materialsBattery.getMaterialsId(), mbb.getMPValues() ),this);
	}

	@Override
	public void setEnergyCapacity() {
		capacity = (int) mbb.materialsBattery.generateEnergy();
		energyStorage.setCapacity(capacity);
		sendPacketPlug();
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
        energyStorage = new EnergyStoragePlug(capacity);
        //energyStorage.receiveEnergy(nbt.getInteger("energy"), false);
        if(nbt.getTag("capabilityEnergy") != null)
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
		energyStorage.setEnergy(energy);
	}
}
