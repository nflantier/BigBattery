package noelflantier.bigbattery.client.gui.manual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.client.bases.GuiComponent;
import noelflantier.bigbattery.client.bases.GuiNFScreen;

public class NGuiManual extends GuiNFScreen{

	public ResourceLocation bgroundL = new ResourceLocation(Ressources.MODID+":textures/gui/gui_manuals_left.png");
	public ResourceLocation bgroundR = new ResourceLocation(Ressources.MODID+":textures/gui/gui_manuals_right.png");
	public static final int heightB = 190;
	public static final int widthB = 180;
	private static final ResourceLocation guiselements = new ResourceLocation(Ressources.MODID+":textures/gui/gui_elements.png");
	public Hashtable<String,ABaseCategory> listLink = new Hashtable<String,ABaseCategory>();
	public static final String CAT_INDEX = "INDEX";
	public static final String CAT_PREVIOUS = "PREVIOUS";
	public List<String> history = new ArrayList<String>();
	public int maxHistorySize = 10;
	public static int maxStringWidth = 100;
	public static ABaseCategory index;
	public String currentKey = "";
	public ABaseCategory currentCategory;
	
	public NGuiManual(){
		super();
		this.xSize = 300;
		this.ySize = 190;
		maxStringWidth = xSize - 15;
	}
	public NGuiManual(EntityPlayer player){
		this();
	}
	public void loadComponents(){
		if(this.componentloaded)return;
		super.loadComponents();
		index = new NIndexManual(CAT_INDEX,this.guiLeft, this.guiTop);
		listLink.put(CAT_INDEX, index);
		initLink(index);
		currentKey = CAT_INDEX;
		setCategory(false);
	}
	
	public void initLink(ABaseCategory c){
		if(c.listCategory.isEmpty())
			return;
		c.addLink(listLink);
		for (Map.Entry<String,ABaseCategory> entry : c.listCategory.entrySet()){
			initLink(entry.getValue());
		}
	}
	
	public void addMenu(){
		this.fullComponentList.put(CAT_INDEX, new GuiComponent(guiLeft+xSize/2-15, guiTop+10, 30, 10){{
			addText("HOME", 0, 0);
			isLink = true;
		}});
		if(this.history.size()>1){
			this.fullComponentList.put(CAT_PREVIOUS, new GuiComponent(guiLeft+10, guiTop+10, 50, 10){{
				addText("previous", 0, 0);
				isLink = true;
			}});
			if(this.listLink.containsKey(CAT_PREVIOUS))
				this.listLink.remove(CAT_PREVIOUS);
			if(history.get(history.size()-2)!=null && listLink.get(history.get(history.size()-2))!=null){
				this.listLink.put(CAT_PREVIOUS, listLink.get(history.get(history.size()-2)));
			}
		}
	}
	
	public void setCategory(boolean init){
		if(!listLink.containsKey(currentKey))
			return;
		if(CAT_PREVIOUS.equals(currentKey)){
			int r = this.history.size()-1;
			this.history.remove(r);
		}else
			addHistory(currentKey);
		currentCategory = listLink.get(currentKey);
		this.fullComponentList.clear();
		this.fullComponentList.putAll(currentCategory.getComponents());
		addMenu();
		if(init)
			this.initGui();
	}
	
	public void addHistory(String key){
		if(this.history.size()>=this.maxHistorySize)
			this.history.remove(0);
		this.history.add(this.history.size(),key);
	} 
	
	@Override
    protected void actionPerformed(GuiButton button) {
		this.fullComponentList.forEach((s,g)->g.handleButton(button));
    }
    
	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		super.mouseClicked(x, y, button);
		Enumeration<String> enumKey = this.fullComponentList.keys();
		while (enumKey.hasMoreElements()) {
		    String key = enumKey.nextElement();
		    Optional<ItemStack> st = this.fullComponentList.get(key).getItemStackHovered(x, y);
		    if(st.isPresent() && listLink.containsKey(NBlocksAndItems.PRE_IB+st.get().getUnlocalizedName())){
		    	currentKey = NBlocksAndItems.PRE_IB+st.get().getUnlocalizedName();
		    	setCategory(true);
	    		break;
		    }
		    if(this.fullComponentList.get(key).clicked(x, y)){
		    	currentKey = key;
		    	setCategory(true);
	    		break;
		    }
		}
	}
	
	@Override
	public boolean doesGuiPauseGame(){
        return false;
    }
	
	@Override
    public void drawScreen(int x, int y, float f){
		Minecraft.getMinecraft().getTextureManager().bindTexture(bgroundL);
		GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize/2, this.ySize-4);
			this.drawTexturedModalRect(guiLeft, guiTop+this.ySize-4, 0, heightB-4, xSize/2, 4);
			GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(bgroundR);
		GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			this.drawTexturedModalRect(guiLeft+xSize/2, guiTop, widthB-xSize/2, 0, xSize/2, this.ySize-4);
			this.drawTexturedModalRect(guiLeft+xSize/2, guiTop+this.ySize-4, widthB-xSize/2, heightB-4, xSize/2, 4);
			GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		 
    	super.drawScreen(x, y, f);
    	
    	drawImageButtons(x,y);
    }
}
