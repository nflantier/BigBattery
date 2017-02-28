package noelflantier.bigbattery.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import noelflantier.bigbattery.BigBattery;

public abstract class ABlockBB extends Block{

	public ABlockBB(Material materialIn) {
		super(materialIn);
		this.setCreativeTab(BigBattery.bbTabs);
	}

}
