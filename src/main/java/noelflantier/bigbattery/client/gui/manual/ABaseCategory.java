package noelflantier.bigbattery.client.gui.manual;

import java.util.Hashtable;

import noelflantier.bigbattery.client.bases.GuiComponent;


public abstract class ABaseCategory{

	public Hashtable<String,ABaseCategory> listCategory = new Hashtable<String,ABaseCategory>();
	public Hashtable<String, GuiComponent> componentList = new Hashtable<String, GuiComponent>();
	public int x;
	public int y;
	public String name;
	
	public ABaseCategory(String name, int x, int y){
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public void addLink(Hashtable<String,ABaseCategory> l){
		l.putAll(listCategory);
	}
	public abstract void initComponent();
	public Hashtable<String, GuiComponent> getComponents(){
		return componentList;
	}
}
