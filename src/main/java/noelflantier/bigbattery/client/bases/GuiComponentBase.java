package noelflantier.bigbattery.client.bases;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import noelflantier.bigbattery.Ressources;

public abstract class GuiComponentBase {

	public int x;
	public int y;
	public int width;
	public int height;
	public static Gui GUIH = new Gui();
    public static FontRenderer FR = Minecraft.getMinecraft().fontRendererObj;
	public static final ResourceLocation guiselements = new ResourceLocation(Ressources.MODID+":textures/gui/gui_elements.png");
	public static GuiImage horizontal = new GuiImage(0, 0, 32,2 , 0F, 0.75F, 0.25F, 0.75F+2/128F, guiselements);
	public static GuiImage vertical = new GuiImage(0, 0, 2,32 , 0F, 0.75F, 2/128F, 1F, guiselements);
	public static GuiImage arrowH = new GuiImage(0, 0, 32,32 , 0.25F, 0.75F, 0.5F, 1F, guiselements);
	
	public GuiComponentBase(int x, int y){
		this.x = x;
		this.y = y;
	}
	public GuiComponentBase(int x, int y, int w, int  h){
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	public abstract void draw(int x, int y);
	public abstract boolean isMouseHover(int mx, int my);
}
