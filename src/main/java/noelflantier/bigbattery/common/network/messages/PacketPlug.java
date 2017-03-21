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
	public double [][] mpvalues;
	
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

	public PacketPlug(BlockPos pos, int energy, int lastenergystored, int currentRF, int [] idmaterials, double[][] mpvalues) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.energy = energy;
		this.lastenergystored = lastenergystored;
		this.currentRF = currentRF;
		this.idmaterials = idmaterials;
		this.mpvalues = mpvalues;
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
						((TilePlug)te).mbb.setMaterials(message.idmaterials);
						((TilePlug)te).mbb.setMaterialsValues(message.mpvalues);
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

	    int l2 = buf.readInt();
	    mpvalues = new double[l2][];
	    for(int j =0;j<l2;j++){
		    int l3 = buf.readInt();
		    double[] tdd = new double[l3];
		    for(int i =0;i<l3;i++)
		    	tdd[i] = buf.readDouble();
		    mpvalues[j] = tdd;
	    }
		
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

	    buf.writeInt(mpvalues.length);
	    for(int j =0;j<mpvalues.length;j++){
	    	buf.writeInt(mpvalues[j].length);
		    for(int i =0;i<mpvalues[j].length;i++)
		    	buf.writeDouble(mpvalues[j][i]);
	    }
		
	}
}
