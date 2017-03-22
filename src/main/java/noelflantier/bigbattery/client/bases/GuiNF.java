package noelflantier.bigbattery.client.bases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import noelflantier.bigbattery.Ressources;

public abstract class GuiNF extends GuiContainer{

	//BUTTON RELATIVE TO THE SCREEN
	//X & Y PARAMS OF MOUSECLIKED AND DRAW FUNCTIONS RELATIVE TO SCREEN
	
	public boolean componentloaded = false;
	public Hashtable<String, GuiComponent> componentList = new Hashtable<String, GuiComponent>();
	public static final ResourceLocation guiselements = new ResourceLocation(Ressources.MODID+":textures/gui/gui_elements.png");
	public String curentToolTipComponent = "";
	public ArrayList<Character> onlyNumericKey= new ArrayList<Character>(){{
		add('0');add('1');add('2');add('3');add('4');add('5');add('6');add('7');add('8');add('9');add('+');add('-');
		}};
	public ArrayList<Integer> genericKey = new ArrayList<Integer>(){{
		add(14);add(203);add(208);add(200);add(205);add(1);add(211);
		}};
	
	public GuiNF(Container container) {
		super(container);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if(button instanceof GuiButtonImage){
			((GuiButtonImage)button).enable = !((GuiButtonImage)button).enable;
		}
	} 
	
	@Override
	public void updateScreen() {
		super.updateScreen();
		this.componentList.forEach((s,g)->g.textFieldList.forEach((tf)->tf.updateCursorCounter()));
	}
	
	public GuiTextField getTFFocused(){
		GuiTextField f = null;
		for (Map.Entry<String,GuiComponent> entry : componentList.entrySet()){
			f = handleTFFocused(entry.getValue());
			if(f!=null)
				break;
		}
        return f;
	}
	
	public GuiTextField handleTFFocused(GuiComponent g){
		if(g.textFieldList.isEmpty())
			return null;
		return g.textFieldList.stream().filter((tf)->tf.isFocused()).findFirst().orElse(null);
	}
	
	@Override
	protected void keyTyped(char par1, int par2) throws IOException{
		GuiTextField tf = getTFFocused();
		if(( par2 == this.mc.gameSettings.keyBindInventory.getKeyCode() || par2 == 1 ) && tf==null) {
			super.keyTyped(par1, par2);
    	}
		if(tf==null){
			super.keyTyped(par1, par2);
		}
		if(tf!=null){
			if(tf instanceof GuiNFTextField){
				boolean flag = false;
				if( ((GuiNFTextField)tf).isOnlyNumeric ){
					flag = genericKey.contains(par2) || onlyNumericKey.contains(par1);
				}
				if(flag)
					tf.textboxKeyTyped(par1, par2);
			}else
				tf.textboxKeyTyped(par1, par2);
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException{
		super.mouseClicked(x, y, button);
		this.componentList.forEach((s,g)->handleTFClicked(g,x,y,button));
	}
	
	public void handleTFClicked(GuiComponent g, int x, int y, int button) {
		if(g.textFieldList.isEmpty())
			return;
		g.textFieldList.forEach((t)->{if(!g.textFieldReadOnly.contains(t)){t.mouseClicked(x-guiLeft, y-guiTop, button);}});
	}

	public void loadComponents(){
		if(this.componentloaded)return;
		this.componentloaded = true;
	}
	
	public abstract void updateToolTips(String key);
	
	//DRAW FIRST PLAN COORD RELATIVE TO SCREEN
	@Override
    public void drawScreen(int x, int y, float f){
		Locale.setDefault(Locale.US);
		curentToolTipComponent = "";
    	super.drawScreen(x, y, f);
    	drawItemStackHovered(x,y);
    	drawToolTips(x,y);
		drawOver(x,y);
	}

	public void drawItemStackHovered(int x, int y){
		Optional<Entry<String, GuiComponent>> result = componentList.entrySet().stream().filter((e)->e.getValue().getItemStackHovered(x-guiLeft, y-guiTop).isPresent()).findFirst();
		if( result.isPresent() ){
			Optional<ItemStack> io = result.get().getValue().getItemStackHovered(x-guiLeft, y-guiTop);
			if( io.isPresent() )
				renderToolTip(io.get(), x, y);
		}
		//ItemStack io = g.getItemStackHovered(x-guiLeft, y-guiTop);
    	/*ItemStack io = null;
		Enumeration<String> enumKey = this.componentList.keys();
		while (enumKey.hasMoreElements()) {
		    String key = enumKey.nextElement();
		    if( io == null )
		    	io = this.componentList.get(key).getItemStackHovered(x-guiLeft, y-guiTop);
		    if( io != null )
		    	break;
		}*/
	}
	
	public void drawImageButtons(int x, int y){
		for (int k = 0; k < this.buttonList.size(); ++k){
			if(this.buttonList.get(k) instanceof GuiButtonImage){
				((GuiButtonImage)this.buttonList.get(k)).drawImage();
			}
		}
	}
	
	public void drawToolTips(int x, int y){
		if(curentToolTipComponent != ""){
	    	this.updateToolTips(curentToolTipComponent);
    		((GuiToolTips)this.componentList.get(curentToolTipComponent)).showToolTips(x, y);
		}
	}
	
	public void drawOver(int x, int y){
		
	}
	
	//DRAW MIDDLE PLAN COORD RELATIVE TO GUILEFT & GUITOP
	@Override
    public void drawGuiContainerForegroundLayer(int x, int y){
		Enumeration<String> enumKey = this.componentList.keys();
		while (enumKey.hasMoreElements()) {
		    String key = enumKey.nextElement();
		    this.componentList.get(key).draw(x, y);
	    	if(this.componentList.get(key) instanceof GuiToolTips && this.componentList.get(key).isMouseHover(x,y)){
	    		curentToolTipComponent = key;
	    	}
		}
    	drawImageButtons(x,y);
    }

	//DRAW BACK PLAN COORD RELATIVE TO GUILEFT & GUITOP
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTickTime, int x, int y) {
		
	}
	
	public GuiButton getButtonById(int id){
		GuiButton tbt = null;
		for(int i = 0;i<this.buttonList.size();i++)
			if(this.buttonList.get(i) instanceof GuiButton && ((GuiButton)this.buttonList.get(i)).id==id){
				tbt = (GuiButton)this.buttonList.get(i);
			}
		return tbt;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		this.loadComponents();
		Enumeration<String> enumKey = this.componentList.keys();
		this.buttonList.clear();
		while (enumKey.hasMoreElements()) {
		    String key = enumKey.nextElement();
		    this.componentList.get(key).init(key);
		    for (GuiButton gb : this.componentList.get(key).buttonList){
		    	gb.visible = true;
    			this.buttonList.add(gb);
    		}
		}
	}
}
