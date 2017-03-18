package noelflantier.bigbattery.common.handlers;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;

public class ModProperties {
	
    public enum InterfaceType implements IStringSerializable{
    	ELECTRODE("electrode"),
    	ELECTROLYTE("electrolyte");

    	public String name;
    	
    	private InterfaceType(String name) {
    		this.name = name;
		}
    	
		@Override
		public String getName() {
			return name;
		}
		
		public static InterfaceType getType(int idx){
			return idx >= InterfaceType.values().length ? InterfaceType.values()[0] : InterfaceType.values()[idx];
		}
    }
    
	public static class PropertyInterfaceType extends PropertyEnum<InterfaceType>{

		protected PropertyInterfaceType(String name, Collection<InterfaceType> allowedValues) {
			super(name, InterfaceType.class, allowedValues);
		}
		public static PropertyInterfaceType create(String name)
	    {
	        return create(name, Predicates.<InterfaceType>alwaysTrue());
	    }
	    public static PropertyInterfaceType create(String name, Predicate<InterfaceType> filter)
	    {
	        return create(name, Collections2.<InterfaceType>filter(Lists.newArrayList(InterfaceType.values()), filter));
	    }
	    public static PropertyInterfaceType create(String name, Collection<InterfaceType> values)
	    {
	        return new PropertyInterfaceType(name, values);
	    }
	}
	
    public enum ConductiveType implements IStringSerializable{
    	ALLUMINIUM("aluminium"),
    	COPPER("copper"),
    	EMERALD("emerald"),
    	NETHERSTAR("netherstar");

    	public String name;
    	
    	private ConductiveType(String name) {
    		this.name = name;
		}
    	
		@Override
		public String getName() {
			return name;
		}
		
		public static ConductiveType getType(int idx){
			return idx >= ConductiveType.values().length ? ConductiveType.values()[0] : ConductiveType.values()[idx];
		}
    }
    
	public static class PropertyConductiveType extends PropertyEnum<ConductiveType>{

		protected PropertyConductiveType(String name, Collection<ConductiveType> allowedValues) {
			super(name, ConductiveType.class, allowedValues);
		}
		public static PropertyConductiveType create(String name)
	    {
	        return create(name, Predicates.<ConductiveType>alwaysTrue());
	    }
	    public static PropertyConductiveType create(String name, Predicate<ConductiveType> filter)
	    {
	        return create(name, Collections2.<ConductiveType>filter(Lists.newArrayList(ConductiveType.values()), filter));
	    }
	    public static PropertyConductiveType create(String name, Collection<ConductiveType> values)
	    {
	        return new PropertyConductiveType(name, values);
	    }
	}
    
    public enum CasingType implements IStringSerializable{
    	GLASS("glass"),
    	IRON("iron");

    	public String name;
    	
    	private CasingType(String name) {
    		this.name = name;
		}
    	
		@Override
		public String getName() {
			return name;
		}
		
		public static CasingType getType(int idx){
			return idx>=CasingType.values().length ? CasingType.values()[0] : CasingType.values()[idx];
		}
    }
    
	public static class PropertyCasingType extends PropertyEnum<CasingType>{

		protected PropertyCasingType(String name, Collection<CasingType> allowedValues) {
			super(name, CasingType.class, allowedValues);
		}
		public static PropertyCasingType create(String name)
	    {
	        return create(name, Predicates.<CasingType>alwaysTrue());
	    }
	    public static PropertyCasingType create(String name, Predicate<CasingType> filter)
	    {
	        return create(name, Collections2.<CasingType>filter(Lists.newArrayList(CasingType.values()), filter));
	    }
	    public static PropertyCasingType create(String name, Collection<CasingType> values)
	    {
	        return new PropertyCasingType(name, values);
	    }
	}
}
