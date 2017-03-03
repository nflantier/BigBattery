package noelflantier.bigbattery.client.bases;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class GuiItemStack extends GuiComponentBase{

	public ItemStack stack = null;
	public boolean showSize = false;
	public int idRecipe = 0;
	
	public GuiItemStack(int x, int y) {
		super(x, y, 16, 16);
	}	
	
	public GuiItemStack(int x, int y, ItemStack stack) {
		this(x, y);
		this.stack = stack;
	}
	public GuiItemStack(ItemStack stack) {
		this(0, 0, stack);
	}
	public GuiItemStack(int x, int y, ItemStack stack, int id) {
		this(x, y, stack);
		this.idRecipe = id;
	}
	
	public GuiItemStack(int x, int y, int w, int h, ItemStack stack) {
		super(x, y, w, h);
		this.stack = stack;
	}
	
	public void draw(int x, int y) {
		if(stack==null)
			return;
        GL11.glPushMatrix();
        
	        RenderHelper.enableGUIStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stack, this.x, this.y);
            if(showSize || stack.getCount()>1)
            	Minecraft.getMinecraft().getRenderItem().renderItemOverlayIntoGUI(FR, stack, this.x, this.y, null);

            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            GlStateManager.enableBlend();
            //GlStateManager.enableLighting();
            //GlStateManager.enableDepth();
            //RenderHelper.enableStandardItemLighting();
        	//RenderHelper.enableStandardItemLighting();
        	//Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stack, this.x, this.y);
	        //if(showSize || stack.stackSize>1)
	        	//Minecraft.getMinecraft().getRenderItem().renderItemOverlayIntoGUI(FR, stack, this.x, this.y, "");
	        	//Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(stack, this.x, this.y);
	        //GL11.glDisable(GL11.GL_LIGHTING);
            //RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
	}

	@Override
	public boolean isMouseHover(int mx, int my){
    	return mx<=this.x+this.width && mx>=this.x && my<this.y+this.height && my>this.y;
	}

}
