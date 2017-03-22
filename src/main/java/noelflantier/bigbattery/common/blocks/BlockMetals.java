package noelflantier.bigbattery.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockMetals extends ABlockBB{

	public BlockMetals(Material materialIn, String ulname) {
		super(materialIn);
		setRegistryName(ulname);
        setUnlocalizedName(ulname);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
	}

}
