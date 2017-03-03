package noelflantier.bigbattery.client.bases;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class GuiNFTextField extends GuiTextField{

	public boolean isOnlyNumeric = false;
	public boolean isOnlyLetter = false;
	
	public GuiNFTextField(int id, FontRenderer p_i1032_1_, int p_i1032_2_, int p_i1032_3_, int p_i1032_4_, int p_i1032_5_) {
		super(id,p_i1032_1_, p_i1032_2_, p_i1032_3_, p_i1032_4_, p_i1032_5_);
	}

}
