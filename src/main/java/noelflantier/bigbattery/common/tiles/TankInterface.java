package noelflantier.bigbattery.common.tiles;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TankInterface extends FluidTank{

	public TankInterface(int capacity) {
		super(capacity);
	}

    public void setAmount(int amount)
    {
    	if(fluid!=null)
    		fluid.amount = amount;
    }
    
    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        return super.fill(resource, doFill);
    }
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain)
    {
    	return super.drain(resource, doDrain);
    }
}
