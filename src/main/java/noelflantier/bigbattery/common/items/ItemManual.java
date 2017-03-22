package noelflantier.bigbattery.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import noelflantier.bigbattery.BigBattery;
import noelflantier.bigbattery.common.handlers.ModGuis;

public class ItemManual extends ItemBB{

	public ItemManual() {
		super();
		this.setMaxStackSize(1);
	}
	

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		playerIn.openGui(BigBattery.instance, ModGuis.guiIDManual, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
		return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
