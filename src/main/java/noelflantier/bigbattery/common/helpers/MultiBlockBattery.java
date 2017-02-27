package noelflantier.bigbattery.common.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import noelflantier.bigbattery.common.blocks.ABlockBBStructure;
import noelflantier.bigbattery.common.blocks.BlockPlug;
import noelflantier.bigbattery.common.handlers.ModBlocks;
import noelflantier.bigbattery.common.tiles.ITileHaveMaster;
import noelflantier.bigbattery.common.tiles.ITileMaster;

public class MultiBlockBattery {

	public boolean init = false;
	public boolean isValid = false;
	public boolean isStructured = false;
	public boolean isManual = true;
	
	public BlockPos plugPos;
	public EnumFacing plugFacing;
	
	public ItemStack electrolyteItemStack;
	public EnumFacing electrolyteFacing;
	public BlockPos dwnElectrolyte;
	public BlockPos uesElectrolyte;
	public int amountElectrolyte;
	
	public BlockPos interfaceElectrolyte;
	public BlockPos interfaceAnode;
	public BlockPos interfaceCathode;
	
	public int maxRange = 200;
	public Map<EnumFacing, ItemStack> mapFacingConductiveItemStack= new HashMap<EnumFacing, ItemStack>();
	public Map<EnumFacing, ItemStack> mapFacingMaterialItemStack= new HashMap<EnumFacing, ItemStack>();
	public Map<EnumFacing, Integer> mapFacingMaterialAmount= new HashMap<EnumFacing, Integer>();
	public Map<EnumFacing, B3D> mapFacingMaterialB3D= new HashMap<EnumFacing, B3D>();
	public BlockPos dwn;
	public BlockPos ues;
	
    public static List<Block> allowedStructureBlock= new ArrayList<Block>();
    public static List<ItemStack> allowedConductiveBlock= new ArrayList<ItemStack>();
    public static List<ItemStack> allowedElectrolyteBlock= new ArrayList<ItemStack>();
    public static List<ItemStack> allowedMaterialsBlock= new ArrayList<ItemStack>();
    
    public static List<Block> allowedAutomaticBlock= new ArrayList<Block>();//other structure blocks that are not in this list are manual
    
    public MaterialsBattery materialsB = new MaterialsBattery();
    
    static{
    	allowedMaterialsBlock.add(new ItemStack(Blocks.IRON_BLOCK));
    	allowedMaterialsBlock.add(new ItemStack(Blocks.GOLD_BLOCK));
    	//allowedMaterialsBlock BLOCK AIR
    	allowedConductiveBlock.add(new ItemStack(Blocks.REDSTONE_BLOCK));
    	allowedConductiveBlock.add(new ItemStack(Blocks.LAPIS_BLOCK));
    	
    	allowedElectrolyteBlock.add(new ItemStack(Blocks.BOOKSHELF));
    	//allowedElectrolyteBlock BLOCK AIR
    	
    	allowedStructureBlock.add(ModBlocks.blockCasing);
    	allowedStructureBlock.add(ModBlocks.blockPlug);
    	allowedStructureBlock.add(Blocks.ANVIL);
    	
    	allowedAutomaticBlock.add(Blocks.ANVIL);
    }
	
	public MultiBlockBattery(){
		
	}
	
	public class B3D{
		public BlockPos dwn;
		public BlockPos ues;
		public Random brdm = new Random();
		
		public B3D(BlockPos d, BlockPos u){
			dwn = d;
			ues = u;
		}
	    public B3D(NBTTagCompound nbt) {
	    	if(nbt!=null)
	    		readFromNBT(nbt);
		}
	    public B3D() {
		}

	    public BlockPos getRandomBlock(){
	    	int sx = Math.abs(ues.getX() - dwn.getX());
	    	int sy = Math.abs(ues.getY() - dwn.getY());
	    	int sz = Math.abs(ues.getZ() - dwn.getZ());
	    	
	    	int rx = sx > 0 ? brdm.nextInt(sx) + dwn.getX() : dwn.getX();
	    	int ry = sy > 0 ? brdm.nextInt(sy) + dwn.getY() : dwn.getY();
	    	int rz = sz > 0 ? brdm.nextInt(sz) + dwn.getZ() : dwn.getZ();
	    	return new BlockPos(rx, ry, rz);
	    }

	    public BlockPos getNextMaterialBlock(World world){
			for (int x = dwn.getX() ; x <= ues.getX() ; x++ ){
				for (int y = dwn.getY() ; y <= ues.getY() ; y++ ){
					for (int z = dwn.getZ() ; z <= ues.getZ() ; z++ ){
						BlockPos p = new BlockPos(x,y,z);
						IBlockState s = world.getBlockState(p);
						if(s.getBlock()!= Blocks.AIR){
							return p;
						}
					}
				}
			}
	    	return null;
	    }
	    
		public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
	    	if(dwn!=null){
		        nbt.setInteger("dwnx", dwn.getX());
		        nbt.setInteger("dwny", dwn.getY());
		        nbt.setInteger("dwnz", dwn.getZ());
	    	}
	    	if(ues!=null){
		        nbt.setInteger("uesx", ues.getX());
		        nbt.setInteger("uesy", ues.getY());
		        nbt.setInteger("uesz", ues.getZ());
	    	}
	        return nbt;
	    }
	    
