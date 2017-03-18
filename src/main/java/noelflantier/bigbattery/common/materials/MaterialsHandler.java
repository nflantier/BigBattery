package noelflantier.bigbattery.common.materials;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import scala.actors.threadpool.Arrays;

public class MaterialsHandler{
	public static Map<Integer,Electrode> electrodeListByPotential= new HashMap<Integer,Electrode>();//desc ID,Electrode
	public static Map<Integer,Electrolyte> electrolyteListByPotential= new HashMap<Integer,Electrolyte>();//desc ID,Electrolyte
	public static Map<Integer,Conductive> conductiveListByRatio= new HashMap<Integer,Conductive>();//desc ID,Conductive
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
	    	System.out.println("..................................... "+electrodeListByPotential.size());
	    	System.out.println("..................................... "+electrolyteListByPotential.size());
	    	System.out.println("..................................... "+conductiveListByRatio.size());*/
	    } else {
	    	System.out.println("Could not load materials config ");
	    }
	}
	
	public static boolean areItemStackSameOre(ItemStack it1, ItemStack it2, boolean strict){
		if(it1.isItemEqualIgnoreDurability(it2))
			return true;
		if(strict)
			return false;
        if (it1.isEmpty() || it2.isEmpty())
            return false;
        
		int [] it1t = OreDictionary.getOreIDs(it1);
		int [] it2t = OreDictionary.getOreIDs(it2);
		if(it1t.length <= 0 || it2t.length <= 0)
			return false;
		return Arrays.asList(ArrayUtils.toObject(it1t)).stream().anyMatch(o->Arrays.asList(ArrayUtils.toObject(it2t)).stream().anyMatch(i->o==i));
	}
	
	public static boolean isItemStackEnough(ItemStack reference, ItemStack candidat){
		return candidat.getCount() >= reference.getCount();
	}
	
	public static boolean isListItemStackSameOre(List<ItemStack> list, ItemStack it, boolean strict){
		return list.stream().anyMatch((i)->areItemStackSameOre(i, it, strict));
	}

	public static boolean isListItemStackSameOreAndSize(List<ItemStack> list, ItemStack it, boolean strict){
		return list.stream().anyMatch((i)->areItemStackSameOre(i, it, strict) && isItemStackEnough(i,it));
	}
	
    public static MaterialsHandler.Electrolyte getElectrolyteFromStack(FluidStack stack){
    	return electrolyteListByPotential.entrySet().stream().filter((e)->e.getValue().fluidStack.stream().anyMatch((w)->w.equals(stack))).findFirst().get().getValue();
    }
    
    public static int getFluidAmountFromStack(MaterialsHandler.Electrolyte electrolyte, FluidStack stack){
    	if(electrolyte == null || stack == null)
    		return 0;
    	return electrolyte.fluidStack.stream().filter((i)->i.equals(stack)).findFirst().get().amount;
    }
    
	public static MaterialsHandler.Electrode getElectrodeFromStack(ItemStack stack){
    	Optional<Entry<Integer, Electrode>> r =  electrodeListByPotential.entrySet().stream().filter((e)->e.getValue().listStack.stream().anyMatch((w)->areItemStackSameOre(w, stack, false))).findFirst();
    	return r.isPresent() ? r.get().getValue() : null;
    }

    public static Map.Entry<ItemStack,Double> getStackAndWeightFromStack(MaterialsHandler.Electrode electrode, ItemStack stack){
    	if(electrode == null || stack == null)
    		return null;
    	return electrode.stackToWeight.entrySet().stream().filter((i)->areItemStackSameOre(i.getKey(), stack, false)).findFirst().get();
    }
    
    public static double getWeightFromStack(MaterialsHandler.Electrode electrode, ItemStack stack){
    	if(electrode == null || stack == null)
    		return 1;
    	return electrode.stackToWeight.entrySet().stream().filter((i)->areItemStackSameOre(i.getKey(), stack, false)).findFirst().get().getValue();
    }
    
    public static MaterialsHandler.Electrolyte getElectrolyteFromStack(ItemStack stack){
    	Optional<Entry<Integer, Electrolyte>> r =  electrolyteListByPotential.entrySet().stream().filter((e)->e.getValue().listStack.stream().anyMatch((w)->areItemStackSameOre(w, stack, false))).findFirst();
    	return r.isPresent() ? r.get().getValue() : null;
    }

    public static Map.Entry<ItemStack,Double> getStackAndWeightFromStack(MaterialsHandler.Electrolyte electrolyte, ItemStack stack){
    	if(electrolyte == null || stack == null)
    		return null;
    	return electrolyte.stackToWeight.entrySet().stream().filter((i)->areItemStackSameOre(i.getKey(), stack, false)).findFirst().get();
    }
    
    public static double getWeightFromStack(MaterialsHandler.Electrolyte electrolyte, ItemStack stack){
    	if(electrolyte == null || stack == null)
    		return 1;
    	return electrolyte.stackToWeight.entrySet().stream().filter((i)->areItemStackSameOre(i.getKey(), stack, false)).findFirst().get().getValue();
    }
    
    public static MaterialsHandler.Conductive getConductiveFromStack(ItemStack stack){
    	Optional<Entry<Integer, Conductive>> r =  conductiveListByRatio.entrySet().stream().filter((e)->e.getValue().listStack.stream().anyMatch((w)->areItemStackSameOre(w, stack, false))).findFirst();
    	return r.isPresent() ? r.get().getValue() : null;
    }

    public static double getWeightFromStack(MaterialsHandler.Conductive conductive, ItemStack stack){
    	if(conductive == null || stack == null)
    		return 1;
    	return conductive.stackToWeight.entrySet().stream().filter((i)->areItemStackSameOre(i.getKey(), stack, false)).findFirst().get().getValue();
    }
    
    public static boolean anyMatchConductive(ItemStack stack){
    	return conductiveListByRatio.entrySet().stream().anyMatch(e->isListItemStackSameOre(e.getValue().listStack, stack, false));
    }
    public static boolean anyMatchElectrodeAndSize(ItemStack stack){
    	return electrodeListByPotential.entrySet().stream().anyMatch(e->isListItemStackSameOreAndSize(e.getValue().listStack, stack, false));
    }
    public static boolean anyMatchElectrolyteAndSize(ItemStack stack){
    	return electrolyteListByPotential.entrySet().stream().anyMatch(e->isListItemStackSameOreAndSize(e.getValue().listStack, stack, false));
    }
    public static boolean anyMatchElectrode(ItemStack stack){
    	return electrodeListByPotential.entrySet().stream().anyMatch(e->isListItemStackSameOre(e.getValue().listStack, stack, false));
    }
    public static boolean anyMatchElectrolyte(ItemStack stack){
    	return electrolyteListByPotential.entrySet().stream().anyMatch(e->isListItemStackSameOre(e.getValue().listStack, stack, false));
    }
    public static boolean anyMatchElectrolyte(FluidStack stack){
    	return electrolyteListByPotential.entrySet().stream().anyMatch(e->e.getValue().fluidStack.stream().anyMatch((l)->l.equals(stack)));
    }
    
	public static class Material<T>{
		public static int IDMATERIALS = 1;
		public static int getNextIdMaterials(){return IDMATERIALS++;}
		
		public String name;
		public int materialID;
		//public Map<Double,ItemStack> weightToStack = new HashMap<Double,ItemStack>();
		public ItemStack stackReference = ItemStack.EMPTY;
		public Map<ItemStack,Double> stackToWeight = new HashMap<ItemStack,Double>();
		public List<ItemStack> listStack = new ArrayList<ItemStack>();
		public List<FluidStack> fluidStack = new ArrayList<FluidStack>();
		
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
		public T addMaterial(FluidStack stack, double weight){
			fluidStack.add(stack.copy());
			return (T) this;
		}
		public T addMaterial(ItemStack stack, double weight){
			if(weight==1.0D)
				stackReference = stack.copy();
			if(!stackToWeight.containsKey(stack))
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
			IONIC(2.5F,2.0D),
			SOLID(1.5F,3.0D);
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
