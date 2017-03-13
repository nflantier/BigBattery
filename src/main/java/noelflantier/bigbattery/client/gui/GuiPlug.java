package noelflantier.bigbattery.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.client.bases.GuiButtonImage;
import noelflantier.bigbattery.client.bases.GuiComponent;
import noelflantier.bigbattery.client.bases.GuiImage;
import noelflantier.bigbattery.client.bases.GuiNF;
import noelflantier.bigbattery.common.container.ContainerPlug;
import noelflantier.bigbattery.common.tiles.TilePlug;

public class GuiPlug extends GuiNF{

	private static final ResourceLocation bground = new ResourceLocation(Ressources.MODID+":textures/gui/gui_plug.png");
	
	TilePlug tile;
	public GuiPlug(InventoryPlayer inventory, TilePlug tile) {
		super(new ContainerPlug(inventory, tile));
		this.xSize = 176;
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
		if(tile.getStructure()!=null){
			if(tile.getStructure().isStructured){
				this.componentList.put("cu", new GuiComponent(6,25){{
					addText("Can generate : "+((int)tile.getStructure().materialsBattery.generateEnergy())+" RF", 0, 0);
					addText("Currently : "+(tile.lastEnergyStoredAmount-tile.energyStorage.getEnergyStored())+" RF/T", 0, 0);
					addText("Size : "+tile.getStructure().getStringSize(), 0, 0);
				}});
			}else{
				
			}
		}
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int x, int y) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
	}
}
