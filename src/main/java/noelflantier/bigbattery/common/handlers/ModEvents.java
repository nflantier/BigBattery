package noelflantier.bigbattery.common.handlers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEvents {
	public static ModEvents INSTANCE;
	public static Map<Block,ItemStack> mapCustomDrop = new HashMap<Block,ItemStack>();
	
	
	public static void preInitCommon(){
		INSTANCE = new ModEvents() ;
	}
	
	public static void postInitCommon(){
		mapCustomDrop.put(Blocks.CLAY, new ItemStack(ModItems.itemDustLithium,4));
	}
	
    @SubscribeEvent
    public void onClonePlayer(BlockEvent.BreakEvent event) {
    	
    }	
}
