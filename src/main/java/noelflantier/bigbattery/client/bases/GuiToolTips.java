package noelflantier.bigbattery.client.bases;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import noelflantier.bigbattery.Ressources;

public class GuiToolTips extends GuiComponent{

	public List<String> content =  new ArrayList<String>();
	public int widthContent = 0;
	public int heightContent = 0;
	public int sceneWidth = 0;
	private static final ResourceLocation bgroundToolTips = new ResourceLocation(Ressources.MODID+":textures/gui/guiDefaultBackground.png");
	
	public GuiToolTips(int x, int y) {
		super(x, y);
	}

	public GuiToolTips(int x, int y, int w, int h, int sw){
		super(x, y, w, h);
		this.sceneWidth = sw;
	}
	
	public void addContent(FontRenderer fr, String c){
		this.content.add(c);
		if(this.widthContent<fr.getStringWidth(c))
			this.widthContent = fr.getStringWidth(c);
		this.heightContent = this.content.size()*10;
	}

	@Override
	public void draw(int x, int y){
		super.draw(x, y);
	}
	
    public void drawTexturedModalRect(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_, float zLevel)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(p_73729_1_ + 0), (double)(p_73729_2_ + p_73729_6_), (double)zLevel).tex((double)((p_73729_3_ + 0) * f), (double)((p_73729_4_ + p_73729_6_) * f1)).endVertex();
        vertexbuffer.pos((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + p_73729_6_), (double)zLevel).tex((double)((p_73729_3_ + p_73729_5_) * f), (double)((p_73729_4_ + p_73729_6_) * f1)).endVertex();
        vertexbuffer.pos((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + 0), (double)zLevel).tex((double)((p_73729_3_ + p_73729_5_) * f), (double)((p_73729_4_ + 0) * f1)).endVertex();
        vertexbuffer.pos((double)(p_73729_1_ + 0), (double)(p_73729_2_ + 0), (double)zLevel).tex((double)((p_73729_3_ + 0) * f), (double)((p_73729_4_ + 0) * f1)).endVertex();
        tessellator.draw();
    }
    
    public void showToolTips(int x, int y){
		if(this.isMouseHover(x,y)){
			//VANILLA ITEM TOOLTIP RENDER AT 300.0F
			this.showContent(this.FR, x, y, 0, 0, 301.0F);
			this.content.clear();
		}
    }
    
	public void showContent(FontRenderer fr, int xp, int yp, int guileft, int guitop, float zlevel){

		int decy = 0;
		int decx = 0;
		int fx = xp - guileft + 10;
		int fy = yp - guitop;
		if(xp>this.sceneWidth/2)
			decx = this.widthContent*-1-10;
		int marge = 5;
		
		GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		Minecraft.getMinecraft().getTextureManager().bindTexture(bgroundToolTips);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		drawTexturedModalRect(fx-marge+decx, fy-marge,0 , 0, this.widthContent-10,this.heightContent-10, zlevel);
		drawTexturedModalRect(fx-marge+decx+this.widthContent-10, fy-marge,220-10-marge*2 , 0, 10+marge*2,this.heightContent-10, zlevel);
		drawTexturedModalRect(fx-marge+decx, fy-marge+this.heightContent-10,0 , 100-10-marge*2, this.widthContent-10,10+marge*2, zlevel);
		drawTexturedModalRect(fx-marge+decx+this.widthContent-10, fy-marge+this.heightContent-10,220-10-marge*2 , 100-10-marge*2, 10+marge*2,10+marge*2, zlevel);
		GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
		

        GL11.glDisable(GL11.GL_DEPTH_TEST);
		for(String ct : this.content){
			fr.drawString(String.format("%s%s%s", this.color, ct, TextFormatting.RESET), fx+decx, fy+decy, 4210752);
			decy += 10;
		}
        GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
	}
}
