package noelflantier.bigbattery.common.network.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import noelflantier.bigbattery.BigBattery;
import noelflantier.bigbattery.common.tiles.TileInterface;
import noelflantier.bigbattery.common.tiles.TilePlug;

public class PacketInterface  implements IMessage, IMessageHandler<PacketInterface, IMessage> {
	
	public int x;
	public int y;
	public int z;
	public int fluidamount;
	public String fluidname;
	
	public PacketInterface(){
	
	}

	public PacketInterface(BlockPos pos){
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}
	
	public PacketInterface(BlockPos pos, int fluidamount){
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.fluidamount = fluidamount;
		this.fluidname = null;
	}
	
	public PacketInterface(BlockPos pos, int fluidamount, String fluidname){
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.fluidamount = fluidamount;
		this.fluidname = fluidname;
	}
	
	
	@Override
	public IMessage onMessage(PacketInterface message, MessageContext ctx) {
		if (ctx.side.isClient()) {
			BigBattery.myProxy.getThreadFromContext(ctx).addScheduledTask(new Runnable(){
				@Override
				public void run() {
					TileEntity te = Minecraft.getMinecraft().player.getEntityWorld().getTileEntity(new BlockPos(message.x,message.y, message.z));
					if(te!=null && te instanceof TileInterface) {
						if(message.fluidname != null)
							((TileInterface)te).tank.setFluid(FluidRegistry.getFluidStack(message.fluidname, message.fluidamount));
						else
							((TileInterface)te).tank.setAmount(message.fluidamount);
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
	    fluidamount = buf.readInt();
	    int size = buf.readInt();
	    if(size > 0){
		    char tc[] = new char[size];
		    for(int j =0;j<size;j++)
		    	tc[j] = buf.readChar();
		    fluidname = new String(tc);
	    }
		
	}
	@Override
	public void toBytes(ByteBuf buf) {
	    buf.writeInt(x);
	    buf.writeInt(y);
	    buf.writeInt(z);
	    buf.writeInt(fluidamount);
	    if(fluidname==null){
		    buf.writeInt(-1);
	    }else{
		    char tc[] = fluidname.toCharArray();
		    buf.writeInt(tc.length);
		    for(int j =0;j<tc.length;j++)
		    	buf.writeChar(tc[j]);
	    }
		
	}
}
