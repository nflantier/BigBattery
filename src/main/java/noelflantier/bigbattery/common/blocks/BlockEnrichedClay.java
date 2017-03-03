package noelflantier.bigbattery.common.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.handlers.ModItems;

public class BlockEnrichedClay extends ABlockBB{

	public BlockEnrichedClay(Material materialIn) {
		super(materialIn);
		setRegistryName(Ressources.UL_NAME_BLOCK_ENRICHED_CLAY);
		setUnlocalizedName(Ressources.UL_NAME_BLOCK_ENRICHED_CLAY);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        return true;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
        List<ItemStack> ret = new ArrayList<ItemStack>();

        Random rand = world instanceof World ? ((World)world).rand : RANDOM;
        ret.add(new ItemStack(Items.CLAY_BALL, rand.nextInt(3)));
        ret.add(new ItemStack(ModItems.itemDustLithium, 2 + rand.nextInt(2) + rand.nextInt(fortune + 1)));
        return ret;
    	
    }
}
