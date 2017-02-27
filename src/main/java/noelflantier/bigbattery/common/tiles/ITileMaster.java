package noelflantier.bigbattery.common.tiles;

import net.minecraft.util.math.BlockPos;
import noelflantier.bigbattery.common.helpers.MultiBlockBattery;
import noelflantier.bigbattery.common.helpers.MultiBlockMessage;

public interface ITileMaster
{
	void fromPleb(BlockPos fromPos, MultiBlockMessage message);
	MultiBlockBattery getStructure();
	void setEnergyCapacity();
}
