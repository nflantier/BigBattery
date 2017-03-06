package noelflantier.bigbattery.common.materials;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.oredict.OreDictionary;
import noelflantier.bigbattery.BigBattery;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.common.materials.MaterialsHandler.Material;

public class RecipeParser extends DefaultHandler {

	public static final String ELEMENT_ROOT_MATERIALS = "materials";
	public static final String ELEMENT_ELECTRODE = "electrode";
	public static final String ELEMENT_ELECTROLYTE = "electrolyte";
	public static final String ELEMENT_CONDUCTIVE = "conductive";
	public static final String ELEMENT_ITEM_STACK = "itemStack";
	public static final String ELEMENT_BUCKET_STACK = "bucketStack";
	public static final String ELEMENT_FLUID_STACK = "fluidStack";
	
	public static final String AT_POTENTIAL = "potential";
	public static final String AT_OXYDATIONNO = "oxydationNo";
	public static final String AT_WEIGHT = "weight";
	public static final String AT_TYPE = "type";
	public static final String AT_RATIO_RF = "ratioRf";
	public static final String AT_NAME = "name";
	public static final String AT_ORE_DICT = "oreDictionary";
	public static final String AT_ITEM_DAMAGE = "itemMeta";
	public static final String AT_QUANTITY = "itemQuantity";
	public static final String AT_ITEM_NAME = "itemName";
	public static final String AT_MOD_ID = "modID";
	public static final String AT_AMOUNT = "amount";
	

	public static List<Material> parse(String str) throws Exception {

		StringReader reader = new StringReader(str);
	    InputSource is = new InputSource(reader);
	    try {
	    	return parse(is);
	    } finally {
	    	reader.close();
	    }
	}
	
	public static List<Material> parse(InputSource is) throws Exception {
		RecipeParser parser = new RecipeParser();
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		SAXParser saxParser = spf.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(parser);
		xmlReader.parse(is);

		return parser.result;
	}

	@Override
	public void warning(SAXParseException e) throws SAXException {
		System.out.println("Warning while loading recipes from config : " + e.getMessage());
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		System.out.println("Error while loading recipes from config : " + e.getMessage());
		e.printStackTrace();
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		System.out.println("Error while loading recipes from config : " + e.getMessage());
		e.printStackTrace();
	}

	public List<Material> result;
	public Material currentMaterial;
	public Class currentMaterialClass;
	public ItemStack currentItemStack;
	
	RecipeParser() {
	}

	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//System.out.println("...................................................... START"+localName);
		if(ELEMENT_ROOT_MATERIALS.equals(localName)){
			if(result != null) {
				System.out.println("Multiple materials elements found.");
		    } else {
		    	result = new ArrayList<Material>();
		    }
		    return;
		}
			    
	    if(ELEMENT_ELECTRODE.equals(localName) || ELEMENT_ELECTROLYTE.equals(localName) || ELEMENT_CONDUCTIVE.equals(localName)){
	    	if(result==null){
	    		System.out.println("No materials root element");
	    		return;
	    	}
	    	if(currentMaterial != null) {
	    		System.out.println("Material not closed before encountering a new material.");
	        }

	    	String name = getStringValue(AT_NAME, attributes, null);
	    	if(ELEMENT_ELECTRODE.equals(localName)){
					double p = getDoubleValue(AT_POTENTIAL, attributes, 0);
					List<Double> o = getDoubleListValue(AT_OXYDATIONNO, attributes, 0);
					currentMaterial = new MaterialsHandler.Electrode(name,p,o);
					currentMaterialClass = MaterialsHandler.Electrode.class;
	    	}else if(ELEMENT_ELECTROLYTE.equals(localName)){
					double p = getDoubleValue(AT_POTENTIAL, attributes, 0);
					List<Double> o = getDoubleListValue(AT_OXYDATIONNO, attributes, 0);
					int idt = getIntValue(AT_TYPE, attributes, 0);
					currentMaterial = new MaterialsHandler.Electrolyte(name,p,o, MaterialsHandler.Electrolyte.Type.getTypeFromIndex(idt));
					currentMaterialClass = MaterialsHandler.Electrolyte.class;
	    	}else if(ELEMENT_CONDUCTIVE.equals(localName)){
					double p = getDoubleValue(AT_RATIO_RF, attributes, 0);
					currentMaterial = new MaterialsHandler.Conductive(name,p);
					currentMaterialClass = MaterialsHandler.Conductive.class;
	    	}
	    	return;
	    }
	    
	    if(currentMaterial == null) {
	    	System.out.println("Found element <" + localName + "> with no material decleration.");
	        return;
	    }	    

