package noelflantier.bigbattery.common.items;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.MapMaker;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noelflantier.bigbattery.common.handlers.ModItems;

public class ItemChloreFilter extends ItemBB{
	
	public static Map<BlockPos, Integer> listFilterClicked = new MapMaker().weakKeys().weakValues().makeMap();
	public static int rangeChlore = 4;
	public static int tickChlore = 1000;
	public static int baseDrop = 0;
	public static Random rdm = new Random();

	public static ToolMaterial FILTER = EnumHelper.addToolMaterial("BB_FILTER", 0, 1000, 0, 0, 14);
    
    public ItemChloreFilter()
    {
		super();
        maxStackSize = 1;
        setMaxDamage(FILTER.getMaxUses());
	}

	@Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
	
    public int getItemEnchantability()
    {
        return FILTER.getEnchantability();
    }
    
    public String getMaterialName()
    {
        return FILTER.toString();
    }
    
	public boolean isPosOutOfChlore(BlockPos posClicked, BlockPos oldPos){
		int r = rangeChlore <= 0 ? 1 : rangeChlore;
		return posClicked.getDistance(oldPos.getX(), oldPos.getY(), oldPos.getZ()) < r;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

        if(worldIn.isRemote)
        	return new ActionResult(EnumActionResult.PASS, itemstack);
        
        if (raytraceresult == null)
        {
            return new ActionResult(EnumActionResult.PASS, itemstack);
        }
        else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
        {
            return new ActionResult(EnumActionResult.PASS, itemstack);
        }
        else
        {
            BlockPos blockpos = raytraceresult.getBlockPos();
            	
        	IBlockState iblockstate = worldIn.getBlockState(blockpos);
            Material material = iblockstate.getMaterial();

            if (material == Material.WATER && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
            {
                boolean nochlore = listFilterClicked.entrySet().stream().anyMatch(b->isPosOutOfChlore(blockpos, b.getKey()));
                if(nochlore)
                    return new ActionResult(EnumActionResult.PASS, itemstack);
                listFilterClicked.put(blockpos, tickChlore);
                itemstack.damageItem(1, playerIn);
                int lvlf = (int) Math.pow(EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("fortune"), itemstack),1.5);
	            worldIn.spawnEntity(new EntityItem(worldIn, blockpos.getX()+0.5, blockpos.getY()+0.5, blockpos.getZ()+0.5, new ItemStack(ModItems.itemDustChlore, rdm.nextInt(5) + baseDrop + (int)(Math.floor(lvlf/2)) + rdm.nextInt(lvlf + 1))));
                return new ActionResult(EnumActionResult.SUCCESS, itemstack);
            }
            
            return new ActionResult(EnumActionResult.PASS, itemstack);
        }
    }
}
