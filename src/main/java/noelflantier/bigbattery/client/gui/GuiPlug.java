package noelflantier.bigbattery.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.client.bases.GuiButtonImage;
import noelflantier.bigbattery.client.bases.GuiComponent;
import noelflantier.bigbattery.client.bases.GuiImage;
import noelflantier.bigbattery.client.bases.GuiNF;
import noelflantier.bigbattery.common.container.ContainerPlug;
import noelflantier.bigbattery.common.tiles.TilePlug;

public class GuiPlug extends GuiNF{

	private static final ResourceLocation guiselements = new ResourceLocation(Ressources.MODID+":textures/gui/gui_elements.png");
	private static final ResourceLocation bground = new ResourceLocation(Ressources.MODID+":textures/gui/gui_plug.png");
	
	TilePlug tile;
	public GuiPlug(InventoryPlayer inventory, TilePlug tile) {
		super(new ContainerPlug(inventory, tile));
		this.xSize = 197;
		this.ySize = 135;
		this.tile = tile;
	}

	@Override
	public void updateToolTips(String key) {
		
	}
	@Override
	public void loadComponents(){
		super.loadComponents();
		this.componentList.put("mf", new GuiComponent(6, 5, 100, 10){{
			addText("Plug :", 0, 0);
		}});
		
		this.componentList.put("btws", new GuiComponent(0,0){{
			addImageButton(new GuiButtonImage(0,guiLeft+50,guiTop+35, 22, 20, new GuiImage(45, 29, 32,32 , 0F, 0F, 1F, 1F,guiselements)), 0.5F,0.25F,4,4, true);
		}});
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int x, int y) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
	}
}
