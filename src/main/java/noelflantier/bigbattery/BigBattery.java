package noelflantier.bigbattery;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noelflantier.bigbattery.common.CommonProxy;

@Mod(modid = Ressources.MODID, name = Ressources.NAME, version = Ressources.VERSION)
public class BigBattery {
	
	public static CreativeTabs bbTabs;
	@SidedProxy(clientSide = Ressources.CLIENT_PROXY, serverSide = Ressources.SERVER_PROXY)
	public static CommonProxy myProxy;
	
	@Instance(Ressources.MODID)
	public static BigBattery instance;
	
	public static SimpleNetworkWrapper networkWrapper;
	
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	bbTabs = new CreativeTabs("SFArtifacts"){
    		@SideOnly(Side.CLIENT)
    		public ItemStack getTabIconItem(){
    			return new ItemStack(Items.APPLE);
    		}
    	};
    	myProxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	myProxy.init(event);
    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent event)
    {
    	myProxy.postinit(event);
    }
}
