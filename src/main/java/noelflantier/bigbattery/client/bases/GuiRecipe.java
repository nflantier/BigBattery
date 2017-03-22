package noelflantier.bigbattery.client.bases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class GuiRecipe extends GuiComponentBase{
	
	public static enum TYPE{
		VANILLA("VANILLA",70,60),
		FURNACE("FURNACE",70,18);
		
		public String usageName;
		public int width;
		public int height;
		private TYPE(String usageName, int width, int height){
			this.usageName = usageName;
			this.width = width;
			this.height = height;
		}
	}
	
	public boolean isRenderingItems = false;
	public boolean isRenderingName = true;
	public String name;
	public List<GuiItemStack> guiItemStackList = new ArrayList<GuiItemStack>();
	public List<GuiItemStack> guiItemStackToCraft = new ArrayList<GuiItemStack>();
	public List<IRecipe> vanillaRecipes = new ArrayList<IRecipe>();
	public TYPE recipeType;
	public int additionalWidth = 0;
	public int additionalHeight = 0;
	public static int heightSlot = 16;
	public static int widthSlot = 16;
	public static int heightDSlot = 17;
	public static int widthDSlot = 17;
	
	public GuiRecipe(String name, int x, int y, ItemStack toCraft, TYPE type){
		super(x,y);
		this.name = name;
		this.recipeType = type;
		getAndSetRecipe(toCraft);
	}
	public GuiRecipe(String name, int x, int y, List<GuiItemStack> toCraft, TYPE type, List<GuiItemStack> li){
		super(x,y);
		this.name = name;
		this.recipeType = type;
		this.guiItemStackList.addAll(li);
		this.guiItemStackToCraft.addAll(toCraft);
	}
	public GuiRecipe(String name, int x, int y, List<GuiItemStack> toCraft, TYPE type, List<GuiItemStack> li, int w, int h){
		this(name, x, y, toCraft, type, li);
		setAdditionalValues(w, h);
	}
	public GuiRecipe(String name, int x, int y, ItemStack toCraft, List<GuiItemStack> li){
		super(x,y);
		this.name = name;
		this.recipeType = TYPE.VANILLA;
		this.guiItemStackList.addAll(li);
		this.guiItemStackToCraft.add(new GuiItemStack(toCraft));
	}
	
	public static List<GuiItemStack> getGuiItemStack(List<ItemStack> l){
		return l.stream().map(i->new GuiItemStack(i)).collect(Collectors.toList());
	}
	
	public static boolean isStackSame(ItemStack st1, ItemStack st2){
		return st1!=null && st2!=null && ( st1.getItem() == st2.getItem() ) && ( st1.getItemDamage() == st2.getItemDamage() );
	}
	
	public static List<GuiItemStack> computeVanillaRecipes(IRecipe recipe){
		if(recipe instanceof ShapedRecipes){
			//this.guiItemStackList.addAll(getGuiItemStack(new ArrayList<ItemStack>(Arrays.asList(((ShapedRecipes)recipe).recipeItems))));
		}else if (recipe instanceof ShapelessOreRecipe){
			//Arrays.asList(((ShapelessOreRecipe)recipe).getInput()).stream().forEach(e-> {if(e!=null){System.out.println(e.getClass());}});
			
			List<ItemStack> li = new ArrayList<ItemStack>();
			for(int i = 0 ; i < ((ShapelessOreRecipe)recipe).getInput().size() ; i++ ){
				Object o = ((ShapelessOreRecipe)recipe).getInput().get(i);
				if(o instanceof ItemStack)
					li.add((ItemStack) o);
				else if(o instanceof NonNullList<?>)
					li.add( ( ( NonNullList<ItemStack> ) o ).get(0) );
				else
					li.add(null);
			}
			return getGuiItemStack(li);
		}else if (recipe instanceof ShapedOreRecipe){
			Arrays.asList(((ShapedOreRecipe)recipe).getInput()).stream().forEach(e-> {if(e!=null && e instanceof ItemStack && ((ItemStack)e).getMetadata() >= OreDictionary.WILDCARD_VALUE){((ItemStack)e).setItemDamage(0);}});
			List<ItemStack> li = new ArrayList<ItemStack>();
			for(int i = 0 ; i < ((ShapedOreRecipe)recipe).getInput().length ; i++ ){
				Object o = ((ShapedOreRecipe)recipe).getInput()[i];
				if(o instanceof ItemStack)
					li.add((ItemStack) o);
				else if(o instanceof NonNullList<?>)
					li.add( ( ( NonNullList<ItemStack> ) o ).get(0) );
				else
					li.add(null);
			}
			return getGuiItemStack(li);
		}
		return null;
	}
	
	public static List<List<GuiItemStack>> getGuiVanillaRecipesForStack(String name, int x, int y, ItemStack stack){
		List<List<GuiItemStack>> lg = new ArrayList<List<GuiItemStack>>();
		List<IRecipe> li = (List<IRecipe>) CraftingManager.getInstance().getRecipeList().stream().filter((s)->isStackSame(((IRecipe)s).getRecipeOutput(),stack)).collect(Collectors.toList());
		if(li.isEmpty())
			return null;
		li.stream().forEach(g->lg.add(computeVanillaRecipes(g)));
		return lg;
	}
	
	public boolean getAndSetRecipe(ItemStack stack){
		if(TYPE.VANILLA==recipeType){
			vanillaRecipes = (List<IRecipe>) CraftingManager.getInstance().getRecipeList().stream().filter((s)->isStackSame(((IRecipe)s).getRecipeOutput(),stack)).collect(Collectors.toList());
			if(vanillaRecipes.isEmpty())
				return false;
			guiItemStackToCraft.addAll(getGuiItemStack(new ArrayList<ItemStack>(){{add(vanillaRecipes.get(0).getRecipeOutput());}}));
			guiItemStackList.addAll(computeVanillaRecipes(vanillaRecipes.stream().findFirst().get()));
		}
		return true;
	}
	
	public void setAdditionalValues(int w, int h){
		additionalHeight = h;
		additionalWidth = w;
	}
	
	public int getActualWidth(){
		return recipeType.width + additionalWidth ;
	}
	public int getActualHeight(){
		return recipeType.height+ additionalHeight ;
	}
	
	public void drawBorder(){
		float ha = horizontal.alpha;
		float va = vertical.alpha;
		horizontal.alpha = 0.2F;
		vertical.alpha = 0.2F;
		horizontal.width = getActualWidth()+8;
		horizontal.height = 1;
		horizontal.x = this.x-4;
		horizontal.y = this.y-4;
		horizontal.draw(x, y);

		horizontal.x = this.x-4;
		horizontal.y = this.y+getActualHeight()+4;
		horizontal.draw(x, y);
		
		vertical.width = 1;
		vertical.height = getActualHeight()+8+1;
		vertical.x = this.x-4;
		vertical.y = this.y-4;
		vertical.draw(x, y);
		
		vertical.x = this.x+getActualWidth()+4;
		vertical.y = this.y-4;
		vertical.draw(x, y);
		
		horizontal.alpha = ha;
		vertical.alpha = va;
	}
	
	public void drawGrid(int x, int y){
		drawBorder();
		if(this.recipeType==TYPE.VANILLA){			
			horizontal.width = widthSlot*3+2;
			horizontal.height = 1;
			horizontal.x = this.x;
			horizontal.y = this.y+heightSlot;
			horizontal.draw(x, y);
			horizontal.y = this.y+heightSlot*2+1;
			horizontal.draw(x, y);
			vertical.width = 1;
			vertical.height = heightSlot*3+2;
			vertical.x = this.x+widthSlot;
			vertical.y = this.y;
			vertical.draw(x, y);
			vertical.x = this.x+widthSlot*2+1;
			vertical.draw(x, y);
		}else if(this.recipeType==TYPE.FURNACE){
			horizontal.width = widthSlot+1;
			horizontal.height = 1;
			horizontal.x = this.x;
			horizontal.y = this.y+heightSlot;
			horizontal.draw(x, y);

			arrowH.x=this.x+20;
			arrowH.y=this.y-6;
			arrowH.draw(x, y);
		}
	}
	
	public void drawResult(int x, int y){
		if(isRenderingItems)
			this.guiItemStackToCraft.forEach((g)->g.draw(x, y));
		if(this.recipeType==TYPE.VANILLA){
			for(int i = 0 ; i<this.guiItemStackToCraft.size();i++){
				horizontal.width = widthSlot;
				horizontal.height = 1;
				horizontal.x = this.x+getActualWidth()-widthSlot;
				horizontal.y = this.y+getActualHeight()/2;
				horizontal.draw(x, y);
			}
		}else if(this.recipeType==TYPE.FURNACE){
			for(int i = 0 ; i<this.guiItemStackToCraft.size();i++){
				horizontal.width = widthSlot;
				horizontal.height = 1;
				horizontal.x = this.x+getActualWidth()-widthSlot;
				horizontal.y = this.y+heightSlot;
				horizontal.draw(x, y);
			}
		}
	}
	
	public void drawRecipe(int x, int y){
		if(isRenderingItems)
			this.guiItemStackList.forEach((g)->g.draw(x, y));
	}
	
	@Override
	public void draw(int x, int y) {
		drawGrid(x,y);
		drawRecipe(x,y);
		drawResult(x,y);
		drawName(x,y);
	}
	
	public int getScaledValue(int v, float scale){
		return (int) (v+(v-v*scale)/scale);
	}
	
	public void drawAutoWidthName(String n, int x, int y, int width, float scale){
		String[] t = n.split("\\s+");
		String str = "";
		int suc = 0;
		for(int i = 0 ; i < t.length ; i++){
			if((float)FR.getStringWidth(str+" "+t[i])*scale>width){	
				FR.drawString(String.format("%s%s%s", TextFormatting.BLACK, str, TextFormatting.RESET), getScaledValue(x,scale), getScaledValue(y+(int)(10*scale*suc),scale), 4210752);
				suc+=1;
				str = "";
			}
			if(str.equals(""))
				str+=t[i];
			else
				str+=" "+t[i];
		}
		if(!str.equals("") && !str.equals(" ")){
			FR.drawString(String.format("%s%s%s", TextFormatting.BLACK, str, TextFormatting.RESET), getScaledValue(x,scale), getScaledValue(y+(int)(10*scale*suc),scale), 4210752);
		}
	}
	
	public void drawName(int x, int y) {
		if(!isRenderingName)
			return;
		float scale = 0.6F;
		GL11.glPushMatrix();
			GL11.glScalef(scale, scale, 1);
			int xName = 0;
			int yName = 0;
			int w = FR.getStringWidth(name);
			if(this.recipeType==TYPE.VANILLA){
				yName = 52;
				drawAutoWidthName(name, this.x+xName, this.y+yName, getActualWidth(), scale);
			}
		GL11.glPopMatrix();
	}
	
	@Override
	public boolean isMouseHover(int mx, int my) {
		return false;
	}
}
