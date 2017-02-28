package noelflantier.bigbattery.common.handlers;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;

public class ModProperties {
    
    public enum CasingType implements IStringSerializable{
    	BASIC("basic"),
    	ADVANCED("advanced");

    	public String name;
    	
    	private CasingType(String name) {
    		this.name = name;
		}
    	
		@Override
		public String getName() {
			return name;
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
