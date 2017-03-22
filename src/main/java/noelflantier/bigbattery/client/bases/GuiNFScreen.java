package noelflantier.bigbattery.client.bases;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class GuiNFScreen extends GuiScreen{

	public boolean componentloaded = false;
	public Hashtable<String, GuiComponent> fullComponentList = new Hashtable<String, GuiComponent>();
	public int guiLeft;
	public int guiTop;
	public int xSize = 150;
	public int ySize = 150;
	
	public GuiNFScreen(){
		super();
	}
	
	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException{
        super.keyTyped(p_73869_1_, p_73869_2_);
        if (p_73869_2_ == 1 || p_73869_2_ == this.mc.gameSettings.keyBindInventory.getKeyCode())
        {
            this.mc.player.closeScreen();
        }
    }

	@Override
	public void drawScreen(int x, int y, float f)
    {
		super.drawScreen(x, y, f);
		Enumeration<String> enumKey = this.fullComponentList.keys();
		while (enumKey.hasMoreElements()) {
		    String key = enumKey.nextElement();
		    this.fullComponentList.get(key).draw(x, y);
		}
    	drawItemStackHovered(x,y);
    }
	
	public void drawItemStackHovered(int x, int y){
		Optional<Entry<String, GuiComponent>> result = fullComponentList.entrySet().stream().filter((e)->e.getValue().getItemStackHovered(x, y).isPresent()).findFirst();
		if( result.isPresent() ){
			Optional<ItemStack> io = result.get().getValue().getItemStackHovered(x, y);
			if( io.isPresent() )
				renderToolTip(io.get(), x, y);
		}
	}
	
	public void loadComponents(){
		if(this.componentloaded)return;
		this.componentloaded = true;
	}
	
	@Override
	public void initGui() {
		super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
		this.loadComponents();
		this.buttonList.clear();
		Enumeration<String> enumKey = this.fullComponentList.keys();
		while (enumKey.hasMoreElements()) {
		    String key = enumKey.nextElement();
		    this.fullComponentList.get(key).init(key);
		    for (GuiButton gb : this.fullComponentList.get(key).buttonList){
		    	gb.visible = true;
    			this.buttonList.add(gb);
    		}
		}
	}	

	
	protected void renderToolTip(ItemStack p_146285_1_, int p_146285_2_, int p_146285_3_){
		if (p_146285_1_==null)
			return;
		List list = p_146285_1_.getTooltip(Minecraft.getMinecraft().player, Minecraft.getMinecraft().gameSettings.advancedItemTooltips);

        for (int k = 0; k < list.size(); ++k)
        {
            if (k == 0)
            {
                list.set(k, p_146285_1_.getRarity().rarityColor + (String)list.get(k));
            }
            else
            {
                list.set(k, TextFormatting.GRAY + (String)list.get(k));
            }
        }

        FontRenderer font = p_146285_1_.getItem().getFontRenderer(p_146285_1_); 
        GL11.glPushMatrix();
        	drawHoveringText(list, p_146285_2_, p_146285_3_, (font == null ? this.fontRendererObj : font));
        	GL11.glDisable(GL11.GL_LIGHTING);
	    GL11.glPopMatrix();
    }
	
	public void drawImageButtons(int x, int y){
		for (int k = 0; k < this.buttonList.size(); ++k){
			if(this.buttonList.get(k) instanceof GuiButtonImage){
				((GuiButtonImage)this.buttonList.get(k)).drawImage();
			}
		}
	}
}
