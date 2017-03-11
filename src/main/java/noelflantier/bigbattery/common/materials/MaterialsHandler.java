package noelflantier.bigbattery.common.materials;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MaterialsHandler{
	public static Map<Integer,Electrode> electrodeListByPotential= new HashMap<Integer,Electrode>();//desc
	public static Map<Integer,Electrolyte> electrolyteListByPotential= new HashMap<Integer,Electrolyte>();//desc
	public static Map<Integer,Conductive> conductiveListByRatio= new HashMap<Integer,Conductive>();//desc
	public static final String FILE_NAME_MATERIALS = "materials.xml";
	public static Comparator<MaterialsHandler.Electrode> byElectrodePotential = (e1, e2) -> Double.compare(
            e1.potential,e2.potential);
	public static Comparator<MaterialsHandler.Electrolyte> byElectrolytePotential = (e1, e2) -> Double.compare(
            e1.potential,e2.potential);
	public static Comparator<MaterialsHandler.Conductive> byConductiveRatio = (e1, e2) -> Double.compare(
            e1.ratioEfficiency,e2.ratioEfficiency);
	
	public MaterialsHandler() {
	}
	
	public static final MaterialsHandler instance = new MaterialsHandler();
	public static MaterialsHandler getInstance() {
		return instance;
	}
	
	public void loadRecipes() {
		List<Material> rh = ConfigHandler.loadRecipeConfig(FILE_NAME_MATERIALS);
	    if(rh != null) {
	    	
	    	electrodeListByPotential.putAll(rh.stream().filter(m->m instanceof MaterialsHandler.Electrode).map(s->(MaterialsHandler.Electrode)s).sorted(byElectrodePotential).collect(Collectors.toMap(Electrode::getID,p->p)));
	    	electrolyteListByPotential.putAll(rh.stream().filter(m->m instanceof MaterialsHandler.Electrolyte).map(s->(MaterialsHandler.Electrolyte)s).sorted(byElectrolytePotential).collect(Collectors.toMap(Electrolyte::getID, p->p)));
	    	conductiveListByRatio.putAll(rh.stream().filter(m->m instanceof MaterialsHandler.Conductive).map(s->(MaterialsHandler.Conductive)s).sorted(byConductiveRatio).collect(Collectors.toMap(Conductive::getID, p->p)));

	    	/*System.out.println(".................................");
	    	electrodeListByPotential.entrySet().stream().forEach((e)->e.getValue().listStack.stream().forEach(System.out::println));
	    	electrolyteListByPotential.entrySet().stream().forEach((e)->e.getValue().listStack.stream().forEach(System.out::println));
	    	conductiveListByRatio.entrySet().stream().forEach((e)->e.getValue().listStack.stream().forEach(System.out::println));
	    	electrodeListByPotential.entrySet().stream().forEach((e)->e.getValue().weightToStack.entrySet().stream().forEach(System.out::println));
	    	electrolyteListByPotential.entrySet().stream().forEach((e)->e.getValue().weightToStack.entrySet().stream().forEach(System.out::println));
	    	System.out.println("..................................... "+electrodeListByPotential.size());
	    	System.out.println("..................................... "+electrolyteListByPotential.size());
	    	System.out.println("..................................... "+conductiveListByRatio.size());
	    	electrolyteListByPotential.entrySet().stream().forEach((e)->e.getValue().weightToStack.entrySet().stream().forEach(System.out::println));*/
	    } else {
	    	System.out.println("Could not load materials config ");
	    }
	}
	
	public static MaterialsHandler.Electrode getElectrodeFromStack(ItemStack stack){
    	return electrodeListByPotential.entrySet().stream().filter((e)->e.getValue().weightToStack.entrySet().stream().anyMatch((w)->w.getValue().isItemEqualIgnoreDurability(stack))).findFirst().get().getValue();
    }

    public static double getWeightFromStack(MaterialsHandler.Electrode electrode, ItemStack stack){
    	if(electrode == null || stack == null)
    		return 1;
    	return electrode.weightToStack.entrySet().stream().filter((i)->i.getValue().isItemEqualIgnoreDurability(stack)).findFirst().get().getKey();
    }
    
    public static MaterialsHandler.Electrolyte getElectrolyteFromStack(ItemStack stack){
    	return electrolyteListByPotential.entrySet().stream().filter((e)->e.getValue().weightToStack.entrySet().stream().anyMatch((w)->w.getValue().isItemEqualIgnoreDurability(stack))).findFirst().get().getValue();
    }

    public static double getWeightFromStack(MaterialsHandler.Electrolyte electrolyte, ItemStack stack){
    	if(electrolyte == null || stack == null)
    		return 1;
    	return electrolyte.weightToStack.entrySet().stream().filter((i)->i.getValue().isItemEqualIgnoreDurability(stack)).findFirst().get().getKey();
    }
    
    public static MaterialsHandler.Conductive getConductiveFromStack(ItemStack stack){
    	return conductiveListByRatio.entrySet().stream().filter((e)->e.getValue().weightToStack.entrySet().stream().anyMatch((w)->w.getValue().isItemEqualIgnoreDurability(stack))).findFirst().get().getValue();
    }

    public static double getWeightFromStack(MaterialsHandler.Conductive conductive, ItemStack stack){
    	if(conductive == null || stack == null)
    		return 1;
    	return conductive.weightToStack.entrySet().stream().filter((i)->i.getValue().isItemEqualIgnoreDurability(stack)).findFirst().get().getKey();
    }
    
    public static boolean anyMatchConductive(ItemStack stack){
    	return conductiveListByRatio.entrySet().stream().anyMatch(e->e.getValue().listStack.stream().anyMatch((l)->l.isItemEqualIgnoreDurability(stack)));
    }
    public static boolean anyMatchElectrode(ItemStack stack){
    	return electrodeListByPotential.entrySet().stream().anyMatch(e->e.getValue().listStack.stream().anyMatch((l)->l.isItemEqualIgnoreDurability(stack)));
    }
    public static boolean anyMatchElectrolyte(ItemStack stack){
    	return electrolyteListByPotential.entrySet().stream().anyMatch(e->e.getValue().listStack.stream().anyMatch((l)->l.isItemEqualIgnoreDurability(stack)));
    }
    
	public static class Material<T>{
		public static int IDMATERIALS = 1;
		public static int getNextIdMaterials(){return IDMATERIALS++;}
		
		public String name;
		public int materialID;
		public Map<Double,ItemStack> weightToStack = new HashMap<Double,ItemStack>();
		public Map<ItemStack,Double> stackToWeight = new HashMap<ItemStack,Double>();
		public List<ItemStack> listStack = new ArrayList<ItemStack>();
		
		public Material(String s){
			this.name = s;
			setID(getNextIdMaterials());
		}
		
		public int getID(){
			return materialID;
		}
		
		public T setID(int i){
			this.materialID = i;
			return (T) this;
		}
		
		public T addMaterial(Block block) {
			addMaterial(new ItemStack(block), 9);
			return (T) this;
		}
		public T addMaterial(Block block, double weight) {
			addMaterial(new ItemStack(block), weight);
			return (T) this;
		}
		public T addMaterial(String oreDictName, double weight){
			if(OreDictionary.doesOreNameExist(oreDictName)){
				List<ItemStack> l = OreDictionary.getOres(oreDictName);
				if(!l.isEmpty())
					addMaterial(l.get(0), weight);
			}
			return (T) this;
		}
		public T addMaterial(ItemStack stack, double weight){
			weightToStack.put(weight,stack.copy());
			stackToWeight.put(stack.copy(), weight);
			listStack.add(stack.copy());
			return (T) this;
		}
	}
	
	public static class Electrode extends Material<Electrode>{
		
		public static enum TYPE{
			NONE,
			ANODE,
			CATHODE;
		}
		
		public double potential = 0;
		public List<Double> oxydationNumber;
		public TYPE type = TYPE.NONE;
		
		public Electrode(String s, double e, List<Double>o){
			super(s);
			this.potential = e;
			this.oxydationNumber = o;
		}
		public double getPotential(){
			return potential;
		}
		public void setType(TYPE t){
			type = t;
		}
	}

	public static class Electrolyte extends Material<Electrolyte>{
		
		public enum Type {
			AQUEOUS(1.0F,1.0D),
			IONIC(2.5F,1.5D),
			SOLID(1.8F,2.0D);
			public double ratioVoltage = 0;
			public double ratioDecay = 0;
			private Type(double rv, double rd){
				this.ratioVoltage = rv;
				this.ratioDecay = rd;
			}
			
			public static Type getTypeFromIndex(int idx){
				return idx >= Type.values().length ? Type.values()[0] : Type.values()[idx];
			}
		}
		
		public double potential = 0;
		public List<Double> oxydationNumber;
		public Type electrolyteType;
		
		public Electrolyte(String s, double e, List<Double> o, Type type) {
			super(s);
			this.potential = e;
			this.oxydationNumber = o;
			this.electrolyteType = type;
		}
		public double getPotential(){
			return potential;
		}
		
	}	


	public static class Conductive extends Material<Conductive>{
		public double ratioEfficiency = 1F;
		public Conductive(String s,double r) {
			super(s);
			this.ratioEfficiency = r;
		}
		public double getRatioRF(){
			return ratioEfficiency;
		}
		
	}

}
