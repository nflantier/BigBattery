package noelflantier.bigbattery.client.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.client.bases.GuiComponent;
import noelflantier.bigbattery.client.bases.GuiNF;
import noelflantier.bigbattery.client.bases.GuiRender;
import noelflantier.bigbattery.client.bases.GuiToolTips;
import noelflantier.bigbattery.common.container.ContainerInterface;
import noelflantier.bigbattery.common.handlers.ModProperties;
import noelflantier.bigbattery.common.tiles.TileInterface;

public class GuiInterface extends GuiNF{

	private static final ResourceLocation bground = new ResourceLocation(Ressources.MODID+":textures/gui/gui_interface.png");
	private static final ResourceLocation bground2 = new ResourceLocation(Ressources.MODID+":textures/gui/gui_interface_2.png");
	
	TileInterface tile;
	public GuiInterface(InventoryPlayer inventory, TileInterface tile) {
		super(new ContainerInterface(inventory, tile));
		this.xSize = 176;
		this.ySize = 170;
		this.tile = tile;
	}

	@Override
	public void updateToolTips(String key) {
		switch(key){
			case "tank" :
				((GuiToolTips)this.componentList.get("tank")).content =  new ArrayList<String>();
				((GuiToolTips)this.componentList.get("tank")).addContent(this.fontRendererObj, String.format("%,d", tile.tank.getFluidAmount())+" MB");
				((GuiToolTips)this.componentList.get("tank")).addContent(this.fontRendererObj, "/ "+String.format("%,d", this.tile.tank.getCapacity())+" MB");
				break;
			default:
				break;
		}
	}
	
	
	
	@Override
	public void loadComponents(){
		super.loadComponents();
		this.componentList.put("tank", 
				new GuiToolTips(guiLeft+153, guiTop+55, 14, 23, this.width)
				);
		this.componentList.put("mf", new GuiComponent(6, 5, 100, 10){{
			addText("Interface "+tile.inventory.type.name+" :", 0, 0);
		}});
		this.componentList.put("in", new GuiComponent(6, 79, 100, 10){{
			addText("Inventory :", 0, 0);
		}});
		this.componentList.put("re", new GuiComponent(28, 59, 100, 10){{
			addText("Only accept", 0, 0);
		}});
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int x, int y) {
		if(tile.inventory.type == ModProperties.InterfaceType.ELECTRODE){
			Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		}else{
			GuiRender.renderFluid(tile.tank, guiLeft+154, guiTop+56, 1, 14, 23);
			Minecraft.getMinecraft().getTextureManager().bindTexture(bground2);
		}
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
	}
}
