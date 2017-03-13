package noelflantier.bigbattery.common.tiles;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyStoragePlug extends EnergyStorage{

	public EnergyStoragePlug(int capacity) {
		super(capacity);
	}
	
	public void setEnergy(int e){
        energy = e;
	}
	
	public void setCapacity(int e){
        capacity = e;
        maxReceive = e;
        maxExtract = e;
	}
	
	public int recieve(int maxReceive, boolean simulate){
		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            energy += energyReceived;
        return energyReceived;
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