	    if(ELEMENT_BUCKET_STACK.equals(localName)){
	    	Fluid f = getFluid(attributes);
	    	if(f==null){
		        System.out.println("Could not find the fluid for the bucketstack");
		        return;
	    	}
	    	ItemStack st = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, f);
        	if(st!=null)
        		currentMaterial.addMaterial(st, getItemStackWeight(attributes));
		    
	    }
	    
	    boolean isFluidStack = ELEMENT_FLUID_STACK.equals(localName);
	    if(ELEMENT_ITEM_STACK.equals(localName)) {
        	ItemStack st = getItemStack(attributes);
        	if(st!=null){
        		currentMaterial.addMaterial(st, getItemStackWeight(attributes));
        	}
	        return;
	    }
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(ELEMENT_ROOT_MATERIALS.equals(localName)){
	    	currentMaterial = null;
	    	currentMaterialClass = null;
		    return;
		}

	    if(ELEMENT_ELECTRODE.equals(localName) || ELEMENT_ELECTROLYTE.equals(localName) || ELEMENT_CONDUCTIVE.equals(localName)){
	        if(currentMaterial != null) {
	        	result.add(currentMaterial);
	        } else {
	        	System.out.println("Could not add current material to group root");
	        }
	        currentMaterial = null;
	    	currentMaterialClass = null;
	        return;
	    }
	}
	
	public Fluid getFluid(Attributes attributes){
	    String name = getStringValue(AT_ITEM_NAME, attributes, null);
		return FluidRegistry.getFluid(name.toLowerCase());
	}
	
	public FluidStack getFluidStack(Attributes attributes){
		int amount = getIntValue(AT_AMOUNT, attributes, 1000);
	    String name = getStringValue(AT_ITEM_NAME, attributes, null);
	    String modid = getStringValue(AT_MOD_ID, attributes, null);
	    if(name == null) {
	    	return null;
	    }
	    Fluid fluid = FluidRegistry.getFluid(name);
	    if(fluid == null) {
	    	System.out.println("When parsing recipes could not find fluid with name: "+ name);
	    	return null;
	    }
		return new FluidStack(fluid, amount);
	}
	
	public ItemStack getItemStack(Attributes attributes){
	    ItemStack stack = null;
	    int stackSize = getIntValue(AT_QUANTITY, attributes, 1);
	    int itemMeta = getIntValue(AT_ITEM_DAMAGE, attributes, 0);
	    String modId = getStringValue(AT_MOD_ID, attributes, null);
	    String name = getStringValue(AT_ITEM_NAME, attributes, null);
	    String orename = getStringValue(AT_ORE_DICT, attributes, null);
    	
	    if(orename!=null && OreDictionary.doesOreNameExist(orename)){
	    	List<ItemStack> ores = OreDictionary.getOres(orename);
	        if(!ores.isEmpty() && ores.get(0) != null) {
	        	stack = ores.get(0).copy();
	        	if(stack!=null){
	        		stack.setCount(stackSize);
	        		stack.setItemDamage(itemMeta);
	        	}
	        }
	    }
	    
	    if(modId != null && name != null) {
	    	Item i = Item.REGISTRY.getObject(new ResourceLocation(modId,name));
	    	//Item i = GameRegistry.findItem(modId, name);
	    	if(i != null) {
	    		stack = new ItemStack(i, stackSize,itemMeta);
	    	} else {
	    		Block b = Block.REGISTRY.getObject(new ResourceLocation(modId,name));
	    		//Block b = GameRegistry.findBlock(modId, name);
	    		if(b != null) {
	    			stack = new ItemStack(b, stackSize, itemMeta);
	    		}
	    	}	
	    }
	    if(stack == null) {
	    	System.out.println("Could not create an item stack");
	        return null;
	    }
		return stack;
	}


	private double getItemStackWeight(Attributes attributes) {
		return getDoubleValue(AT_WEIGHT, attributes, 1);
	}
	
	public static boolean getBooleanValue(String qName, Attributes attributes, boolean def) {
		String val = attributes.getValue(qName);
	    if(val == null) {
	    	return def;
	    }
	    val = val.toLowerCase().trim();
	    return val.equals("false") ? false : val.equals("true") ? true : def;
	}

	public static int getIntValue(String qName, Attributes attributes, int def) {
		try {
			return Integer.parseInt(getStringValue(qName, attributes, def + ""));
		} catch (Exception e) {
			System.out.println("Could not parse a valid int for attribute " + qName + " with value " + getStringValue(qName, attributes, null));
			return def;
		}
	}

	public static float getFloatValue(String qName, Attributes attributes, float def) {
		try {
			return Float.parseFloat(getStringValue(qName, attributes, def + ""));
		} catch (Exception e) {
			System.out.println("Could not parse a valid float for attribute " + qName + " with value " + getStringValue(qName, attributes, null));
			return def;
		}
	}
	public static double getDoubleValue(String qName, Attributes attributes, double def) {
		try {
			return Double.parseDouble(getStringValue(qName, attributes, def + ""));
		} catch (Exception e) {
			System.out.println("Could not parse a valid float for attribute " + qName + " with value " + getStringValue(qName, attributes, null));
			return def;
		}
	}
	public static List<Double> getDoubleListValue(String qName, Attributes attributes, double def) {
		try {
			return Arrays.asList(getStringValue(qName, attributes, def + "").split(",")).stream().map(str->Double.parseDouble(str)).collect(Collectors.toList());
			/*
			List<String> s = Arrays.asList(getStringValue(qName, attributes, def + "").split(","));
			List<Float> f = s.stream().map(st->Float.parseFloat(st)).collect(Collectors.toList());
			return ArrayUtils.toPrimitive(f.toArray(new Float[0]));
			*/
		} catch (Exception e) {
			System.out.println("Could not parse a valid float for attribute " + qName + " with value " + getStringValue(qName, attributes, null));
			return new ArrayList<Double>(){{add(def);}};
		}
	}
	
	public static String getStringValue(String qName, Attributes attributes, String def) {
		String val = attributes.getValue(qName);
		if(val == null) {
			return def;
		}
		val = val.trim();
		if(val.length() <= 0) {
			return null;
		}
		return val;
	}
}
