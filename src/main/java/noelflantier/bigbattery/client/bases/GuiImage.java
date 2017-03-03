package noelflantier.bigbattery.client.bases;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class GuiImage extends GuiComponentBase{

	public ResourceLocation rl;
	public boolean useUV = false;
	public float minu = 0;
	public float minv = 0;
	public float maxu = 1;
	public float maxv = 1;
	public float alpha = 1;
	
	public GuiImage(int x, int y, ResourceLocation rl){
		super(x, y);
		this.rl = rl;
	}
	
	public GuiImage(int x, int y, int w, int h, ResourceLocation rl){
		super(x,y,w,h);
		this.rl = rl;
	}

	public GuiImage(int x, int y, int w, int h, float minu, float minv, float maxu, float maxv, ResourceLocation rl){
		super(x,y,w,h);
		this.rl = rl;
		this.maxu = maxu;
		this.maxv  = maxv;
		this.minu = minu;
		this.minv  = minv;
	}
	
	public void draw(int x, int y){
		renderImage(x,y,minu,minv,maxu,maxv);
	}
	
	public void renderImage(int x, int y) {
		Minecraft.getMinecraft().renderEngine.bindTexture(this.rl);
		GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1f, 1f, 1f, alpha);
	        float f = 0.0625F;
	        float f1 = 0.0625F;
	        Tessellator tessellator = Tessellator.getInstance();
	        VertexBuffer vertexbuffer = tessellator.getBuffer();
	        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
	        vertexbuffer.pos((double)(this.x + 0), (double)(this.y + this.height), (double)1).tex(0,1).endVertex();
	        vertexbuffer.pos((double)(this.x + this.width), (double)(this.y + this.height), (double)1).tex(1,1).endVertex();
	        vertexbuffer.pos((double)(this.x + this.width), (double)(this.y + 0), (double)1).tex(1,0).endVertex();
	        vertexbuffer.pos((double)(this.x + 0), (double)(this.y + 0), (double)1).tex(0,0).endVertex();
	        /*tessellator.addVertexWithUV((double)(this.x + 0), (double)(this.y + this.height), (double)1, (double)((float)0), (double)((float)1));
	        tessellator.addVertexWithUV((double)(this.x + this.width), (double)(this.y + this.height), (double)1, (double)((float)1), (double)((float)1));
	        tessellator.addVertexWithUV((double)(this.x + this.width), (double)(this.y + 0), (double)1, (double)((float)1), (double)((float)0));
	        tessellator.addVertexWithUV((double)(this.x + 0), (double)(this.y + 0), (double)1, (double)((float)0), (double)((float)0));*/
	        tessellator.draw();
			GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}	
	
	public void renderImage(int x, int y, float minu, float minv, float maxu, float maxv) {
		Minecraft.getMinecraft().renderEngine.bindTexture(this.rl);
		GL11.glPushMatrix();
			//GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(1f, 1f, 1f, alpha);
	        float f = 0.015625F;
	        Tessellator tessellator = Tessellator.getInstance();
	        VertexBuffer vertexbuffer = tessellator.getBuffer();
	        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
	        vertexbuffer.pos((double)(this.x + 0), (double)(this.y + this.height), (double)1).tex((double)minu,(double)maxv).endVertex();
	        vertexbuffer.pos((double)(this.x + this.width), (double)(this.y + this.height), (double)1).tex((double)maxu,(double)maxv).endVertex();
	        vertexbuffer.pos((double)(this.x + this.width), (double)(this.y + 0), (double)1).tex((double)maxu,(double)minv).endVertex();
	        vertexbuffer.pos((double)(this.x + 0), (double)(this.y + 0), (double)1).tex((double)minu,(double)minv).endVertex();
	        /*tessellator.addVertexWithUV((double)(this.x + 0), (double)(this.y + this.height), (double)1, (double)((float)minu), (double)((float)maxv));
	        tessellator.addVertexWithUV((double)(this.x + this.width), (double)(this.y + this.height), (double)1, (double)((float)maxu), (double)((float)maxv));
	        tessellator.addVertexWithUV((double)(this.x + this.width), (double)(this.y + 0), (double)1, (double)((float)maxu), (double)((float)minv));
	        tessellator.addVertexWithUV((double)(this.x + 0), (double)(this.y + 0), (double)1, (double)((float)minu), (double)((float)minv));*/
	        tessellator.draw();
			GL11.glDisable(GL11.GL_BLEND);
			//GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	@Override
	public boolean isMouseHover(int mx, int my){
		return mx<=this.x+this.width && mx>=this.x && my<this.y-this.height && my>this.y;
	}
}
