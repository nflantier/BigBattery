package noelflantier.bigbattery.client.gui;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
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
		this.ySize = 171;
		this.tile = tile;
	}

	@Override
	public void updateToolTips(String key) {
		
	}
	
	@Override
	public void updateScreen(){
		super.updateScreen();
		componentList.get("cu").replaceString(1, I18n.format("gui.plug.cang")+" : "+((int)tile.currentRF<=-1?0:(int)tile.currentRF)+" "+I18n.format("gui.global.rf"));
		componentList.get("e1m").replaceString(0, I18n.format("gui.plug.amount")+" : "+tile.mbb.materialsBattery.electrode1MP.currentAmount).replaceString(1, I18n.format("gui.plug.max")+" : "+tile.mbb.materialsBattery.electrode1MP.maxAmount).replaceString(2, I18n.format("gui.plug.decay")+" : "+(int)tile.mbb.materialsBattery.electrode1MP.currentUnit);
		componentList.get("e2m").replaceString(0, I18n.format("gui.plug.amount")+" : "+tile.mbb.materialsBattery.electrode2MP.currentAmount).replaceString(1, I18n.format("gui.plug.max")+" : "+tile.mbb.materialsBattery.electrode2MP.maxAmount).replaceString(2, I18n.format("gui.plug.decay")+" : "+(int)tile.mbb.materialsBattery.electrode2MP.currentUnit);
		componentList.get("elm").replaceString(0, I18n.format("gui.plug.amount")+" : "+tile.mbb.materialsBattery.electrolyteMP.currentAmount).replaceString(1, I18n.format("gui.plug.max")+" : "+tile.mbb.materialsBattery.electrolyteMP.maxAmount).replaceString(2, I18n.format("gui.plug.decay")+" : "+(int)tile.mbb.materialsBattery.electrolyteMP.currentUnit);
	}
	
	@Override
	public void loadComponents(){
		super.loadComponents();
		this.componentList.put("mf", new GuiComponent(6, 5, 100, 10){{
			addText(I18n.format("gui.plug.tile"), 0, 0);
		}});
		
		Map<MaterialsHandler.Electrode.TYPE,Integer> mat = new HashMap<>();
		mat.put(MaterialsHandler.Electrode.TYPE.ANODE, 8);
		mat.put(MaterialsHandler.Electrode.TYPE.CATHODE, 2);
		
		if(tile.getStructure()!=null){
			if(tile.getStructure().isStructured){

				this.componentList.put("cu", new GuiComponent(32,20){{
					addText(I18n.format("gui.plug.size")+" : "+tile.getStructure().getStringSize(), 0, 0);
					addText(I18n.format("gui.plug.cang")+" : "+((int)tile.currentRF<=-1?0:(int)tile.currentRF)+" "+I18n.format("gui.global.rf"), 0, 0);
				}});
				
				this.componentList.put("e1", new GuiComponent(10,48){{
					addText(I18n.format("gui.plug.electrode1"), 17, 0);
					if(tile.mbb.materialsBattery.electrode1 != null){
						if(tile.mbb.materialsBattery.electrode1.type != MaterialsHandler.Electrode.TYPE.NONE)
							addText(""+tile.mbb.materialsBattery.electrode1.type.name(), mat.get(tile.mbb.materialsBattery.electrode1.type), 25);
						this.addItemStack(new GuiItemStack(25,62,tile.mbb.materialsBattery.electrode1.stackReference));
					}else
						addText(I18n.format("gui.plug.empty"), 10, 10);
					
				}});
				this.componentList.put("e1m", new GuiComponent(10,95){{
					this.globalScale = 0.6F;
					addText(I18n.format("gui.plug.amount")+" : "+tile.mbb.materialsBattery.electrode1MP.currentAmount, 0, 0);
					addText(I18n.format("gui.plug.max")+" : "+tile.mbb.materialsBattery.electrode1MP.maxAmount, 0, 0);
					addText(I18n.format("gui.plug.decay")+" : "+(int)tile.mbb.materialsBattery.electrode1MP.currentUnit, 0, 0);
				}});
				
				this.componentList.put("el", new GuiComponent(65,48){{
					addText(I18n.format("gui.plug.electrolyte"), 16, 0);
					if(tile.mbb.materialsBattery.electrolyte != null)
						this.addItemStack(new GuiItemStack(80,62,tile.mbb.materialsBattery.electrolyte.stackReference));
					else
						addText(I18n.format("gui.plug.empty"), 10, 10);
				}});
				this.componentList.put("elm", new GuiComponent(65,95){{
					this.globalScale = 0.6F;
					addText(I18n.format("gui.plug.amount")+" : "+tile.mbb.materialsBattery.electrolyteMP.currentAmount, 0, 0);
					addText(I18n.format("gui.plug.max")+" : "+tile.mbb.materialsBattery.electrolyteMP.maxAmount, 0, 0);
					addText(I18n.format("gui.plug.decay")+" : "+(int)tile.mbb.materialsBattery.electrolyteMP.currentUnit, 0, 0);
				}});
				
				this.componentList.put("e2", new GuiComponent(120,48){{
					addText(I18n.format("gui.plug.electrode2"), 17, 0);
					if(tile.mbb.materialsBattery.electrode2 != null){
						if(tile.mbb.materialsBattery.electrode2.type != MaterialsHandler.Electrode.TYPE.NONE)
							addText(""+tile.mbb.materialsBattery.electrode2.type.name(), mat.get(tile.mbb.materialsBattery.electrode2.type), 25);
						this.addItemStack(new GuiItemStack(135,62,tile.mbb.materialsBattery.electrode2.stackReference));
					}else
						addText(I18n.format("gui.plug.empty"), 10, 10);
				}});
				this.componentList.put("e2m", new GuiComponent(120,95){{
					this.globalScale = 0.6F;
					addText(I18n.format("gui.plug.amount")+" : "+tile.mbb.materialsBattery.electrode2MP.currentAmount, 0, 0);
					addText(I18n.format("gui.plug.max")+" : "+tile.mbb.materialsBattery.electrode2MP.maxAmount, 0, 0);
					addText(I18n.format("gui.plug.decay")+" : "+(int)tile.mbb.materialsBattery.electrode2MP.currentUnit, 0, 0);
				}});
				
				this.componentList.put("c1", new GuiComponent(10,145){{
					addText(I18n.format("gui.plug.conductives")+" : ", 0, 0);

					if(tile.mbb.materialsBattery.electrode1Cond != null){
						this.addItemStack(new GuiItemStack(78,142,tile.mbb.materialsBattery.electrode1Cond.stackReference));
					}
					if(tile.mbb.materialsBattery.electrode2Cond != null){
						this.addItemStack(new GuiItemStack(94,142,tile.mbb.materialsBattery.electrode2Cond.stackReference));
					}
					addText(I18n.format("gui.plug.ratio")+" : *"+((tile.mbb.materialsBattery.electrode2Cond.ratioEfficiency + tile.mbb.materialsBattery.electrode1Cond.ratioEfficiency) /2), 102, -10);
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
