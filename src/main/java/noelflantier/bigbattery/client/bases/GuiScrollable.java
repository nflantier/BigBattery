package noelflantier.bigbattery.client.bases;

import java.util.Hashtable;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.util.ResourceLocation;
import noelflantier.bigbattery.Ressources;

public class GuiScrollable {

	public int maxComponent = 10;
	public int currentIndex = 0;
	public Hashtable<Integer, GuiComponent> componentList = new Hashtable<Integer, GuiComponent>();
	public int tickDelay = 2;
	public int tickInput = 0;
	public boolean showArrows = true;
	public boolean showBorders = true;
	public int x,y;
	public int width, height;
	private static final ResourceLocation guiselements = new ResourceLocation(Ressources.MODID+":textures/gui/gui_elements.png");
	public GuiImage arrowUp = new GuiImage(0, 0, 32,32 , 0F, 0.25F, 0.25F, 0.5F, guiselements);
	public GuiImage arrowDown = new GuiImage(0, 0, 32,32 , 0.25F, 0.25F, 0.5F, 0.5F, guiselements);
	public GuiImage topbot = new GuiImage(0, 0, 32,2 , 0F, 0.75F, 0.25F, 0.75F+2/128F, guiselements);
	public GuiImage rightleft = new GuiImage(0, 0, 2,32 , 0F, 0.75F, 2/128F, 1F, guiselements);
	
	
	public GuiScrollable(int maxComponent){
		this.maxComponent = maxComponent; 
	}
	
	public void addComponent(int id, GuiComponent gc){
		this.componentList.put(id, gc);
	}
	
	public void input(){
		this.inputKeyBoard();
		this.inputMouse();
	}
	
	public void inputKeyBoard(){
		this.tickInput--;
		if(tickInput>0)return;
		
		this.tickInput = 0;
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			this.incIndex();
			this.tickInput = tickDelay;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			this.decIndex();
			this.tickInput = tickDelay;
		}
		return;
	}
	
	public void inputMouse(){
		int w = Mouse.getDWheel();
		if(w<0)
			this.incIndex();
		if(w>0)
			this.decIndex();
		return;
	}
	
	public void incIndex(){
		this.currentIndex = this.componentList.size()>this.maxComponent?this.currentIndex<this.componentList.size()-this.maxComponent?this.currentIndex+1:this.componentList.size()-this.maxComponent:0;
	}
	
	public void decIndex(){
		this.currentIndex = this.currentIndex>0?this.currentIndex-1:0;
	}
	
	public void setArrowsPositionAndAlpha(int x, int y, int dec, float alpha){
		arrowUp.x = x;
		arrowUp.y = y;
		arrowDown.x = x;
		arrowDown.y = y+dec;
		arrowUp.alpha = alpha;
		arrowDown.alpha = alpha;
	} 
	
	public void setPositionSizeAndAlpha(int x, int y, int width, int height, float alpha){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		topbot.width = width;
		topbot.height = 1;
		rightleft.width = 1;
		rightleft.height = height;
		arrowUp.x = x+width-23;
		arrowUp.y = y-8;
		arrowDown.x = x+width-23;
		arrowDown.y = y+height-25;
		arrowUp.alpha = alpha;
		arrowDown.alpha = alpha;
	} 

	public void showTheBorders(int x, int y){
		topbot.x = this.x;
		topbot.y = this.y;
		topbot.draw(x, y);
		topbot.x = this.x;
		topbot.y = this.y+height;
		topbot.draw(x, y);
		
		rightleft.x = this.x;
		rightleft.y = this.y;
		rightleft.draw(x, y);
		rightleft.x = this.x+width-1;
		rightleft.y = this.y;
		rightleft.draw(x, y);
	}
	
	public void showTheArrows(int x, int y){
		arrowUp.draw(x, y);
		arrowDown.draw(x, y);
	}
	
	public void show(int x, int y){
		if(showArrows)
			showTheArrows(x,y);
		if(showBorders)
			showTheBorders(x,y);
		for(int i = this.currentIndex ; i < this.currentIndex + this.maxComponent ; i++){
			if(this.componentList.get(i)==null)break;
	    	this.componentList.get(i).scrollingYMarge = this.currentIndex*10;
		    this.componentList.get(i).draw(x,y);
		}
	}
}
