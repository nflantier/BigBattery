package noelflantier.bigbattery.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class ABlockBBStructure  extends ABlockContainerBB {
    public static final PropertyBool ISSTRUCT = PropertyBool.create("isstruct");

	protected ABlockBBStructure(Material materialIn) {
		super(materialIn);
	}

    public abstract TileEntity createNewTileEntity(World worldIn, int meta);

}
