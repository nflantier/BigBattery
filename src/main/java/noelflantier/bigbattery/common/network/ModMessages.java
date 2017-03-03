package noelflantier.bigbattery.common.network;

import net.minecraftforge.fml.relauncher.Side;
import noelflantier.bigbattery.common.network.messages.PacketPlug;

public class ModMessages {

	public static void preInitCommon() {
	    //PacketHandler.INSTANCE.registerMessage(PacketInductorGui.class, PacketInductorGui.class, PacketHandler.nextId(), Side.SERVER);
		PacketHandler.INSTANCE.registerMessage(PacketPlug.class, PacketPlug.class, PacketHandler.nextId(), Side.CLIENT);
	}
}
