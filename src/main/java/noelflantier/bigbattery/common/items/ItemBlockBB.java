package noelflantier.bigbattery.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import noelflantier.bigbattery.BigBattery;

public class ItemBlockBB extends ItemBlock{

	public ItemBlockBB(Block block) {
		super(block);
		this.setCreativeTab(BigBattery.bbTabs);
	}

}
