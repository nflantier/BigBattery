package noelflantier.bigbattery.common.handlers;

import java.util.stream.Collectors;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import noelflantier.bigbattery.common.items.ItemChloreFilter;

public class ModEvents {
	public static ModEvents INSTANCE;

	public long getServerTick(){return this.serverTickCount;}
	public long getClientTick(){return this.clientTickCount;}	
    @SubscribeEvent
    public void onTick(ServerTickEvent evt) {if(evt.phase == Phase.END)serverTickCount++;}
    @SubscribeEvent
    public void onTick(ClientTickEvent evt) {if(evt.phase == Phase.END)clientTickCount++;}
    
	public static void preInitCommon(){
		INSTANCE = new ModEvents() ;
	}
	
	public static void postInitCommon(){
		
	}	
	protected long serverTickCount = 0;
	protected long clientTickCount = 0;
	
	public static void init(){
		INSTANCE = new ModEvents() ;
	}
    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
    	if(event.phase == Phase.END) {
    		if(ItemChloreFilter.listFilterClicked.isEmpty())
    			return;
    		ItemChloreFilter.listFilterClicked.entrySet().stream().forEach((m)->m.setValue(m.getValue()-1));
    		ItemChloreFilter.listFilterClicked.entrySet().stream().filter((m)->m.getValue()<0).collect(Collectors.toList());
    		
    		if(ItemChloreFilter.listFilterClicked.size()>100)//SAFESTATE
    			ItemChloreFilter.listFilterClicked.clear();
    	}
    }
	
}
