package noelflantier.bigbattery.common.helpers;

import java.util.Map;
import java.util.Optional;

import com.google.common.base.Predicate;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface B3DIterate<T> {
	/*	
	 * predicate
	 * dpos
	 * upos
	 * pos 
	 */
	abstract Optional<T> checkBlock(Map<String, Object> m, World world);
	default Optional<T> iterateBlocks(Map<String, Object> m, World world){
		BlockPos d = (BlockPos) m.get("dpos");
		BlockPos u = (BlockPos) m.get("upos");
		int inc = m.containsKey("inc") ? (int)m.get("inc") : 1;
		for (int y = d.getY() ; inc >= 0 ? y <= u.getY() : y >= u.getY() ; y += inc ){
			for (int x = d.getX() ; inc >= 0 ? x <= u.getX() : x >= u.getX() ; x += inc ){
				for (int z = d.getZ() ; inc >= 0 ? z <= u.getZ() : z >= u.getZ() ; z += inc ){
					//System.out.println("......................ib "+x+"  "+y+"  "+z);
					m.remove("pos");
					m.put("pos", new BlockPos(x,y,z));
					Optional<T> b = checkBlock(m, world);
					if(b.isPresent())
						return b;
				}
			}
		}
		return Optional.empty();
	};
}
