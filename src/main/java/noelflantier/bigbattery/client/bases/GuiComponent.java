package noelflantier.bigbattery.client.bases;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class GuiComponent extends GuiComponentBase{

	public List<GuiButton> buttonList = new ArrayList<GuiButton>();
	public List<GuiTextField> textFieldList  = new ArrayList<GuiTextField>();
	public List<GuiTextField> textFieldReadOnly  = new ArrayList<GuiTextField>();
	public List<GuiImage> imageList = new ArrayList<GuiImage>();
	public List<GuiItemStack> itemStackList = new ArrayList<GuiItemStack>();
	public List<GuiRecipe> recipeList = new ArrayList<GuiRecipe>();
	public Hashtable<Integer,String> stringList = new Hashtable<Integer,String>();
	public Hashtable<Integer,Integer[]> cStringList = new Hashtable<Integer,Integer[]>();
	public String name;
	public boolean handleRecipesGroup = false;
	public int xButtonRecipes = 0;
	public int yButtonRecipes = 0;
	public int btRecipesPrev = 1001;
	public int btRecipesNext = 1002;
	public int currentGuiRecipe = 0;
	public int nbGuiRecipeHorizontal = 3;
	public int nbGuiRecipeVertical = 1;
	public int stringX;
	public int stringY;
	public float globalScale = 1;
	public boolean isVisible = true;
	public boolean isLink = false;
	public String customLink;
	public boolean isScrolable = false;
	public int scrollingYMarge = 0;
	public int globalYMarge = 0;
	public final int fontHeight = 10;
    public int stringID = -1;
    public TextFormatting defColor = TextFormatting.DARK_GRAY;
    public TextFormatting color = TextFormatting.DARK_GRAY;
    public TextFormatting linkColor = TextFormatting.GRAY;
	
	public GuiComponent(int x, int y){
		super(x,y);
	}

	public GuiComponent(int x, int y, int w, int h){
		super(x,y,w,h);
	}
	
	public void reset(){
		this.buttonList.clear();
		this.textFieldList.clear();
		this.stringList.clear();
		this.imageList.clear();
		this.itemStackList.clear();
		this.recipeList.clear();
		this.cStringList.clear();
		this.globalYMarge = 0;
		this.stringID = -1;
		this.stringY = 0;
		this.currentGuiRecipe = 0;
		this.stringX = 0;
	}
	
	public void addTextField(int decx, int decy, int width, int height){
		GuiTextField tf = new GuiTextField(this.textFieldList.size(),FR,this.x+decx,this.y+decy,width,height);
		tf.setCanLoseFocus(true);
		tf.setMaxStringLength(10);
		tf.setFocused(false);
		this.textFieldList.add(tf);
	}
	public void addSFATextField(int decx, int decy, int width, int height){
		GuiNFTextField tf = new GuiNFTextField(this.textFieldList.size(),FR,this.x+decx,this.y+decy,width,height);
		tf.setCanLoseFocus(true);
		tf.setMaxStringLength(10);
		tf.setFocused(false);
		this.textFieldList.add(tf);
	}
	public void addButton(int id, int decx, int decy, int width, int height, String str){
		GuiButton bt = new GuiButton(id,this.x+decx,this.y+decy,width,height,str);
		bt.visible = false;
		this.buttonList.add(bt);
	}

	public void addSfaButton(int id, int decx, int decy, int width, int height, String str){
		GuiNFButton bt = new GuiNFButton(id,this.x+decx,this.y+decy,width,height,str);
		bt.visible = false;
		this.buttonList.add(bt);
	}

	public void addImage(GuiImage img){
		this.imageList.add(img);
	}

	public void addItemStack(GuiItemStack st){
		this.itemStackList.add(st);
	}
	
	public void addRecipe(GuiRecipe r){
		updateRecipe(r);
		recipeList.add(r);
		r.guiItemStackList.forEach((i)->i.idRecipe = recipeList.size()-1);
		r.guiItemStackToCraft.forEach((i)->i.idRecipe = recipeList.size()-1);
		itemStackList.addAll(r.guiItemStackList);
		itemStackList.addAll(r.guiItemStackToCraft);
	}

	public void addRecipe(GuiRecipe r, int id){
		updateRecipe(r);
		recipeList.add(r);
		r.guiItemStackList.forEach((i)->i.idRecipe = id);
		r.guiItemStackToCraft.forEach((i)->i.idRecipe = id);
		itemStackList.addAll(r.guiItemStackList);
		itemStackList.addAll(r.guiItemStackToCraft);
	}

	public void updateRecipe(GuiRecipe recipe){
		if(recipe==null)
			return;
		if(recipe.recipeType==GuiRecipe.TYPE.VANILLA){
						
			for(int k = 0 ; k < recipe.guiItemStackList.size() ; k++){
				recipe.guiItemStackList.get(k).x=((k%3)*GuiRecipe.widthDSlot)+recipe.x;
				recipe.guiItemStackList.get(k).y=((k/3)*GuiRecipe.heightDSlot)+recipe.y;			
			}
			for(int k = 0 ; k < recipe.guiItemStackToCraft.size() ; k++){
				recipe.guiItemStackToCraft.get(k).x=recipe.x+k*GuiRecipe.widthSlot+GuiRecipe.TYPE.VANILLA.width-GuiRecipe.widthSlot;
				recipe.guiItemStackToCraft.get(k).y=recipe.y+GuiRecipe.TYPE.VANILLA.height/2-GuiRecipe.heightSlot;			
			}
			
		}
	}
	
	public void updateRecipe(){
		if(this.recipeList.isEmpty())
			return;
		int l = 0;
		for( int i = 0+(this.currentGuiRecipe*this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical) ; i < this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical+(this.currentGuiRecipe*this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical) ; i++ ){
			if(this.recipeList.size()>i && this.recipeList.get(i)!=null){
				this.recipeList.get(i).x = this.x+(l%this.nbGuiRecipeHorizontal)*(this.recipeList.get(i).recipeType.width+10);
				this.recipeList.get(i).y = this.y+(l/this.nbGuiRecipeHorizontal)*(this.recipeList.get(i).recipeType.height+10);
				this.updateRecipe(this.recipeList.get(i));
				l+=1;
			}
		}
	}
	
	public void addImageButton(GuiButtonImage bt, float baseU, float baseV, int elW, int elH, boolean enable){
		bt.baseU = baseU;
		bt.baseV = baseV;
		bt.elemPHeight = elH;
		bt.elemPWidth = elW;
		bt.enable = enable;
		this.buttonList.add(bt);
	}
	
	public void addButton(GuiButton bt){
		this.buttonList.add(bt);
	}
	
	public int nextStringId(){
		this.stringID+=1;
		return this.stringID;
	}
	
	public int addText(String str, int decx, int decy){
		int nid = this.nextStringId();
		this.stringList.put(nid,str);
		this.cStringList.put(nid, new Integer[]{decx,decy+stringY+globalYMarge});
		
		this.globalYMarge+=decy;
		this.stringY += this.fontHeight;
		return nid;
	}

	public void addTextAutoWitdh(String text, int decx, int decy, int width){
		String[] t = text.split("\\s+");
		String str = "";
		for(int i = 0 ; i < t.length ; i++){
			if(FR.getStringWidth(str+" "+t[i])>width){
				this.addText(str, decx, decy);
				str = "";
			}
			if(str.equals(""))
				str+=t[i];
			else
				str+=" "+t[i];
		}
		if(!str.equals("") && !str.equals(" "))
			this.addText(str, decx, decy);
	}
	
	public boolean clicked(int x, int y){
		return this.isLink && this.isMouseHover(x, y);
	}
	
	public void replaceString(int id, String str){
		this.stringList.replace(id, str);
	}
	
	public void draw(int x, int y){
		Locale.setDefault(Locale.US);
		this.color = this.defColor;
		if(this.isLink && this.isMouseHover(x, y)){
    		this.color = this.linkColor;
		}
		Iterator<Map.Entry<Integer,String>> it = stringList.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer,String> entry = it.next();
			Integer[] t = this.cStringList.get(entry.getKey());
			
			GL11.glPushMatrix();
				GL11.glScalef(globalScale, globalScale, 1);
				int xp = (int) (this.x+t[0]+(this.x-this.x*globalScale)/globalScale);
				int yp = (int) (this.y-this.scrollingYMarge+t[1]+((this.y-this.scrollingYMarge)-(this.y-this.scrollingYMarge)*globalScale)/globalScale);
				FR.drawString(String.format("%s%s%s", this.color, entry.getValue(), TextFormatting.RESET), xp, yp, 4210752);
			GL11.glPopMatrix();
		}
		this.color = this.defColor;

		this.textFieldList.forEach((t)->t.drawTextBox());
		this.imageList.forEach((i)->i.draw(x, y));
		this.itemStackList.forEach((i)->drawItemStack(i));
		drawRecipes(x,y);
	}
	
	public void init(String name){
		this.name = name;
		if(handleRecipesGroup)
			updateRecipe();
		
		if(this.recipeList.size()>this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical){
			this.currentGuiRecipe = 0;
			this.buttonList.clear();
			btRecipesPrev = this.name.hashCode();
			btRecipesNext = this.name.hashCode()+1;
			addButton(btRecipesPrev,xButtonRecipes,yButtonRecipes,20,20,"<");
			addButton(btRecipesNext,xButtonRecipes+21,yButtonRecipes,20,20,">");
		}
	}
	
	public void handleButton(GuiButton button){
		if(!handleRecipesGroup)
			return;
		if(this.recipeList.size()<=this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical)
			return;
		if(button.id==btRecipesNext){
			this.currentGuiRecipe=this.currentGuiRecipe+1<Math.ceil((float)this.recipeList.size()/(float)(this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical))?this.currentGuiRecipe+1:this.currentGuiRecipe;
			updateRecipe();
		}else if(button.id==btRecipesPrev){
			this.currentGuiRecipe=this.currentGuiRecipe-1>0?this.currentGuiRecipe-1:0;
			updateRecipe();
		}
	}
	
	public void drawRecipes(int x, int y){
		if(this.recipeList.isEmpty())
			return;
		for( int i = 0+(this.currentGuiRecipe*this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical) ; i < this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical+(this.currentGuiRecipe*this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical) ; i++ ){
			if(this.recipeList.size()>i && this.recipeList.get(i)!=null){
				this.recipeList.get(i).draw(x, y);
			}
		}
	}
	
	public void drawItemStack(GuiItemStack i){
		if(i.idRecipe>=this.currentGuiRecipe*this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical 
				&& i.idRecipe<this.currentGuiRecipe*this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical+
				this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical)
			i.draw(x, y);
	} 
	
	public boolean isMouseHover(int mx, int my){
		return mx<=this.x+this.width && mx>=this.x && my<this.y-this.scrollingYMarge+this.height && my>this.y-this.scrollingYMarge;
	}

	public ItemStack getItemStackHovered(int x, int y) {
	    if(!itemStackList.isEmpty()){
	    	for(int i = 0 ; i < itemStackList.size() ; i++){
	    		if(itemStackList.get(i).stack!=null 
	    				&& itemStackList.get(i).idRecipe>=this.currentGuiRecipe*this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical 
	    				&& itemStackList.get(i).idRecipe<this.currentGuiRecipe*this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical+
	    				this.nbGuiRecipeHorizontal*this.nbGuiRecipeVertical 
	    				&& itemStackList.get(i).isMouseHover(x, y)){
	    			return itemStackList.get(i).stack.copy();
	    		}
	    	}
	    }
		return null;
	}
}
