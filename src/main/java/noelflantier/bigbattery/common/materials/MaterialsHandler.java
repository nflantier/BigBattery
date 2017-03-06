package noelflantier.bigbattery.common.materials;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MaterialsHandler{
	/*int i = 1;
	electrodeListByPotential.put(i,new Electrode("Iron", 3.27F,new double[]{3D}).addMaterial(Blocks.IRON_BLOCK).addMaterial(new ItemStack(Items.IRON_INGOT), 1).setID(i));
	i++;
	electrodeListByPotential.put(i,new Electrode("Gold", -0.1F,new double[]{-4D,-3D,-2D,-1D,0D,1D,2D,3D,4D}).addMaterial(Blocks.GOLD_BLOCK).addMaterial(new ItemStack(Items.GOLD_INGOT), 1).addMaterial(new ItemStack(Items.GOLD_NUGGET), -9).setID(i));
	i++;
	electrolyteListByPotential.put(i,new Electrolyte("Book", -3.04F,new double[]{1D}, Electrolyte.Type.IONIC).addMaterial(Blocks.BOOKSHELF,9).setID(i));
	i++;
	conductiveListByRatio.put(i,new Conductive("RedStone", 1.1F).setID(i).addMaterial(Blocks.REDSTONE_BLOCK,1));
	i++;
	conductiveListByRatio.put(i,new Conductive("Lapiz", 0.9F).setID(i).addMaterial(Blocks.LAPIS_BLOCK,1));*/
	/*i++;
	electrodeListByPotential.add(i,new Electrode("Silver", 1.98F,new float[]{1F}).setID(i));
	i++;
	electrodeListByPotential.add(i,new Electrode("Lead", 1.69F,new float[]{2F}).setID(i));
	i++;
	electrodeListByPotential.add(i,new Electrode("Platinium", 1.188F,new float[]{2F,4F}).setID(i));//shiny
	i++;
	electrodeListByPotential.add(i,new Electrode("Copper", 0.52F,new float[]{2F}).setID(i));
	i++;
	electrodeListByPotential.add(i,new Electrode("Graphite", 0.13F,new float[]{-4F,-3F,-2F,-1F,0F,1F,2F,3F,4F}).setID(i));
	i++;
	electrodeListByPotential.add(i,new Electrode("Zinc", -0.13F,new float[]{2F,4F,-4F}).setID(i));
	i++;
	electrodeListByPotential.add(i,new Electrode("Nickel", -0.25F,new float[]{2F}).setID(i));//ferrous
	i++;
	electrodeListByPotential.add(i,new Electrode("Zinc", -0.76F,new float[]{2F}).setID(i));
	i++;
	electrolyteListByPotential.add(i,new Electrolyte("Chlore", 1.35F,new float[]{5F}, Electrolyte.Type.AQUEOUS).setID(i));
	i++;
	electrolyteListByPotential.add(i,new Electrolyte("Lithium", -3.04F,new float[]{1F}, Electrolyte.Type.IONIC).setID(i));
	i++;
	electrolyteListByPotential.add(i,new Electrolyte("Calcium", -3.8F,new float[]{2F}, Electrolyte.Type.SOLID).setID(i));
	i++;
	conductiveListByRatio.add(i,new Conductive("Alluminium", 2.1F).setID(i));
	i++;
	conductiveListByRatio.add(i,new Conductive("Copper", 1.2F).setID(i));*/
	public static Map<Integer,Electrode> electrodeListByPotential= new HashMap<Integer,Electrode>();//desc
	public static Map<Integer,Electrolyte> electrolyteListByPotential= new HashMap<Integer,Electrolyte>();//desc
	public static Map<Integer,Conductive> conductiveListByRatio= new HashMap<Integer,Conductive>();//desc
	public static final String FILE_NAME_MATERIALS = "materials.xml";
	public static int IDMATERIALS = 0;
	public static int getNextIdMaterials(){return IDMATERIALS++;}
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
	    	
	    	electrodeListByPotential.putAll(rh.stream().filter(m->m instanceof MaterialsHandler.Electrode).map(s->(MaterialsHandler.Electrode)s).sorted(byElectrodePotential).collect(Collectors.toMap(p->getNextIdMaterials(), p->p)));
	    	electrolyteListByPotential.putAll(rh.stream().filter(m->m instanceof MaterialsHandler.Electrolyte).map(s->(MaterialsHandler.Electrolyte)s).sorted(byElectrolytePotential).collect(Collectors.toMap(p->getNextIdMaterials(), p->p)));
	    	conductiveListByRatio.putAll(rh.stream().filter(m->m instanceof MaterialsHandler.Conductive).map(s->(MaterialsHandler.Conductive)s).sorted(byConductiveRatio).collect(Collectors.toMap(p->getNextIdMaterials(), p->p)));
	    	
	    	System.out.println("..................................... "+electrodeListByPotential.size());
	    	System.out.println("..................................... "+electrolyteListByPotential.size());
	    	System.out.println("..................................... "+conductiveListByRatio.size());
	    	//recipes.addAll(rh.recipes);
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
	public static class Material<T>{
		public String name;
		public int materialID;
		public Map<Double,ItemStack> weightToStack = new HashMap<Double,ItemStack>();
		public Map<ItemStack,Double> stackToWeight = new HashMap<ItemStack,Double>();
		
		public Material(){
		}
		public Material(String s){
			this.name = s;
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
			weightToStack.put(weight,stack);
			stackToWeight.put(stack, weight);
			return (T) this;
		}
	}
	
	public static class Electrode extends Material<Electrode>{
		public double potential = 0;
		public List<Double> oxydationNumber;
		public Electrode(String s, double e, List<Double>o){
			super(s);
			this.potential = e;
			this.oxydationNumber = o;
		}
		public double getPotential(){
			return potential;
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
