package noelflantier.bigbattery.common.helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Materials {
	public static Map<Integer,Electrode> electrodeListByPotential= new HashMap<Integer,Electrode>();//desc
	public static Map<Integer,Electrolyte> electrolyteListByPotential= new HashMap<Integer,Electrolyte>();//desc
	public static Map<Integer,Conductive> conductiveListByRatio= new HashMap<Integer,Conductive>();//desc
	
    public static void initList(){
    	int i = 1;
    	electrodeListByPotential.put(i,new Electrode("Iron", 3.27F,new float[]{3F}).addMaterial(Blocks.IRON_BLOCK).addMaterial(new ItemStack(Items.IRON_INGOT), 1).setID(i));
    	i++;
    	electrodeListByPotential.put(i,new Electrode("Gold", -0.1F,new float[]{-4F,-3F,-2F,-1F,0F,1F,2F,3F,4F}).addMaterial(Blocks.GOLD_BLOCK).addMaterial(new ItemStack(Items.GOLD_INGOT), 1).addMaterial(new ItemStack(Items.GOLD_NUGGET), -9).setID(i));
    	i++;
    	electrolyteListByPotential.put(i,new Electrolyte("Book", -3.04F,new float[]{1F}, Electrolyte.Type.IONIC).addMaterial(Blocks.BOOKSHELF,1).setID(i));
    	i++;
    	conductiveListByRatio.put(i,new Conductive("RedStone", 1.1F).setID(i).addMaterial(Blocks.REDSTONE_BLOCK,1));
    	i++;
    	conductiveListByRatio.put(i,new Conductive("Lapiz", 0.9F).setID(i).addMaterial(Blocks.LAPIS_BLOCK,1));
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
    	electrolyteListByPotential.add(i,new Electrolyte("Chlore", 1.35F,new float[]{5F}, Electrolyte.Type.SOLID).setID(i));
    	i++;
    	electrolyteListByPotential.add(i,new Electrolyte("Lithium", -3.04F,new float[]{1F}, Electrolyte.Type.IONIC).setID(i));
    	i++;
    	electrolyteListByPotential.add(i,new Electrolyte("Calcium", -3.8F,new float[]{2F}, Electrolyte.Type.SOLID).setID(i));
    	i++;
    	conductiveListByRatio.add(i,new Conductive("Alluminium", 2.1F).setID(i));
    	i++;
    	conductiveListByRatio.add(i,new Conductive("Copper", 1.2F).setID(i));*/
    }
        
    public static Materials.Electrode getElectrodeFromStack(ItemStack stack){
    	return electrodeListByPotential.entrySet().stream().filter((e)->e.getValue().weightToStack.entrySet().stream().anyMatch((w)->w.getValue().isItemEqualIgnoreDurability(stack))).findFirst().get().getValue();
    }

    public static float getWeightFromStack(Materials.Electrode electrode, ItemStack stack){
    	if(electrode == null || stack == null)
    		return 1;
    	return electrode.weightToStack.entrySet().stream().filter((i)->i.getValue().isItemEqualIgnoreDurability(stack)).findFirst().get().getKey();
    }
    
    public static Materials.Electrolyte getElectrolyteFromStack(ItemStack stack){
    	return electrolyteListByPotential.entrySet().stream().filter((e)->e.getValue().weightToStack.entrySet().stream().anyMatch((w)->w.getValue().isItemEqualIgnoreDurability(stack))).findFirst().get().getValue();
    }

    public static float getWeightFromStack(Materials.Electrolyte electrolyte, ItemStack stack){
    	if(electrolyte == null || stack == null)
    		return 1;
    	return electrolyte.weightToStack.entrySet().stream().filter((i)->i.getValue().isItemEqualIgnoreDurability(stack)).findFirst().get().getKey();
    }
    
    public static Materials.Conductive getConductiveFromStack(ItemStack stack){
    	return conductiveListByRatio.entrySet().stream().filter((e)->e.getValue().weightToStack.entrySet().stream().anyMatch((w)->w.getValue().isItemEqualIgnoreDurability(stack))).findFirst().get().getValue();
    }

    public static float getWeightFromStack(Materials.Conductive conductive, ItemStack stack){
    	if(conductive == null || stack == null)
    		return 1;
    	return conductive.weightToStack.entrySet().stream().filter((i)->i.getValue().isItemEqualIgnoreDurability(stack)).findFirst().get().getKey();
    }
    
    
	public static class Material<T>{
		public String name;
		public int materialID;
		public Map<Float,ItemStack> weightToStack = new HashMap<Float,ItemStack>();
		public Map<ItemStack,Float> stackToWeight = new HashMap<ItemStack,Float>();
		
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
		public T addMaterial(Block block, float weight) {
			addMaterial(new ItemStack(block), weight);
			return (T) this;
		}
		public T addMaterial(String oreDictName, float weight){
			if(OreDictionary.doesOreNameExist(oreDictName)){
				List<ItemStack> l = OreDictionary.getOres(oreDictName);
				if(!l.isEmpty())
					addMaterial(l.get(0), weight);
			}
			return (T) this;
		}
		public T addMaterial(ItemStack stack, float weight){
			weightToStack.put(weight,stack);
			stackToWeight.put(stack, weight);
			return (T) this;
		}
	}
	
	public static class Electrode extends Material<Electrode>{
		public float potential = 0;
		public float [] oxydationNumber;
		public Electrode(String s, float e, float []o){
			super(s);
			this.potential = e;
			this.oxydationNumber = o;
		}
	}

	public static class Electrolyte extends Material<Electrolyte>{
		
		public enum Type {
			AQUEOUS(1.0F,1.0F),
			IONIC(2.5F,1.5F),
			SOLID(1.8F,2.0F);
			public float ratioVoltage = 0;
			public float ratioDecay = 0;
			private Type(float rv, float rd){
				this.ratioVoltage = rv;
				this.ratioDecay = rd;
			}
		}
		
		public float potential = 0;
		public float [] oxydationNumber;
		public Type electrolyteType;
		
		public Electrolyte(String s, float e, float[] o, Type type) {
			super(s);
			this.potential = e;
			this.oxydationNumber = o;
			this.electrolyteType = type;
		}
		
	}	


	public static class Conductive extends Material<Conductive>{
		public float ratioEfficiency = 1F;
		public Conductive(String s,float r) {
			super(s);
			this.ratioEfficiency = r;
		}
		
	}
}
