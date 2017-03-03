package noelflantier.bigbattery.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import noelflantier.bigbattery.Ressources;

public class PacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Ressources.MODID);
	private static int ID = 0;
	
	public static int nextId(){
		return ID++;
	}
	public static void sendToAllAround(IMessage message, TileEntity tile){
    	INSTANCE.sendToAllAround(message, new NetworkRegistry.TargetPoint(tile.getWorld().provider.getDimension(),tile.getPos().getX(),tile.getPos().getY(),tile.getPos().getZ(),64));
	}
	
	public static void sendToAllAroundPlayer(IMessage message, EntityPlayer player){
    	INSTANCE.sendToAllAround(message, new NetworkRegistry.TargetPoint(player.getEntityWorld().provider.getDimension(),player.posX,player.posY,player.posZ,16));
	}
	
	public static void sendToPlayerMP(IMessage message, EntityPlayerMP player){
    	INSTANCE.sendTo(message, player);
	}
	
	public static void sendToAll(IMessage message, EntityPlayerMP player){
    	INSTANCE.sendToAll(message);
	}
	
	public static void sendToTargetPoint(IMessage message, World world, BlockPos pos){
    	INSTANCE.sendToAllAround(message, new NetworkRegistry.TargetPoint(world.provider.getDimension(),pos.getX(), pos.getY(), pos.getZ(),64));
	}
}
