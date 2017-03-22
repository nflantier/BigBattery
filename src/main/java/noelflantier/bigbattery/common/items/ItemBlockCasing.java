package noelflantier.bigbattery.common.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noelflantier.bigbattery.common.blocks.BlockCasing;
import noelflantier.bigbattery.common.handlers.ModProperties.CasingType;

public class ItemBlockCasing  extends ItemBlockBB{

	public ItemBlockCasing(Block block) {
		super(block);
	}
	
	@Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
    	return super.getUnlocalizedName()+"."+CasingType.getType((int) Math.floor(itemstack.getItemDamage()/2)).getName();
    }
	
	@Override
    public int getMetadata(int damage)
    {
        return damage;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
    	for(int i = 0 ; i < CasingType.values().length ; i++){
    		subItems.add(new ItemStack(itemIn, 1, block.getMetaFromState(block.getDefaultState().withProperty(BlockCasing.CASING_TYPE, CasingType.getType(i)))));
    	}
    }
}
