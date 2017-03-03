package noelflantier.bigbattery.client.bases;

import net.minecraft.client.gui.GuiButton;

public class GuiButtonImage extends GuiButton{
	public GuiImage image;
	public boolean oneState = false;
	public boolean enable = true;
	public float baseU = 0F;
	public float baseV = 0F;
	public int elemPWidth = 2;
	public int elemPHeight = 2;
	public int decX = 4;
	public int decY = 4;
	
    public GuiButtonImage(int id, int x, int y, String displayString){
        this(id, x, y, 200, 20, displayString);
    }
	public GuiButtonImage(int id, int x, int y, int width, int height, String displayString) {
		super(id, x, y, width, height, displayString);
	}
	public GuiButtonImage(int id, int x, int y, int width, int height, GuiImage im) {
		super(id, x, y, width, height, "");
		this.image = im;
	}
	public GuiButtonImage(int id, int x, int y, int width, int height, GuiImage im, boolean oneState) {
		this(id, x, y, width, height, im);
		this.oneState = oneState;
	}
	
	public void drawImage(){
		
		if(enable || oneState){
			image.minu=baseU+0F;
			image.minv=baseV+0F;
			image.maxu=baseU+1/(float)elemPWidth;
			image.maxv=baseV+1/(float)elemPHeight;
		}else{
			image.minu=baseU+1/(float)elemPWidth;
			image.minv=baseV;
			image.maxu=baseU+1/(float)elemPWidth+1/(float)elemPWidth;
			image.maxv=baseV+1/(float)elemPHeight;	
		}
		this.image.draw(this.xPosition, this.yPosition);
	}
}
