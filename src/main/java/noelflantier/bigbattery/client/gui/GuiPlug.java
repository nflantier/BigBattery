package noelflantier.bigbattery.client.gui;

import java.util.Enumeration;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.client.bases.GuiComponent;
import noelflantier.bigbattery.client.bases.GuiItemStack;
import noelflantier.bigbattery.client.bases.GuiNF;
import noelflantier.bigbattery.common.container.ContainerPlug;
import noelflantier.bigbattery.common.materials.MaterialsHandler;
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
	public void updateScreen(){
		super.updateScreen();
		//this.componentList.get("fluiddraining").replaceString(0, "Fluid draining : "+this.pillar.amountToExtract+" MB/T");
	}
	
	@Override
	public void loadComponents(){
		super.loadComponents();
		this.componentList.put("mf", new GuiComponent(6, 5, 100, 10){{
			addText("Plug : "+tile.getStructure().getStringSize(), 0, 0);
		}});
		if(tile.getStructure()!=null){
			if(tile.getStructure().isStructured){

				this.componentList.put("cu", new GuiComponent(6,15){{
					addText("Can generate : "+((int)tile.currentRF<=-1?0:(int)tile.currentRF)+" RF", 0, 0);
				}});
				
				this.componentList.put("e1", new GuiComponent(6,35){{
					addText("Electrode 1 : ", 0, 0);
					if(tile.mbb.materialsBattery.electrode1 != null){
						if(tile.mbb.materialsBattery.electrode1.type != MaterialsHandler.Electrode.TYPE.NONE)
							addText(""+tile.mbb.materialsBattery.electrode1.type.name(), 90, -10);
						this.addItemStack(new GuiItemStack(75,30,tile.mbb.materialsBattery.electrode1.stackReference));
					}else
						addText("empty", 75, -10);
					
				}});
				
				this.componentList.put("e2", new GuiComponent(6,55){{
					addText("Electrode 2 : ", 0, 0);
					if(tile.mbb.materialsBattery.electrode2 != null){
						if(tile.mbb.materialsBattery.electrode2.type != MaterialsHandler.Electrode.TYPE.NONE)
							addText(""+tile.mbb.materialsBattery.electrode2.type.name(), 90, -10);
						this.addItemStack(new GuiItemStack(75,50,tile.mbb.materialsBattery.electrode2.stackReference));
					}else
						addText("empty", 75, -10);
				}});
				
				this.componentList.put("el", new GuiComponent(6,75){{
					addText("Electrolyte : ", 0, 0);
					if(tile.mbb.materialsBattery.electrolyte != null)
						this.addItemStack(new GuiItemStack(72,70,tile.mbb.materialsBattery.electrolyte.stackReference));
					else
						addText("empty", 72, -10);
				}});
				
				this.componentList.put("c1", new GuiComponent(6,95){{
					addText("Conductive 1 : ", 0, 0);
					if(tile.mbb.materialsBattery.electrode1Cond != null)
						this.addItemStack(new GuiItemStack(80,90,tile.mbb.materialsBattery.electrode1Cond.stackReference));
					else
						addText("empty", 80, -10);
				}});
				
				this.componentList.put("c2", new GuiComponent(6,115){{
					addText("Conductive 2 : ", 0, 0);
					if(tile.mbb.materialsBattery.electrode2Cond != null)
						this.addItemStack(new GuiItemStack(80,110,tile.mbb.materialsBattery.electrode2Cond.stackReference));
					else
						addText("empty", 80, -10);
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
