package noelflantier.bigbattery.common.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import noelflantier.bigbattery.BigBattery;
import noelflantier.bigbattery.common.materials.MaterialsHandler;
import noelflantier.bigbattery.common.tiles.TilePlug;

public class PacketPlug  implements IMessage, IMessageHandler<PacketPlug, IMessage> {
	
	public int x;
	public int y;
	public int z;
	public int energy;
	public int lastenergystored;
	public int currentRF;
	public int [] idmaterials;
	
	public PacketPlug(){
	
	}

	public PacketPlug(BlockPos pos, int energy){
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.energy = energy;
		this.lastenergystored = -1;
	}

	public PacketPlug(BlockPos pos, int energy, int lastenergystored, int currentRF, int [] idmaterials){
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.energy = energy;
		this.lastenergystored = lastenergystored;
		this.currentRF = currentRF;
		this.idmaterials = idmaterials;
	}

	@Override
	public IMessage onMessage(PacketPlug message, MessageContext ctx) {
		if (ctx.side.isClient()) {
			BigBattery.myProxy.getThreadFromContext(ctx).addScheduledTask(new Runnable(){
				@Override
				public void run() {
					TileEntity te = Minecraft.getMinecraft().player.getEntityWorld().getTileEntity(new BlockPos(message.x,message.y, message.z));
					if(te!=null && te instanceof TilePlug) {
						((TilePlug)te).setEnergy(message.energy);
						((TilePlug)te).lastEnergyStoredAmount = message.lastenergystored;
						((TilePlug)te).currentRF = message.currentRF;
						((TilePlug)te).mbb.materialsBattery.electrode1 = message.idmaterials[0]!=-1 ? MaterialsHandler.electrodeListByPotential.get(message.idmaterials[0]) : null;
						((TilePlug)te).mbb.materialsBattery.electrode2 = message.idmaterials[1]!=-1 ? MaterialsHandler.electrodeListByPotential.get(message.idmaterials[1]) : null;
						((TilePlug)te).mbb.materialsBattery.electrolyte = message.idmaterials[2]!=-1 ? MaterialsHandler.electrolyteListByPotential.get(message.idmaterials[2]) : null;
						((TilePlug)te).mbb.materialsBattery.electrode1Cond = message.idmaterials[3]!=-1 ? MaterialsHandler.conductiveListByRatio.get(message.idmaterials[3]) : null;
						((TilePlug)te).mbb.materialsBattery.electrode2Cond = message.idmaterials[4]!=-1 ? MaterialsHandler.conductiveListByRatio.get(message.idmaterials[4]) : null;
					}
				}}
			);
		}
		return null;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
	    x = buf.readInt();
	    y = buf.readInt();
	    z = buf.readInt();
	    energy = buf.readInt();
	    lastenergystored = buf.readInt();
	    currentRF = buf.readInt();
	    
	    int l = buf.readInt();
	    idmaterials = new int [l];
	    for(int j =0;j<l;j++)
	    	idmaterials[j] = buf.readInt();
		
	}
	@Override
	public void toBytes(ByteBuf buf) {
	    buf.writeInt(x);
	    buf.writeInt(y);
	    buf.writeInt(z);
	    buf.writeInt(energy);
	    buf.writeInt(lastenergystored);
	    buf.writeInt(currentRF);
	    
	    buf.writeInt(idmaterials.length);
	    for(int j =0;j<idmaterials.length;j++)
	    	buf.writeInt(idmaterials[j]);
		
	}
}
