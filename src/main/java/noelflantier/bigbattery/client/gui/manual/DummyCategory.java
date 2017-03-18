package noelflantier.bigbattery.client.gui.manual;

import noelflantier.bigbattery.client.bases.GuiComponent;

public class DummyCategory extends ABaseCategory{

	public DummyCategory(String name, int x, int y) {
		super(name, x, y);
		initComponent();
	}
	
	public void addComponent(String s, GuiComponent c){
		this.componentList.put(s, c);
	}
	public void addCategory(String s,ABaseCategory ca){
		this.listCategory.put(s, ca);
	}
	
	@Override
	public void initComponent() {
		
	}

}