	    public void readFromNBT(NBTTagCompound nbt) {
	        dwn = new BlockPos(nbt.getInteger("dwnx"),nbt.getInteger("dwny"),nbt.getInteger("dwnz"));	
	        ues = new BlockPos(nbt.getInteger("uesx"),nbt.getInteger("uesy"),nbt.getInteger("uesz"));
	    }
		
	}
	
	public class MaterialPart{

		public float weight = 1;
		public double totalUnit = 0;//~tick time per block
		public double currentUnit = 0;
		public int totalAmount = 0;//nombre block
		public B3D materialLimit = new B3D();
		
		public MaterialPart(){}
		
	    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
	        nbt.setFloat("weight", weight);
	        nbt.setDouble("totalUnit", totalUnit);
	        nbt.setDouble("currentUnit", currentUnit);
	        nbt.setInteger("totalAmount", totalAmount);
	        materialLimit.writeToNBT(nbt);
	        return nbt;
	    }
	    
	    public void readFromNBT(NBTTagCompound nbt) {
	    	weight = nbt.getFloat("weight");
	    	totalUnit = nbt.getDouble("totalUnit");
	    	currentUnit = nbt.getDouble("currentUnit");
	    	totalAmount = nbt.getInteger("totalAmount");
	    	materialLimit.readFromNBT(nbt);
	    }

		public void reset() {
			weight = 1;
			totalUnit = 0;
			currentUnit = 0;
			totalAmount = 0;
		}
	}
	
	public class MaterialsBattery{
	
		public Materials.Electrode anode;
		public Materials.Conductive anodeCond;
		public MaterialPart anodeMP = new MaterialPart();
		
		public Materials.Electrode cathode;
		public Materials.Conductive cathodeCond;
		public MaterialPart cathodeMP = new MaterialPart();
		
		public Materials.Electrolyte electrolyte;
		public MaterialPart electrolyteMP = new MaterialPart();
		
		public float potentialDifference = 0;
		public int baseAmount = 0;
		public Random rdm  = new Random();
		
		public MaterialsBattery(){
			
		}

		public boolean useMaterial(World world, BlockPos pos){
			if(pos == null)
				return false;
			IBlockState state = world.getBlockState(pos);
			if(state.getBlock() != Blocks.AIR){
				world.setBlockToAir(pos);
				return true;
			}
			return false;
		}
		
		public void handleMaterials(World world, float rationRF){
			if(anode == null || cathode == null || electrolyte == null)
				return ;
			
			if(anodeMP.currentUnit<=0){
				anodeMP.currentUnit	= anodeMP.totalUnit;
				if( anodeMP.totalAmount <= 0){
					anode = null;
					anodeMP.reset();
				}
				anodeMP.totalAmount-=1;
				//if( !useMaterial(world, anodeMP.materialLimit.getRandomBlock()) )
					//useMaterial(world,anodeMP.materialLimit.getNextMaterialBlock(world));
			}
			if(cathodeMP.currentUnit<=0){
				cathodeMP.currentUnit = cathodeMP.totalUnit;
				if( cathodeMP.totalAmount <= 0){
					cathode = null;
					cathodeMP.reset();
				}
				cathodeMP.totalAmount-=1;
				//if( !useMaterial(world, cathodeMP.materialLimit.getRandomBlock()) )
					//useMaterial(world,cathodeMP.materialLimit.getNextMaterialBlock(world));
				
			}
			if(electrolyteMP.currentUnit<=0){
				electrolyteMP.currentUnit = electrolyteMP.totalUnit;
				if( electrolyteMP.totalAmount <= 0){
					electrolyte = null;
					electrolyteMP.reset();
				}
				electrolyteMP.totalAmount-=1;
				//if( !useMaterial(world, electrolyteMP.materialLimit.getRandomBlock()) )
					//useMaterial(world,electrolyteMP.materialLimit.getNextMaterialBlock(world));
			}
			
			anodeMP.currentUnit -= ( rdm.nextDouble() + 1 ) * rationRF +1;
			cathodeMP.currentUnit -= ( rdm.nextDouble() + 1 ) * rationRF +1;
			electrolyteMP.currentUnit -= ( rdm.nextDouble() + 1 ) * rationRF +1;
			
			System.out.println("ano "+anodeMP.currentUnit);
			System.out.println("cath "+cathodeMP.currentUnit);
			System.out.println("ele "+electrolyteMP.currentUnit);
		}
		
		public double generateEnergy(){
			if(anode == null || cathode == null || electrolyte == null)
				return 0;
			//decay electrode per tick : oxydation number
			//decay electrolyte per tick : oxydation number plut type de ratio decay
			//rf per tick potential difference, lower electrolyte potential more rf, electrolyte type ratiovoltage for one block
			//multiply by number of blocks
			//less electrolyte = less rf per tick
			//conductive lose rf per tick : ratioEfficiency
			return potentialDifference * Math.pow(Math.abs(electrolyte.potential) , 1.08) * Math.pow(electrolyte.electrolyteType.ratioVoltage,1.08) * Math.pow(anodeMP.totalAmount + cathodeMP.totalAmount+1, 1.18);
		}
		
		public void setEnergecticValues(){
			if(anode == null || cathode == null || electrolyte == null)
				return;
			
			potentialDifference = Math.abs(anode.potential - cathode.potential);
			int anodeOxydationNo = anode.oxydationNumber.length;
			int cathodeOxydationNo = cathode.oxydationNumber.length;
			int electrolyteOxydationNo = electrolyte.oxydationNumber.length;
			baseAmount = anodeMP.totalAmount + cathodeMP.totalAmount;
			anodeMP.totalUnit = Math.floor( anodeMP.weight * ( Math.pow(anodeOxydationNo * 1.5,1.5) * 100) );
			cathodeMP.totalUnit = Math.floor( cathodeMP.weight * ( Math.pow(cathodeOxydationNo * 1.5,1.5) * 100) );
			electrolyteMP.totalUnit= Math.floor( electrolyteMP.weight * ( Math.pow(electrolyteOxydationNo * 1.5,electrolyte.electrolyteType.ratioDecay) * 100) );
		}
		
		public MaterialsBattery setCathodeAndConductive(ItemStack cathodeStack, ItemStack conductive, int amount){
			cathode = Materials.getElectrodeFromStack(cathodeStack);
			cathodeCond = Materials.getConductiveFromStack(conductive);
			cathodeMP.weight = Materials.getWeightFromStack(cathode, cathodeStack);
			cathodeMP.totalAmount = amount;
			return this;
		}
		public MaterialsBattery setAnodeAndConductive(ItemStack anodeStack, ItemStack conductive, int amount){
			anode = Materials.getElectrodeFromStack(anodeStack);
			anodeCond = Materials.getConductiveFromStack(conductive);
			anodeMP.weight = Materials.getWeightFromStack(anode, anodeStack);
			anodeMP.totalAmount = amount;
			return this;
		}

		public MaterialsBattery setElectrolyte(ItemStack electrolyteStack, int amount){
			electrolyte = Materials.getElectrolyteFromStack(electrolyteStack);
			electrolyteMP.totalAmount = amount;
			return this;
		}
		
	    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
	    	NBTTagCompound t = new NBTTagCompound();
	        t.setInteger("anode", anode != null ? anode.materialID : 0);
	        t.setInteger("anodeCond", anodeCond != null ? anodeCond.materialID : 0);
	        t.setTag("anodeMP", anodeMP.writeToNBT(new NBTTagCompound()));
	        
	        t.setInteger("cathode", cathode != null ? cathode.materialID : 0);
	        t.setInteger("cathodeCond", cathodeCond != null ? cathodeCond.materialID : 0);
	        t.setTag("cathodeMP", cathodeMP.writeToNBT(new NBTTagCompound()));
	        
	        t.setInteger("electrolyte", electrolyte != null ? electrolyte.materialID : 0);
	        t.setTag("electrolyteMP", electrolyteMP.writeToNBT(new NBTTagCompound()));

	        t.setFloat("potentialDifference", potentialDifference);
	        t.setInteger("baseAmount", baseAmount);
	        nbt.setTag("materialbattery", t);
	        
	        return nbt;
	    }
	    
	    public void readFromNBT(NBTTagCompound nbt) {
	    	NBTTagCompound b = (NBTTagCompound) nbt.getTag("materialbattery");
	    	if(b!=null){
		    	int anodeid = b.getInteger("anode");
		    	anode = anodeid >= 0 ? Materials.electrodeListByPotential.get(anodeid) : null;
		    	int anodeCondid = b.getInteger("anodeCond");
		    	anodeCond = anodeCondid >= 0 ? Materials.conductiveListByRatio.get(anodeCondid) : null;
		    	anodeMP.readFromNBT((NBTTagCompound) b.getTag("anodeMP"));

		    	int cathodeid = b.getInteger("cathode");
		    	cathode = cathodeid >= 0 ? Materials.electrodeListByPotential.get(cathodeid) : null;
		    	int cathodeCondid = b.getInteger("cathodeCond");
		    	cathodeCond = cathodeCondid >= 0 ? Materials.conductiveListByRatio.get(cathodeCondid) : null;
		    	cathodeMP.readFromNBT((NBTTagCompound) b.getTag("cathodeMP"));
	    		
		    	int electrolyteid = b.getInteger("electrolyte");
		    	electrolyte = electrolyteid >= 0 ? Materials.electrolyteListByPotential.get(electrolyteid) : null;
		    	electrolyteMP.readFromNBT((NBTTagCompound) b.getTag("electrolyteMP"));
		    	
		    	potentialDifference = b.getFloat("potentialDifference");
		    	baseAmount = b.getInteger("baseAmount");
	    	}
	    }
		
	}
	
	public void init(BlockPos plugPos, IBlockState plugState){
		this.init = true;
		this.plugPos = plugPos;
		this.plugFacing = plugState.getValue(BlockPlug.FACING).getOpposite();
	}
		
	private BlockPos findSide(BlockPos currentPos, EnumFacing direction, World world, int range, boolean checkElectrolyteFacing, boolean allowAir){
		range++;
		if(range>maxRange)
			return null;
		BlockPos nextPos = currentPos.add(direction.getDirectionVec());
		IBlockState currentState = world.getBlockState(currentPos);
		IBlockState nextState = world.getBlockState(nextPos);
		ItemStack nextits = new ItemStack(nextState.getBlock());

		if(allowedConductiveBlock.stream().anyMatch((l)->l.isItemEqualIgnoreDurability(nextits))){
			if(!mapFacingConductiveItemStack.containsKey(direction))
				mapFacingConductiveItemStack.put(direction, nextits);
			return findSide(nextPos, direction, world, range, checkElectrolyteFacing, allowAir);
		}else{
			if(checkElectrolyteFacing && electrolyteFacing == null && range == 1){
				electrolyteFacing = direction;
			}
		}
		if(allowedElectrolyteBlock.stream().anyMatch((l)->l.isItemEqualIgnoreDurability(nextits)))
			return findSide(nextPos, direction, world, range, checkElectrolyteFacing, allowAir);
		if(allowAir && nextState.getBlock() == Blocks.AIR){
			return findSide(nextPos, direction, world, range, checkElectrolyteFacing, allowAir);
		}
		if(allowedStructureBlock.contains(nextState.getBlock())){
			allowAir = false;
			if(nextState.getBlock() instanceof ABlockBBStructure == true && nextState.getValue(ABlockBBStructure.ISSTRUCT) == true)
				return currentPos;
			return findSide(nextPos, direction, world, range, checkElectrolyteFacing, allowAir);
		}else if(allowedStructureBlock.contains(currentState.getBlock())){
			return currentPos;
		}
		return null;
	}
		
	public boolean batteryCheckAndSetupStructure(World world, BlockPos ppos, @Nullable EntityPlayer player){
		init(ppos, world.getBlockState(ppos));
		if(!init)
			return false;
		
		if(!isStructured){
			reset();

			EnumFacing dummyFacing = plugFacing;
			
			BlockPos l5 = findSide(plugPos, dummyFacing, world, 0, false, true);
			
			dummyFacing = dummyFacing.getAxis().isVertical() ? dummyFacing.rotateAround(EnumFacing.Axis.X) : dummyFacing.rotateY();
			BlockPos l1 = findSide(plugPos, dummyFacing, world, 0, true, false);
			BlockPos l2 = findSide(plugPos, dummyFacing.getOpposite(), world, 0, true, false);
	
			dummyFacing = dummyFacing.rotateAround(plugFacing.getAxis());

			BlockPos l3 = findSide(plugPos, dummyFacing, world, 0, true, false);
			BlockPos l4 = findSide(plugPos, dummyFacing.getOpposite(), world, 0, true, false);
			if(l1 == null || l2 == null || l3 == null || l4 == null || l5 == null || mapFacingConductiveItemStack.size()!=2 ){
				reset();
				return false;
			}
			
			List<Integer> lx = new ArrayList<Integer>();
			Collections.addAll(lx,l1.getX(),l2.getX(),l3.getX(),l4.getX(),l5.getX());
			List<Integer> ly = new ArrayList<Integer>();
			Collections.addAll(ly,l1.getY(),l2.getY(),l3.getY(),l4.getY(),l5.getY());
			List<Integer> lz = new ArrayList<Integer>();
			Collections.addAll(lz,l1.getZ(),l2.getZ(),l3.getZ(),l4.getZ(),l5.getZ());

			dwn = new BlockPos(Collections.min(lx),Collections.min(ly),Collections.min(lz));
			ues = new BlockPos(Collections.max(lx),Collections.max(ly),Collections.max(lz));
		}

		boolean chst = checkStructure(world);
		boolean chco = isStructured ? true : checkConductive(world);
		boolean chel = isStructured ? true : isManual ? checkElectrolyte(world) : interfaceElectrolyte != null ? true : checkElectrolyte(world);
		boolean chma = isStructured ? true : isManual ? checkMaterials(world) : interfaceAnode != null && interfaceCathode != null ? true : checkMaterials(world);

		System.out.println(chst);
		System.out.println(chco);
		System.out.println(chel);
		System.out.println(chma);
		isValid = chst && chco && chel && chma;
		
		if(!isValid){
			if(isStructured){
				isStructured = false;
				unsetupStructure(world);
			}
			reset();
			return false;
		}
		
		//SETUP
		setupStructure(world);
		if(!isStructured){
			reset();
			return false;
		}

		setupMaterials(world);
		TileEntity te = world.getTileEntity(plugPos);
		if( te != null && te instanceof ITileMaster){
			((ITileMaster)te).setEnergyCapacity();
		}
		
		return isStructured;
	}
	
	public boolean updateMaterials(){
		
		//checkmaterials
		//setupMaterials(world);
		
		return true;
	}
	
	private void setupMaterials(World world){
		if( mapFacingConductiveItemStack.isEmpty() || mapFacingMaterialAmount.isEmpty() || mapFacingMaterialB3D.isEmpty())
			return;
		ItemStack stco = null;
		EnumFacing fco = null;
		ItemStack stm1 = null;
		ItemStack stm2 = null;
		
		for (Map.Entry<EnumFacing, ItemStack> entry : mapFacingConductiveItemStack.entrySet()){
			if(stm1 == null) stm1 = mapFacingMaterialItemStack.get(entry.getKey()).copy();
			else if(stm2 == null) stm2 = mapFacingMaterialItemStack.get(entry.getKey()).copy();
			
			if(stm1 != null && stm2 != null){
				if(Materials.getElectrodeFromStack(stm2).potential < Materials.getElectrodeFromStack(stm1).potential ){
					materialsB.setAnodeAndConductive(stm2.copy(), entry.getValue(), mapFacingMaterialAmount.get(entry.getKey()));
					materialsB.setCathodeAndConductive(stm1.copy(), stco, mapFacingMaterialAmount.get(fco));
					materialsB.anodeMP.materialLimit = mapFacingMaterialB3D.get(entry.getKey());
					materialsB.cathodeMP.materialLimit = mapFacingMaterialB3D.get(fco);
				}else{
					materialsB.setAnodeAndConductive(stm1.copy(), stco, mapFacingMaterialAmount.get(fco));
					materialsB.setCathodeAndConductive(stm2.copy(), entry.getValue(), mapFacingMaterialAmount.get(entry.getKey()));
					materialsB.anodeMP.materialLimit = mapFacingMaterialB3D.get(fco);
					materialsB.cathodeMP.materialLimit = mapFacingMaterialB3D.get(entry.getKey());
				}
			}
			
			stco = entry.getValue();
			fco = entry.getKey();
		}
		materialsB.setElectrolyte(electrolyteItemStack, amountElectrolyte);
		materialsB.setEnergecticValues();
	}
	
	public boolean isBlockPartOfStructure(BlockPos pos){
		return ( pos.getX() >= dwn.getX() && pos.getX() <= ues.getX() ) && ( pos.getY() >= dwn.getY() && pos.getY() <= ues.getY() ) && ( pos.getZ() >= dwn.getZ() && pos.getZ() <= ues.getZ() );
	}
	
	private void unsetupBlockStructure(BlockPos bp, World world){
		IBlockState s = world.getBlockState(bp);
		if(s!=null && s.getBlock() instanceof ABlockBBStructure)
			world.setBlockState(bp, s.withProperty(ABlockBBStructure.ISSTRUCT, false));
	}
	
	private void unsetupStructure(World world) {
		int [] tx = new int[]{dwn.getX(), ues.getX()};
		for (int i = 0 ; i < tx.length ; i++ ){
			int x = tx[i];
			for (int z = dwn.getZ() ; z <= ues.getZ() ; z++ ){
				for (int y = dwn.getY() ; y <= ues.getY() ; y++ ){
					unsetupBlockStructure(new BlockPos(x,y,z), world);
				}
			}
		}

		int [] ty = new int[]{dwn.getY(), ues.getY()};
		for (int i = 0 ; i < ty.length ; i++ ){
			int y = ty[i];
			for (int x = dwn.getX() ; x <= ues.getX() ; x++ ){
				for (int z = dwn.getZ() ; z <= ues.getZ() ; z++ ){
					unsetupBlockStructure(new BlockPos(x,y,z), world);
				}
			}
		}

		int [] tz = new int[]{dwn.getZ(), ues.getZ()};
		for (int i = 0 ; i < tz.length ; i++ ){
			int z = tz[i];
			for (int x = dwn.getX() ; x <= ues.getX() ; x++ ){
				for (int y = dwn.getY() ; y <= ues.getY() ; y++ ){
					unsetupBlockStructure(new BlockPos(x,y,z), world);
				}
			}
		}
	}

	private void reset() {
		electrolyteItemStack = null;
		electrolyteFacing = null;
		mapFacingConductiveItemStack.clear();
		mapFacingMaterialItemStack.clear();
		mapFacingMaterialAmount.clear();
		mapFacingMaterialB3D.clear();
		dwn = null;
		ues = null;
		isStructured = false;
		isValid = false;
		isManual = true;
		materialsB = new MaterialsBattery();
	}
	
	private boolean checkMaterials(World world) {

		if(dwnElectrolyte == null || uesElectrolyte == null || mapFacingConductiveItemStack.isEmpty())
			return false;
		
		EnumFacing tf = mapFacingConductiveItemStack.entrySet().iterator().next().getKey();
		BlockPos nbp = plugPos.add(plugFacing.getDirectionVec()).add(tf.getDirectionVec());
		
		ItemStack b1 = null;
		EnumFacing fb1 = null;
		int amountb1 = 0;

		for (int x = dwn.getX()+1 ; x < uesElectrolyte.getX() ; x++ ){
			for (int y = dwn.getY()+1 ; y < uesElectrolyte.getY() ; y++ ){
				for (int z = dwn.getZ()+1 ; z < uesElectrolyte.getZ() ; z++ ){
					IBlockState s = world.getBlockState(new BlockPos(x,y,z));
					ItemStack n = new ItemStack(s.getBlock());
					if(x == nbp.getX() && y == nbp.getY() && z == nbp.getZ())
						fb1 = tf;
					if(s.getBlock() == Blocks.AIR || allowedMaterialsBlock.stream().anyMatch((l)->l.isItemEqualIgnoreDurability(n)) ){
						if(s.getBlock() != Blocks.AIR){
							amountb1++;
							if(b1 == null)b1 = n.copy();
							else if( !n.isItemEqualIgnoreDurability(b1) )
								return false;
						}
					}else
						return false;
				}
			}
		}
		ItemStack b2 = null;
		EnumFacing fb2 = null;
		int amountb2 = 0;
		if(fb1 == null){
			fb1 = tf.getOpposite();
			fb2 = tf;
		}else{
			fb2 = tf.getOpposite();
		}
		
		
		for (int x = dwnElectrolyte.getX()+1 ; x < ues.getX() ; x++ ){
			for (int y = dwnElectrolyte.getY()+1 ; y < ues.getY() ; y++ ){
				for (int z = dwnElectrolyte.getZ()+1 ; z < ues.getZ() ; z++ ){
					IBlockState s = world.getBlockState(new BlockPos(x,y,z));
					ItemStack n = new ItemStack(s.getBlock());
					if(s.getBlock() == Blocks.AIR || allowedMaterialsBlock.stream().anyMatch((l)->l.isItemEqualIgnoreDurability(n)) ){
						if(s.getBlock() != Blocks.AIR){
							amountb2++;
							if(b2 == null)b2 = n.copy();
							else if( !n.isItemEqualIgnoreDurability(b2) )
								return false;
						}
					}else
						return false;
				}
			}
		}
		
		if(b1 != null){
			mapFacingMaterialItemStack.put(fb1, b1);
			mapFacingMaterialAmount.put(fb1, amountb1);
			mapFacingMaterialB3D.put(fb1, new B3D(new BlockPos(dwn.getX()+1, dwn.getY()+1, dwn.getZ()+1),new BlockPos(uesElectrolyte.getX()-1,uesElectrolyte.getY()-1,uesElectrolyte.getZ()-1)));
		}
		if(b2 != null){
			mapFacingMaterialItemStack.put(fb2, b2);
			mapFacingMaterialAmount.put(fb2, amountb2);
			mapFacingMaterialB3D.put(fb2, new B3D(new BlockPos(dwnElectrolyte.getX()+1,dwnElectrolyte.getY()+1,dwnElectrolyte.getZ()+1),new BlockPos(ues.getX()-1, ues.getY()-1, ues.getZ()-1)));
		}
		return true;
	}

	private boolean checkElectrolyte(World world) {
		int di = plugFacing.getAxisDirection().getOffset();
		int xmin = plugPos.getX();
		int xmax = plugPos.getX();
		int ymin = plugPos.getY();
		int ymax = plugPos.getY();
		int zmin = plugPos.getZ();
		int zmax = plugPos.getZ();
		
		int xmina = 0;
		int xmaxa = 0;
		int ymina = 0;
		int ymaxa = 0;
		int zmina = 0;
		int zmaxa = 0;
		
		switch(plugFacing.getAxis()){
			case X:
				xmin = dwn.getX()+1;
				xmina = -1;
				xmax = ues.getX()-1;
				xmaxa = 1;
				switch(electrolyteFacing.getAxis()){
					case Y:
						ymin = dwn.getY()+1;
						ymina = -1;
						ymax = ues.getY()-1;
						ymaxa = 1;
						break;
					case Z:
						zmin = dwn.getZ()+1;
						zmina = -1;
						zmax = ues.getZ()-1;
						zmaxa = 1;
						break;
					default:
						break;
				}
				break;
			case Y:
				ymin = dwn.getY()+1;
				ymina = -1;
				ymax = ues.getY()-1;
				ymaxa = 1;
				switch(electrolyteFacing.getAxis()){
					case X:
						xmin = dwn.getX()+1;
						xmina = -1;
						xmax = ues.getX()-1;
						xmaxa = 1;
						break;
					case Z:
						zmin = dwn.getZ()+1;
						zmina = -1;
						zmax = ues.getZ()-1;
						zmaxa = 1;
						break;
					default:
						break;
				}
				break;
			case Z:
				zmin = dwn.getZ()+1;
				zmina = -1;
				zmax = ues.getZ()-1;
				zmaxa = 1;
				switch(electrolyteFacing.getAxis()){
					case Y:
						ymin = dwn.getY()+1;
						ymina = -1;
						ymax = ues.getY()-1;
						ymaxa = 1;
						break;
					case X:
						xmin = dwn.getX()+1;
						xmina = -1;
						xmax = ues.getX()-1;
						xmaxa = 1;
						break;
					default:
						break;
				}
				break;
			default:
				break;
		}
		materialsB.electrolyteMP.materialLimit = new B3D(new BlockPos(xmin,ymin,zmin),new BlockPos(xmax,ymax,zmax));
		
		dwnElectrolyte = new BlockPos(xmin+xmina,ymin+ymina,zmin+zmina);
		uesElectrolyte = new BlockPos(xmax+xmaxa,ymax+ymaxa,zmax+zmaxa);
		ItemStack elec = null;
		for (int x = xmin ; x <= xmax ; x++ ){
			for (int y = ymin ; y <= ymax ; y++ ){
				for (int z = zmin ; z <= zmax ; z++ ){
					IBlockState st = world.getBlockState(new BlockPos(x,y,z));
					ItemStack n = new ItemStack(st.getBlock());
					if(st.getBlock() == Blocks.AIR || allowedElectrolyteBlock.stream().anyMatch((l)->l.isItemEqualIgnoreDurability(n)) ){
						if(st.getBlock() != Blocks.AIR){
							amountElectrolyte++;
							if(electrolyteItemStack == null)electrolyteItemStack = n.copy();
							else if( !n.isItemEqualIgnoreDurability(electrolyteItemStack) )
								return false;
						}
					}else
						return false;
				}
			}
		}
		return true;
	}
	
	private boolean isConductiveAllowedHere(BlockPos pos){
		switch(mapFacingConductiveItemStack.entrySet().iterator().next().getKey().getAxis()){
			case X:
				return pos.getY() == plugPos.getY() && pos.getZ() == plugPos.getZ();
			case Y:
				return pos.getX() == plugPos.getX() && pos.getZ() == plugPos.getZ();
			case Z:
				return pos.getX() == plugPos.getX() && pos.getY() == plugPos.getY();
			default:
				break;
		
		}
		return false;
	}
	
	private boolean checkBlockConductive(BlockPos currentPos, EnumFacing direction, World world, BlockPos limit, ItemStack conductiveRef){
		BlockPos nextPos = currentPos.add(direction.getDirectionVec());
		IBlockState nextState = world.getBlockState(nextPos);

		if(currentPos.getX()==limit.getX() && currentPos.getY()==limit.getY() && currentPos.getZ()==limit.getZ())
			return true;
		if(conductiveRef.isItemEqualIgnoreDurability(new ItemStack(nextState.getBlock()))){
			return checkBlockConductive(nextPos, direction, world, limit, conductiveRef);
		}
		
		return false;
	}
	
	private boolean checkConductive (World world){

		for (Map.Entry<EnumFacing, ItemStack> entry : mapFacingConductiveItemStack.entrySet()){
			int di = entry.getKey().getAxisDirection().getOffset();
			int x = plugPos.getX();
			int y = plugPos.getY();
			int z = plugPos.getZ();
			switch(entry.getKey().getAxis()){
				case X:
					x = di < 0 ? dwn.getX()+1: ues.getX()-1; 
					break;
				case Y:
					y = di < 0 ? dwn.getY()+1: ues.getY()-1; 
					break;
				case Z:
					z = di < 0 ? dwn.getZ()+1: ues.getZ()-1; 
					break;
				default:
					break;
			}
			boolean cc = checkBlockConductive(plugPos.add(entry.getKey().getDirectionVec()), entry.getKey(), world, new BlockPos(x,y,z), entry.getValue());
			if(!cc)
				return false;
		}
		
		return true;
	}
	
	private void setupblockStructure(BlockPos bp, World world){
		IBlockState s = world.getBlockState(bp);
		if(s!=null && s.getBlock() instanceof ABlockBBStructure)
			world.setBlockState(bp, s.withProperty(ABlockBBStructure.ISSTRUCT, true));
		TileEntity te = world.getTileEntity(bp);
		if(te!=null && te instanceof ITileHaveMaster){
			((ITileHaveMaster)te).setMaster(plugPos);
		}
	}

	private void setupStructure(World world) {				
		int [] tx = new int[]{dwn.getX(), ues.getX()};
		for (int i = 0 ; i < tx.length ; i++ ){
			int x = tx[i];
			for (int z = dwn.getZ() ; z <= ues.getZ() ; z++ ){
				for (int y = dwn.getY() ; y <= ues.getY() ; y++ ){
					setupblockStructure(new BlockPos(x,y,z), world);
				}
			}
		}

		int [] ty = new int[]{dwn.getY(), ues.getY()};
		for (int i = 0 ; i < ty.length ; i++ ){
			int y = ty[i];
			for (int x = dwn.getX() ; x <= ues.getX() ; x++ ){
				for (int z = dwn.getZ() ; z <= ues.getZ() ; z++ ){
					setupblockStructure(new BlockPos(x,y,z), world);
				}
			}
		}

		int [] tz = new int[]{dwn.getZ(), ues.getZ()};
		for (int i = 0 ; i < tz.length ; i++ ){
			int z = tz[i];
			for (int x = dwn.getX() ; x <= ues.getX() ; x++ ){
				for (int y = dwn.getY() ; y <= ues.getY() ; y++ ){
					setupblockStructure(new BlockPos(x,y,z), world);
				}
			}
		}
		isStructured = true;
	}
	
	private boolean checkStructure(World world) {
				
		//check x wall
		int [] tx = new int[]{dwn.getX(), ues.getX()};
		for (int i = 0 ; i < tx.length ; i++ ){
			int x = tx[i];
			for (int z = dwn.getZ() ; z <= ues.getZ() ; z++ ){
				for (int y = dwn.getY() ; y <= ues.getY() ; y++ ){
					if(!checkBlockStructure(new BlockPos(x,y,z), world))
						return false;
				}
			}
		}

		//check y wall
		int [] ty = new int[]{dwn.getY(), ues.getY()};
		for (int i = 0 ; i < ty.length ; i++ ){
			int y = ty[i];
			for (int x = dwn.getX() ; x <= ues.getX() ; x++ ){
				for (int z = dwn.getZ() ; z <= ues.getZ() ; z++ ){
					if(!checkBlockStructure(new BlockPos(x,y,z), world))
						return false;
				}
			}
		}

		//check z wall
		int [] tz = new int[]{dwn.getZ(), ues.getZ()};
		for (int i = 0 ; i < tz.length ; i++ ){
			int z = tz[i];
			for (int x = dwn.getX() ; x <= ues.getX() ; x++ ){
				for (int y = dwn.getY() ; y <= ues.getY() ; y++ ){
					if(!checkBlockStructure(new BlockPos(x,y,z), world))
						return false;
				}
			}
		}
		
		return true;
	}
	
	private boolean checkBlockStructure(BlockPos bp, World world){
		IBlockState s = world.getBlockState(bp);
		ItemStack ns = new ItemStack(s.getBlock());
		if(allowedAutomaticBlock.contains(s.getBlock()))
			isManual = false;
		if(!allowedStructureBlock.contains(s.getBlock()) && !allowedConductiveBlock.stream().anyMatch((l)->l.isItemEqualIgnoreDurability(ns)))
			return false;
		if(allowedStructureBlock.contains(s.getBlock()) && s.getBlock() instanceof ABlockBBStructure == true && s.getValue(ABlockBBStructure.ISSTRUCT) == true && !isStructured)
			return false;
		if(allowedConductiveBlock.stream().anyMatch((l)->l.isItemEqualIgnoreDurability(ns))){
			boolean b = mapFacingConductiveItemStack.entrySet().stream().noneMatch((f)->f.getValue().isItemEqualIgnoreDurability(new ItemStack(s.getBlock())));
			if( b == true || isConductiveAllowedHere(bp) == false)
				return false;
		}
		return true;
	}
	
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    	if(dwn!=null){
	        nbt.setInteger("dwnx", dwn.getX());
	        nbt.setInteger("dwny", dwn.getY());
	        nbt.setInteger("dwnz", dwn.getZ());
    	}
    	if(ues!=null){
	        nbt.setInteger("uesx", ues.getX());
	        nbt.setInteger("uesy", ues.getY());
	        nbt.setInteger("uesz", ues.getZ());
    	}
    	if(dwnElectrolyte!=null){
	        nbt.setInteger("dwnelex", dwnElectrolyte.getX());
	        nbt.setInteger("dwneley", dwnElectrolyte.getY());
	        nbt.setInteger("dwnelez", dwnElectrolyte.getZ());
    	}
    	if(uesElectrolyte!=null){
	        nbt.setInteger("ueselex", uesElectrolyte.getX());
	        nbt.setInteger("ueseley", uesElectrolyte.getY());
	        nbt.setInteger("ueselez", uesElectrolyte.getZ());
    	}
		for (Map.Entry<EnumFacing, ItemStack> entry : mapFacingConductiveItemStack.entrySet()){
			nbt.setTag("facingConductive"+entry.getKey().getIndex(), entry.getValue().writeToNBT(new NBTTagCompound()));
		}
		for (Map.Entry<EnumFacing, ItemStack> entry : mapFacingMaterialItemStack.entrySet()){
			nbt.setTag("facingMaterial"+entry.getKey().getIndex(), entry.getValue().writeToNBT(new NBTTagCompound()));
		}
		for (Map.Entry<EnumFacing, Integer> entry : mapFacingMaterialAmount.entrySet()){
			nbt.setInteger("facingAmount"+entry.getKey().getIndex(), entry.getValue());
		}
		for (Map.Entry<EnumFacing, B3D> entry : mapFacingMaterialB3D.entrySet()){
			nbt.setTag("facingB3D"+entry.getKey().getIndex(), entry.getValue().writeToNBT(new NBTTagCompound()));
		}
		
    	if(plugFacing!=null)
    		nbt.setInteger("plugFacing", plugFacing.getIndex());
    	if(electrolyteFacing!=null)
    		nbt.setInteger("electrolyteFacing", electrolyteFacing.getIndex());
    	if(electrolyteItemStack!=null){
			nbt.setTag("electrolyteItemStack", electrolyteItemStack.writeToNBT(new NBTTagCompound()));
    	}
    	
		nbt.setInteger("amountElectrolyte", amountElectrolyte);
        nbt.setBoolean("isValid", isValid);
        nbt.setBoolean("isManual", isManual);
        nbt.setBoolean("isStructured", isStructured);
        materialsB.writeToNBT(nbt);
        return nbt;
    }
    
    public void readFromNBT(NBTTagCompound nbt) {
        dwn = new BlockPos(nbt.getInteger("dwnx"),nbt.getInteger("dwny"),nbt.getInteger("dwnz"));	
        ues = new BlockPos(nbt.getInteger("uesx"),nbt.getInteger("uesy"),nbt.getInteger("uesz"));
        dwnElectrolyte = new BlockPos(nbt.getInteger("dwnelex"),nbt.getInteger("dwneley"),nbt.getInteger("dwnelez"));
        uesElectrolyte = new BlockPos(nbt.getInteger("ueselex"),nbt.getInteger("ueseley"),nbt.getInteger("ueselez"));
        for (EnumFacing val : EnumFacing.VALUES){
        	NBTBase fsc = nbt.getTag("facingConductive"+val.getIndex());
        	if(fsc != null)
        		mapFacingConductiveItemStack.put(val, new ItemStack((NBTTagCompound)fsc));
        	
        	NBTBase fsm = nbt.getTag("facingMaterial"+val.getIndex());
        	if(fsm != null){
        		mapFacingMaterialItemStack.put(val, new ItemStack((NBTTagCompound)fsm));
        		mapFacingMaterialAmount.put(val, nbt.getInteger("facingAmount"+val.getIndex()));
        		mapFacingMaterialB3D.put(val,new B3D((NBTTagCompound) nbt.getTag("facingB3D"+val.getIndex())));
        	}
		}

        NBTBase es = nbt.getTag("electrolyteItemStack");
        if(es != null)
        	electrolyteItemStack = new ItemStack((NBTTagCompound)es);

        amountElectrolyte = nbt.getInteger("amountElectrolyte");
        plugFacing = EnumFacing.getFront(nbt.getInteger("plugFacing"));
        electrolyteFacing = EnumFacing.getFront(nbt.getInteger("electrolyteFacing"));
        isValid = nbt.getBoolean("isValid");
        isManual = nbt.getBoolean("isManual");
        isStructured = nbt.getBoolean("isStructured");
        materialsB.readFromNBT(nbt);
    }
}
