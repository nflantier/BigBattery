package noelflantier.bigbattery.client.bases;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class GuiRender {

	public static void renderTask(int capacity, int amount, double x, double y, double zLevel, double width, double height, double xT, double yT){
		renderHorizontalRectangle(capacity, amount, x, y, zLevel, width, height, xT, yT);
	}
	
	public static void renderHorizontalRectangle(int capacity, int amount, double x, double y, double zLevel, double width, double height, double xT, double yT){
	    int renderAmount = (int) ((capacity-amount) * width / capacity);
	    int posX = (int) (x); 
	    
	    GL11.glEnable(GL11.GL_BLEND);
	    float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(posX + 0), (double)(y + height), (double)zLevel).tex((double)((xT + 0) * f), (double)((yT + height) * f1)).endVertex();
        vertexbuffer.pos((double)(posX + renderAmount), (double)(y + height), (double)zLevel).tex((double)((xT + renderAmount) * f), (double)((yT + height) * f1)).endVertex();
        vertexbuffer.pos((double)(posX + renderAmount), (double)(y + 0), (double)zLevel).tex((double)((xT + renderAmount) * f), (double)((yT + 0) * f1)).endVertex();
        vertexbuffer.pos((double)(posX + 0), (double)(y + 0), (double)zLevel).tex((double)((xT + 0) * f), (double)((yT + 0) * f1)).endVertex();
        tessellator.draw();
	    GL11.glDisable(GL11.GL_BLEND);
	}

	public static void renderEnergy(int capacity, int amount, double x, double y, double zLevel, double width, double height, double xT, double yT){
		renderVerticalRectangle(capacity, amount, x, y, zLevel, width, height, xT, yT);
	}
	
	public static void renderVerticalRectangle(int capacity, int amount, double x, double y, double zLevel, double width, double height, double xT, double yT){

	    int renderAmount = (int) Math.max(Math.min(height, amount * height / capacity), 0);
	    int posY = (int) (y + height - renderAmount); 
	    
	    GL11.glEnable(GL11.GL_BLEND);
	    float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(x + 0), (double)(posY + renderAmount), (double)zLevel).tex((double)((xT + 0) * f), (double)((yT + renderAmount) * f1)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(posY + renderAmount), (double)zLevel).tex((double)((xT + width) * f), (double)((yT + renderAmount) * f1)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(posY + 0), (double)zLevel).tex((double)((xT + width) * f), (double)((yT + 0) * f1)).endVertex();
        vertexbuffer.pos((double)(x + 0), (double)(posY + 0), (double)zLevel).tex((double)((xT + 0) * f), (double)((yT + 0) * f1)).endVertex();
        tessellator.draw();
	    GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void renderFluid(FluidTank tank, double x, double y, double zLevel, double width, double height) {
		renderFluidStack(tank.getFluid(), tank.getCapacity(), tank.getFluidAmount(), x, y, zLevel, width, height);
	}
	public static void renderFluidStack(FluidStack fluid, int capacity, int amount, double x, double y, double zLevel, double width, double height) {
	    if(fluid == null || fluid.getFluid() == null || fluid.amount <= 0) {
	      return;
	    }

	    TextureMap map = Minecraft.getMinecraft().getTextureMapBlocks();
		TextureAtlasSprite icon = map.getTextureExtry(fluid.getFluid().getStill().toString());
		if(icon == null){
			return;
		}

	    int renderAmount = (int) Math.max(Math.min(height, amount * height / capacity), 1);
	    int posY = (int) (y + height - renderAmount);

		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	    int color = fluid.getFluid().getColor(fluid);
	    GL11.glColor3ub((byte) (color >> 16 & 0xFF), (byte) (color >> 8 & 0xFF), (byte) (color & 0xFF));

	    GL11.glEnable(GL11.GL_BLEND);
	    for (int i = 0; i < width; i += 16) {
	      for (int j = 0; j < renderAmount; j += 16) {
	        int drawWidth = (int) Math.min(width - i, 16);
	        int drawHeight = Math.min(renderAmount - j, 16);

	        int drawX = (int) (x + i);
	        int drawY = posY + j;

	        double minU = icon.getMinU();
	        double maxU = icon.getMaxU();
	        double minV = icon.getMinV();
	        double maxV = icon.getMaxV();

	        Tessellator tessellator = Tessellator.getInstance();
	        VertexBuffer vertexbuffer = tessellator.getBuffer();
	        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
	        vertexbuffer.pos(drawX, drawY + drawHeight, 0).tex(minU, minV + (maxV - minV) * drawHeight / 16F).endVertex();
	        vertexbuffer.pos(drawX + drawWidth, drawY + drawHeight, 0).tex(minU + (maxU - minU) * drawWidth / 16F, minV + (maxV - minV) * drawHeight / 16F).endVertex();
	        vertexbuffer.pos(drawX + drawWidth, drawY,0).tex(minU + (maxU - minU) * drawWidth / 16F, minV).endVertex();
	        vertexbuffer.pos(drawX, drawY, 0).tex(minU, minV).endVertex();
	        tessellator.draw();
	      }
	    }
	    GL11.glDisable(GL11.GL_BLEND);
	  }
}
