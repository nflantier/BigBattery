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
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;
import noelflantier.bigbattery.common.blocks.ABlockBBStructure;
import noelflantier.bigbattery.common.blocks.BlockConductive;
import noelflantier.bigbattery.common.blocks.BlockFluid;
import noelflantier.bigbattery.common.blocks.BlockPlug;
import noelflantier.bigbattery.common.handlers.ModBlocks;
import noelflantier.bigbattery.common.handlers.ModConfig;
import noelflantier.bigbattery.common.handlers.ModProperties.InterfaceType;
import noelflantier.bigbattery.common.materials.MaterialsHandler;
import noelflantier.bigbattery.common.materials.MaterialsHandler.Electrode;
import noelflantier.bigbattery.common.materials.MaterialsHandler.Electrode.TYPE;
import noelflantier.bigbattery.common.tiles.ITileHaveMaster;
import noelflantier.bigbattery.common.tiles.ITileMaster;

public class MultiBlockBattery {

	public boolean init = false;
	public boolean isValid = false;
	public boolean isStructured = false;
	public static Random rdm  = new Random();
	
	public BlockPos plugPos;
	public EnumFacing plugFacingOpposite;
	
	public ItemStack electrolyteItemStack;
	public EnumFacing electrolyteFacing;
	public BlockPos dwnElectrolyte;
	public BlockPos uesElectrolyte;
	public int amountElectrolyte;
	
	public List<BlockPos> interfacePos = new ArrayList<BlockPos>();	
	
	public Map<EnumFacing, ItemStack> mapFacingConductiveItemStack= new HashMap<EnumFacing, ItemStack>();
	public Map<EnumFacing, ItemStack> mapFacingMaterialItemStack= new HashMap<EnumFacing, ItemStack>();
	public Map<EnumFacing, Integer> mapFacingMaterialAmount= new HashMap<EnumFacing, Integer>();
	public Map<EnumFacing, B3D> mapFacingMaterialB3D= new HashMap<EnumFacing, B3D>();
	public BlockPos dwn;
	public BlockPos ues;
	
    public static List<Block> allowedStructureBlock= new ArrayList<Block>();
    public static List<Block> allowedInterfaceBlock= new ArrayList<Block>();   
    
    public MaterialsBattery materialsBattery = new MaterialsBattery();
    
    static{    	
    	allowedStructureBlock.add(ModBlocks.blockCasing);
    	allowedStructureBlock.add(ModBlocks.blockPlug);
    	allowedStructureBlock.add(ModBlocks.blockInterface);
    	
    	allowedInterfaceBlock.add(ModBlocks.blockInterface);
    }
	
	public MultiBlockBattery(){
		
	}

	public static class B3D{
		public BlockPos dwn;
		public BlockPos ues;
		public static Random brdm = new Random();
		
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
	    
		public int getNumberOfBlock() {
			return ( Math.abs( ues.getX() - dwn.getX() ) + 1 ) * ( Math.abs( ues.getZ() - dwn.getZ() ) + 1 ) * ( Math.abs( ues.getY() - dwn.getY() ) + 1 );
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
	    
		public boolean placeBlockFromStack(World world, ItemStack stack, Block toReplace, int amount) {
			int i = 0;
			if(stack != null && stack.getItem() instanceof ItemBlock){
				if(((ItemBlock)stack.getItem()).block != null){
					for (int x = dwn.getX() ; x <= ues.getX() ; x++ ){
						for (int y = dwn.getY() ; y <= ues.getY() ; y++ ){
							for (int z = dwn.getZ() ; z <= ues.getZ() ; z++ ){
								BlockPos p = new BlockPos(x,y,z);
								IBlockState s = world.getBlockState(p);
								if(s.getBlock() == toReplace  || isBlockFluidReplaceable(s)){
									world.setBlockState(p, ((ItemBlock)stack.getItem()).block.getDefaultState());
									i++;
									if( i >= amount )
										return true;
								}
							}
						}
					}
				}
			}
			return false;
		}
		
		public boolean placeBlockFromFluidStack(World world, FluidStack stack, Block toReplace, int amount) {
			int i = 0;
			if(stack != null && stack.getFluid()!=null){
				if(stack.getFluid().getBlock() != null){
					for (int x = dwn.getX() ; x <= ues.getX() ; x++ ){
						for (int y = dwn.getY() ; y <= ues.getY() ; y++ ){
							for (int z = dwn.getZ() ; z <= ues.getZ() ; z++ ){
								BlockPos p = new BlockPos(x,y,z);
								IBlockState s = world.getBlockState(p);
								if(s.getBlock() == toReplace || isBlockFluidReplaceable(s) ){
									world.setBlockState(p, stack.getFluid().getBlock().getDefaultState());
									i++;
									if( i >= amount )
										return true;
								}
							}
						}
					}
				}
			}
			return false;
		}
		
	}
	
	public class MaterialPart{

		public double weight = 1;
		public double totalUnit = -1;//~tick time per block
		public double currentUnit = -1;//~current tick time
		public int currentAmount = -1;//~current nb block
		public int maxAmount = -1;//~nb max block
		public B3D materialLimit = new B3D();
		
		public MaterialPart(){}
		
	    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
	        nbt.setDouble("weight", weight);
	        nbt.setDouble("totalUnit", totalUnit);
	        nbt.setDouble("currentUnit", currentUnit);
	        nbt.setInteger("currentAmount", currentAmount);
	        nbt.setInteger("maxAmount", maxAmount);
	        materialLimit.writeToNBT(nbt);
	        return nbt;
	    }
	    
	    public void readFromNBT(NBTTagCompound nbt) {
	    	weight = nbt.getFloat("weight");
	    	totalUnit = nbt.getDouble("totalUnit");
	    	currentUnit = nbt.getDouble("currentUnit");
	    	currentAmount = nbt.getInteger("currentAmount");
	    	maxAmount = nbt.getInteger("maxAmount");
	    	materialLimit.readFromNBT(nbt);
	    }

		public void reset() {
			weight = 1;
			totalUnit = -1;
			currentUnit = -1;
			currentAmount = -1;
		}
		
		public void setMaterialLimit(B3D b3d) {
			materialLimit = b3d;
			maxAmount = b3d.getNumberOfBlock();
		}

		public boolean updateValueAndMaterials(World world, ItemStack stack, Block toReplace, int i) {
			if(currentAmount + i > maxAmount)
				i = maxAmount - currentAmount;
			if(currentAmount >= maxAmount)
				return false;
			currentAmount += i;
			return materialLimit.placeBlockFromStack(world, stack, toReplace, i);
		}
		
		public boolean updateValueAndMaterials(World world, FluidStack stack, Block toReplace, int i) {
			if(currentAmount >= maxAmount)
				return false;
			currentAmount += i;
			return materialLimit.placeBlockFromFluidStack(world, stack, toReplace, i);
		}
	}
	
	public class MaterialsBattery{
	
		public MaterialsHandler.Electrode electrode1;
		public MaterialsHandler.Conductive electrode1Cond;
		public MaterialPart electrode1MP = new MaterialPart();
		
		public MaterialsHandler.Electrode electrode2;
		public MaterialsHandler.Conductive electrode2Cond;
		public MaterialPart electrode2MP = new MaterialPart();
		
		public MaterialsHandler.Electrolyte electrolyte;
		public MaterialPart electrolyteMP = new MaterialPart();
		
		public double potentialDifference = 0;
		public boolean deleteMaterials = true;
		int rangeElectrolyteO = 5;
		int rangeElectrodeO = 5;
		
		public MaterialsBattery(){
			
		}

		public boolean useMaterial(World world, BlockPos pos){
			if(pos == null)
				return false;
			IBlockState state = world.getBlockState(pos);
			ItemStack st = new ItemStack(state.getBlock());
			if(state.getBlock() != Blocks.AIR ){
				world.setBlockToAir(pos);
				return true;
			}
			return false;
		}
		
		public void handleMaterials(World world, float rationRF){
			if(electrode1 == null || electrode2 == null || electrolyte == null)
				return ;
			
			if(electrode1MP.currentUnit<=0){
				electrode1MP.currentUnit = electrode1MP.totalUnit;
				if( electrode1MP.currentAmount <= 0){
					electrode1 = null;
					electrode1MP.reset();
					return;
				}
				if(electrode1MP.currentAmount>electrode1MP.maxAmount)
					electrode1MP.currentAmount = electrode1MP.maxAmount;
					electrode1MP.currentAmount-=1;
				if(deleteMaterials && !useMaterial(world, electrode1MP.materialLimit.getRandomBlock()) )
					if( !useMaterial(world,electrode1MP.materialLimit.getNextMaterialBlock(world))){
						//TODO si usematerial false check mp limit if block equal and set mp currentamount to that number
					}
			}
			if(electrode2MP.currentUnit<=0){
				electrode2MP.currentUnit = electrode2MP.totalUnit;
				if( electrode2MP.currentAmount <= 0){
					electrode2 = null;
					electrode2MP.reset();
					return;
				}
				if(electrode2MP.currentAmount>electrode2MP.maxAmount)
					electrode2MP.currentAmount = electrode2MP.maxAmount;
					electrode2MP.currentAmount-=1;
				if(deleteMaterials && !useMaterial(world, electrode2MP.materialLimit.getRandomBlock()) )
					useMaterial(world,electrode2MP.materialLimit.getNextMaterialBlock(world));
				
			}
			
			if(electrolyteMP.currentUnit<=0){
				electrolyteMP.currentUnit = electrolyteMP.totalUnit;
				if( electrolyteMP.currentAmount <= 0){
					electrolyte = null;
					electrolyteMP.reset();
					return;
				}
				if(electrolyteMP.currentAmount>electrolyteMP.maxAmount)
					electrolyteMP.currentAmount = electrolyteMP.maxAmount;
					electrolyteMP.currentAmount-=1;
				if(deleteMaterials && !useMaterial(world, electrolyteMP.materialLimit.getRandomBlock()) )
					useMaterial(world,electrolyteMP.materialLimit.getNextMaterialBlock(world));
			}
			
			electrode1MP.currentUnit -= ( rdm.nextDouble() + 1 ) * rationRF +1;
			electrode2MP.currentUnit -= ( rdm.nextDouble() + 1 ) * rationRF +1;
			electrolyteMP.currentUnit -= ( rdm.nextDouble() + 1 ) * rationRF +1;

			/*System.out.println("ano "+anodeMP.currentUnit);
			System.out.println("cath "+cathodeMP.currentUnit);
			System.out.println("ele "+electrolyteMP.currentUnit);*/
		}
		
		public double generateEnergy(){
			if(electrode1 == null || electrode2 == null || electrolyte == null){
				return 0;
			}
			if(potentialDifference == 0 ){
				return 1;
			}

			/*System.out.println(electrode2MP.currentUnit+"    "+electrode1MP.currentUnit+"  "+electrolyteMP.currentUnit);
			System.out.println(electrolyteMP.currentAmount);*/
			double cate = 1;
			double anoe = 1;
			if(electrode1.type == TYPE.ANODE){
				cate = Math.pow( Math.abs( Math.ceil( rangeElectrodeO - Math.abs( Collections.min( electrode2.oxydationNumber ) ) ) ) + 1 , 1 ) * Math.pow( electrode2MP.currentAmount + 1, 1.1 );
				anoe = Math.pow( Math.abs( Math.ceil( rangeElectrodeO - Math.abs( Collections.max( electrode1.oxydationNumber ) ) ) ) + 1 , 1 ) * Math.pow( electrode1MP.currentAmount + 1, 1.1 );
			}else{
				cate = Math.pow( Math.abs( Math.ceil( rangeElectrodeO - Math.abs( Collections.min( electrode1.oxydationNumber ) ) ) ) + 1 , 1 ) * Math.pow( electrode1MP.currentAmount + 1, 1.1 );
				anoe = Math.pow( Math.abs( Math.ceil( rangeElectrodeO - Math.abs( Collections.max( electrode2.oxydationNumber ) ) ) ) + 1 , 1 ) * Math.pow( electrode2MP.currentAmount + 1, 1.1 );
			}
			double ne = Math.pow( potentialDifference , 1.1 ) * ( cate + anoe );
			double ee = Math.pow( Math.abs( Math.ceil( rangeElectrolyteO - Math.abs( Collections.max( electrolyte.oxydationNumber ) ) ) ) + 1 , 1 ) * Math.abs(electrolyte.potential) * Math.pow( electrolyte.electrolyteType.ratioVoltage , 1.1 ) * Math.pow( electrolyteMP.currentAmount + 1, 1.1 );
			double ce = Math.pow( ee + ne , 1 ) * ( ( electrode1Cond.ratioEfficiency + electrode2Cond.ratioEfficiency ) / 2 );
			
			//System.out.println(cate+"   "+anoe+"   "+ne+"    "+ee+"   "+ce+"   "+electrode1Cond.ratioEfficiency+"    "+electrode2Cond.ratioEfficiency);
			
			return ce < 0 ? 0 : ce;
		}
		
		public void setEnergecticValues(){
			if(electrode1 == null || electrode2 == null || electrolyte == null)
				return;
			
			potentialDifference = Math.abs(electrode1.potential - electrode2.potential);
			electrode1MP.totalUnit = Math.floor( ( electrode1MP.weight < 1 ? Math.floor( 1 / Math.abs( electrode1MP.weight ) ) : electrode1MP.weight ) * ( Math.pow(electrode1.oxydationNumber.size(),1.2) * ( 1000 / Collections.max( electrode1.oxydationNumber ) ) ) );
			electrode2MP.totalUnit = Math.floor( ( electrode2MP.weight < 1 ? Math.floor( 1 / Math.abs( electrode2MP.weight ) ) : electrode2MP.weight ) * ( Math.pow(electrode2.oxydationNumber.size() ,1.2) * ( 1000 / Collections.max( electrode2.oxydationNumber ) ) ) );
			electrolyteMP.totalUnit= Math.floor( ( electrolyteMP.weight < 1 ? Math.floor( 1 / Math.abs( electrolyteMP.weight ) ) : electrolyteMP.weight ) * ( Math.pow(electrolyte.potential ,electrolyte.electrolyteType.ratioDecay) * ( 1000 / Collections.max( electrolyte.oxydationNumber ) ) * ( Math.pow( electrolyte.oxydationNumber.size(), 1 ) ) ) );

			//System.out.println(electrode1MP.totalUnit+"    "+electrode2MP.totalUnit+"  "+electrolyteMP.totalUnit);
		}

		public int[] getMaterialsId(){
			return new int[]{electrode1!=null?electrode1.materialID:-1,electrode2!=null?electrode2.materialID:-1,electrolyte!=null?electrolyte.materialID:-1,electrode1Cond!=null?electrode1Cond.materialID:-1,electrode2Cond!=null?electrode2Cond.materialID:-1};
		}
		
		public MaterialsBattery setElectrodes(ItemStack el1Stack, int el1Amount, int id){
			if(el1Stack == null)
				return this;
			if(id == 1){
				electrode1 = MaterialsHandler.getElectrodeFromStack(el1Stack);
				electrode1MP.weight = 1;//MaterialsHandler.getWeightFromStack(electrode1, el1Stack);
				electrode1MP.currentAmount = el1Amount;
			}else{
				electrode2 = MaterialsHandler.getElectrodeFromStack(el1Stack);
				electrode2MP.weight = 1;//MaterialsHandler.getWeightFromStack(electrode2, el1Stack);
				electrode2MP.currentAmount = el1Amount;
			}
			return this;
		}
		public void setAnodeAndCathode(){
			if(electrode1 == null || electrode2 == null)
				return;
			
			if(electrode1.potential < electrode2.potential ){
				electrode1.setType(Electrode.TYPE.ANODE);
				electrode2.setType(Electrode.TYPE.CATHODE);
			}else{
				electrode1.setType(Electrode.TYPE.CATHODE);
				electrode2.setType(Electrode.TYPE.ANODE);
			}
		}		
		
		public Electrode getAnode(){
			if(electrode1 == null || electrode2 == null)
				return null;
			return electrode1.type == Electrode.TYPE.ANODE ? electrode1 : electrode2;
		}
		public Electrode getCathode(){
			if(electrode1 == null || electrode2 == null)
				return null;
			return electrode1.type == Electrode.TYPE.CATHODE ? electrode1 : electrode2;
		}
		
		public MaterialsBattery setConductives(ItemStack el1CondStack, ItemStack el2CondStack){
			electrode1Cond = MaterialsHandler.getConductiveFromStack(el1CondStack);
			electrode2Cond = MaterialsHandler.getConductiveFromStack(el2CondStack);
			return this;
		}

		public void setLimit(B3D b3d, B3D b3d2) {
			electrode1MP.setMaterialLimit(b3d);
			electrode2MP.setMaterialLimit(b3d2);
		}
		
		public MaterialsBattery setElectrolyte(ItemStack electrolyteStack, int amount){
			if(electrolyteStack == null){
				electrolyteMP.currentAmount = 0;
				return this;
			}
			electrolyte = MaterialsHandler.getElectrolyteFromStack(electrolyteStack);
			electrolyteMP.currentAmount = amount;
			return this;
		}

		public MaterialsBattery setElectrolyte(FluidStack electrolyteStack, int amount){
			if(electrolyteStack == null){
				electrolyteMP.currentAmount = 0;
				return this;
			}
			electrolyte = MaterialsHandler.getElectrolyteFromStack(electrolyteStack);
			System.out.println(electrolyte.stackReference);
			electrolyteMP.currentAmount = amount;
			return this;
		}
		
	    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
	    	NBTTagCompound t = new NBTTagCompound();
	        t.setInteger("electrode1", electrode1 != null ? electrode1.materialID : 0);
	        t.setInteger("electrode1Cond", electrode1Cond != null ? electrode1Cond.materialID : 0);
	        t.setTag("electrode1MP", electrode1MP.writeToNBT(new NBTTagCompound()));
	        
	        t.setInteger("electrode2", electrode2 != null ? electrode2.materialID : 0);
	        t.setInteger("electrode2Cond", electrode2Cond != null ? electrode2Cond.materialID : 0);
	        t.setTag("electrode2MP", electrode2MP.writeToNBT(new NBTTagCompound()));
	        
	        t.setInteger("electrolyte", electrolyte != null ? electrolyte.materialID : 0);
	        t.setTag("electrolyteMP", electrolyteMP.writeToNBT(new NBTTagCompound()));

	        t.setDouble("potentialDifference", potentialDifference);
	        nbt.setTag("materialbattery", t);
	        
	        return nbt;
	    }
	    
	    public void readFromNBT(NBTTagCompound nbt) {
	    	NBTTagCompound b = (NBTTagCompound) nbt.getTag("materialbattery");
	    	if(b!=null){
		    	int electrode1id = b.getInteger("electrode1");
		    	electrode1 = electrode1id >= 0 ? MaterialsHandler.electrodeListByPotential.get(electrode1id) : null;
		    	int electrode1Condid = b.getInteger("electrode1Cond");
		    	electrode1Cond = electrode1Condid >= 0 ? MaterialsHandler.conductiveListByRatio.get(electrode1Condid) : null;
		    	electrode1MP.readFromNBT((NBTTagCompound) b.getTag("electrode1MP"));

		    	int electrode2id = b.getInteger("electrode2");
		    	electrode2 = electrode2id >= 0 ? MaterialsHandler.electrodeListByPotential.get(electrode2id) : null;
		    	int electrode2Condid = b.getInteger("electrode2Cond");
		    	electrode2Cond = electrode2Condid >= 0 ? MaterialsHandler.conductiveListByRatio.get(electrode2Condid) : null;
		    	electrode2MP.readFromNBT((NBTTagCompound) b.getTag("electrode2MP"));
		    	
		    	setAnodeAndCathode();
		    	
		    	int electrolyteid = b.getInteger("electrolyte");
		    	electrolyte = electrolyteid >= 0 ? MaterialsHandler.electrolyteListByPotential.get(electrolyteid) : null;
		    	electrolyteMP.readFromNBT((NBTTagCompound) b.getTag("electrolyteMP"));
		    	
		    	potentialDifference = b.getFloat("potentialDifference");
	    	}
	    }
		
	}
	
	public void init(BlockPos plugPos, IBlockState plugState){
		this.init = true;
		this.plugFacingOpposite = plugState.getValue(BlockPlug.FACING).getOpposite();
	}
		
	private BlockPos findSide(BlockPos currentPos, EnumFacing direction, World world, int range, boolean checkElectrolyteFacing, boolean allowAir, double rangemin, EntityPlayer player){
		range++;
		if(range > ModConfig.maxSizeBattery)
			return null;
		BlockPos nextPos = currentPos.add(direction.getDirectionVec());
		IBlockState currentState = world.getBlockState(currentPos);
		IBlockState nextState = world.getBlockState(nextPos);
		ItemStack nextits = new ItemStack(nextState.getBlock(),1,nextState.getBlock().getMetaFromState(nextState));

		if(MaterialsHandler.anyMatchConductive(nextits)){
			if(!mapFacingConductiveItemStack.containsKey(direction))
				mapFacingConductiveItemStack.put(direction, nextits);
			return findSide(nextPos, direction, world, range, checkElectrolyteFacing, allowAir, rangemin, player);
		}else{
			if(checkElectrolyteFacing && electrolyteFacing == null && range == 1){
				electrolyteFacing = direction;
			}
		}
				
		if(MaterialsHandler.anyMatchElectrolyte(nextits)){
			return findSide(nextPos, direction, world, range, checkElectrolyteFacing, allowAir, rangemin, player);
		}
		if( ( allowAir && nextState.getBlock() == Blocks.AIR ) || ( allowAir && isBlockFluidReplaceable(nextState) ) ){
			return findSide(nextPos, direction, world, range, checkElectrolyteFacing, allowAir, rangemin, player);
		}
		if(allowedStructureBlock.contains(nextState.getBlock())){
			allowAir = false;
			if(nextState.getBlock() instanceof ABlockBBStructure == true && nextState.getValue(ABlockBBStructure.ISSTRUCT) == true)
				return range >= rangemin ? currentPos : null;
			return findSide(nextPos, direction, world, range, checkElectrolyteFacing, allowAir, rangemin, player);
		}else if(allowedStructureBlock.contains(currentState.getBlock())){
			return range >= rangemin ? currentPos : null;
		}
		return null;
	}
	
	public static boolean isBlockFluidReplaceable(IBlockState state){
		return state.getPropertyKeys().contains(BlockFluid.LEVEL) && state.getValue(BlockFluid.LEVEL) != 0 ;
	}
	
	public boolean getStructure(World world, EntityPlayer player){
		reset();

		EnumFacing dummyFacing = plugFacingOpposite;

		BlockPos l5 = findSide(plugPos, dummyFacing, world, 0, false, true, 3, player);
		
		dummyFacing = dummyFacing.getAxis().isVertical() ? dummyFacing.rotateAround(EnumFacing.Axis.X) : dummyFacing.rotateY();
		BlockPos l1 = findSide(plugPos, dummyFacing, world, 0, true, false, 0, player);
		BlockPos l2 = findSide(plugPos, dummyFacing.getOpposite(), world, 0, true, false, 0, player);

		dummyFacing = dummyFacing.rotateAround(plugFacingOpposite.getAxis());

		BlockPos l3 = findSide(plugPos, dummyFacing, world, 0, true, false, 0, player);
		BlockPos l4 = findSide(plugPos, dummyFacing.getOpposite(), world, 0, true, false, 0, player);
		/*System.out.println(l1);
		System.out.println(l2);
		System.out.println(l3);
		System.out.println(l4);
		System.out.println(l5);*/
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
		return true;
	}
	
	public boolean batteryCheckAndSetupStructure(World world, BlockPos ppos, @Nullable EntityPlayer player){
		init(ppos, world.getBlockState(ppos));
		if(!init)
			return false;
		
		if(!isStructured){
			if(!getStructure(world, player))
				 return false;
		}

		boolean chst = checkStructure(world);
		boolean chco = isStructured ? true : checkConductive(world);
		boolean chel = isStructured ? true : checkElectrolyte(world);
		boolean chma = isStructured ? true : checkMaterials(world);

		/*System.out.println(chst);
		System.out.println(chco);
		System.out.println(chel);
		System.out.println(chma);*/
		isValid = chst && chco && chel && chma;
		
		if(!isValid){
			if(isStructured){
				isStructured = false;
				unsetupStructure(world);
			}
			reset();
			return false;
		}
		
		setupStructure(world);
		if(!isStructured){
			reset();
			return false;
		}

		setupMaterials(world);
		setPlugCapacity(world);
		
		return isStructured;
	}
	
	private void setPlugCapacity(World world){
		if(plugPos == null)
			return;
		TileEntity te = world.getTileEntity(plugPos);
		if( te != null && te instanceof ITileMaster){
			((ITileMaster)te).setEnergyCapacity();
		}
	}

	public ActionResult<FluidStack> handleConsumeFluidStack(World world, FluidStack stack, InterfaceType type){

        if(stack==null || stack.getFluid() == null || stack.amount == 0)
            return new ActionResult(EnumActionResult.PASS, null);
		switch(type){
			case ELECTRODE :
			case ELECTROLYTE :
				if(materialsBattery.electrolyte != null){
					if( materialsBattery.electrolyteMP.currentAmount < materialsBattery.electrolyteMP.maxAmount ){
						if(materialsBattery.electrolyte.fluidStack.stream().anyMatch((e)->e.equals(stack))){
							int amount = MaterialsHandler.getFluidAmountFromStack(materialsBattery.electrolyte, stack);
							if(stack.amount < amount)
								return new ActionResult(EnumActionResult.PASS, stack);
							FluidStack fs = stack.copy();
							fs.amount = stack.amount - amount;
							materialsBattery.electrolyteMP.updateValueAndMaterials(world , stack, Blocks.AIR, 1);	
							return  fs.amount <= 0 ? new ActionResult(EnumActionResult.SUCCESS, null) : new ActionResult(EnumActionResult.SUCCESS, fs);
						}
					}
				}
				if(materialsBattery.electrolyte == null){
					if(MaterialsHandler.anyMatchElectrolyte(stack)){
						materialsBattery.setElectrolyte(stack.copy(), 0);
						materialsBattery.setEnergecticValues();
						materialsBattery.electrolyteMP.updateValueAndMaterials(world ,stack , Blocks.AIR, 1);
						setPlugCapacity(world);
						FluidStack fs = stack.copy();
						int w = MaterialsHandler.getFluidAmountFromStack(materialsBattery.electrolyte, stack);
						fs.amount = stack.amount - w;
						return  fs.amount <= 0 ? new ActionResult(EnumActionResult.SUCCESS, null) : new ActionResult(EnumActionResult.SUCCESS, fs);
					}
				}
			default:
				break;
		}
		return new ActionResult(EnumActionResult.PASS, stack);
	}
	
	public ActionResult<ItemStack> handleConsumeStack(World world, ItemStack stack, InterfaceType type){
        if(stack.isEmpty())
            return new ActionResult(EnumActionResult.PASS, ItemStack.EMPTY);

		switch(type){
			case ELECTRODE :
				if(materialsBattery.electrode1 != null){
					if( materialsBattery.electrode1MP.currentAmount < materialsBattery.electrode1MP.maxAmount ){
						if(MaterialsHandler.isListItemStackSameOreAndSize(materialsBattery.electrode1.listStack, stack, false)){
							Map.Entry<ItemStack,Double> e = MaterialsHandler.getStackAndWeightFromStack(materialsBattery.electrode1, stack);
							if( e == null || ( e!=null && e.getValue() > 1 && materialsBattery.electrode1MP.currentAmount + e.getValue() > materialsBattery.electrode1MP.maxAmount ) )
								return new ActionResult(EnumActionResult.FAIL, stack);
							materialsBattery.electrode1MP.updateValueAndMaterials(world , materialsBattery.electrode1.stackReference, Blocks.AIR, e.getValue() <= 1.0D ? 1 : (int) Math.abs(e.getValue()));
							return stack.getCount() != e.getKey().getCount() ? new ActionResult(EnumActionResult.SUCCESS, ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - e.getKey().getCount())) : new ActionResult(EnumActionResult.SUCCESS, ItemStack.EMPTY);
						}
					}else{
						if(MaterialsHandler.isListItemStackSameOre(materialsBattery.electrode1.listStack, stack, false))
							return new ActionResult(EnumActionResult.PASS, stack);
					}
				}				
				if(materialsBattery.electrode2 != null){
					if( materialsBattery.electrode2MP.currentAmount < materialsBattery.electrode2MP.maxAmount ){
						if(MaterialsHandler.isListItemStackSameOreAndSize(materialsBattery.electrode2.listStack, stack, false)){
							Map.Entry<ItemStack,Double> e = MaterialsHandler.getStackAndWeightFromStack(materialsBattery.electrode2, stack);
							if( e == null || ( e!=null && e.getValue() > 1 && materialsBattery.electrode2MP.currentAmount + e.getValue() > materialsBattery.electrode2MP.maxAmount ) )
								return new ActionResult(EnumActionResult.FAIL, stack);
							materialsBattery.electrode2MP.updateValueAndMaterials(world , materialsBattery.electrode2.stackReference, Blocks.AIR, e.getValue() <= 1.0D ? 1 : (int) Math.abs(e.getValue()));
							return stack.getCount() != e.getKey().getCount() ? new ActionResult(EnumActionResult.SUCCESS, ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - e.getKey().getCount())) : new ActionResult(EnumActionResult.SUCCESS, ItemStack.EMPTY);
						}
					}else{
						if(MaterialsHandler.isListItemStackSameOre(materialsBattery.electrode2.listStack, stack, false))
							return new ActionResult(EnumActionResult.PASS, stack);
					}
				}
				if(materialsBattery.electrode1 == null){
					if(MaterialsHandler.anyMatchElectrodeAndSize(stack)){
						materialsBattery.setElectrodes(stack.copy(), 0, 1);
						materialsBattery.setAnodeAndCathode();
						materialsBattery.setEnergecticValues();
						Map.Entry<ItemStack,Double> e = MaterialsHandler.getStackAndWeightFromStack(materialsBattery.electrode1, stack);
						if( e == null )
							return new ActionResult(EnumActionResult.FAIL, stack);
						materialsBattery.electrode1MP.updateValueAndMaterials(world ,materialsBattery.electrode1.stackReference, Blocks.AIR, e.getValue() <= 1.0D ? 1 : (int) Math.abs(e.getValue()));
						setPlugCapacity(world);
						return stack.getCount() != e.getKey().getCount() ? new ActionResult(EnumActionResult.SUCCESS, ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - e.getKey().getCount())) : new ActionResult(EnumActionResult.SUCCESS, ItemStack.EMPTY);
					}
				}
				if(materialsBattery.electrode2 == null){
					if(MaterialsHandler.anyMatchElectrodeAndSize(stack)){
						materialsBattery.setElectrodes(stack.copy(), 0, 2);
						materialsBattery.setAnodeAndCathode();
						materialsBattery.setEnergecticValues();	
						Map.Entry<ItemStack,Double> e = MaterialsHandler.getStackAndWeightFromStack(materialsBattery.electrode2, stack);
						if( e == null )
							return new ActionResult(EnumActionResult.FAIL, stack);
						materialsBattery.electrode2MP.updateValueAndMaterials(world , materialsBattery.electrode2.stackReference, Blocks.AIR, e.getValue() <= 1.0D ? 1 : (int) Math.abs(e.getValue()));
						setPlugCapacity(world);						
						return stack.getCount() != e.getKey().getCount() ? new ActionResult(EnumActionResult.SUCCESS, ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - e.getKey().getCount())) : new ActionResult(EnumActionResult.SUCCESS, ItemStack.EMPTY);
					}
				}
			case ELECTROLYTE :
				if(materialsBattery.electrolyte != null){
					if( materialsBattery.electrolyteMP.currentAmount < materialsBattery.electrolyteMP.maxAmount ){
						if(MaterialsHandler.isListItemStackSameOreAndSize(materialsBattery.electrolyte.listStack, stack, false)){
							Map.Entry<ItemStack,Double> e = MaterialsHandler.getStackAndWeightFromStack(materialsBattery.electrolyte, stack);
							if( e == null || ( e!=null && e.getValue() > 1 && materialsBattery.electrolyteMP.currentAmount + e.getValue() > materialsBattery.electrolyteMP.maxAmount ) )
								return new ActionResult(EnumActionResult.FAIL, stack);
							materialsBattery.electrolyteMP.updateValueAndMaterials(world , materialsBattery.electrolyte.stackReference, Blocks.AIR, e.getValue() <= 1.0D ? 1 : (int) Math.abs(e.getValue()));
							return stack.getCount() != e.getKey().getCount() ? new ActionResult(EnumActionResult.SUCCESS, ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - e.getKey().getCount())) : new ActionResult(EnumActionResult.SUCCESS, ItemStack.EMPTY);
						}
					}
				}
				if(materialsBattery.electrolyte == null){
					if(MaterialsHandler.anyMatchElectrolyteAndSize(stack)){
						materialsBattery.setElectrolyte(stack.copy(), 0);
						materialsBattery.setEnergecticValues();
						Map.Entry<ItemStack,Double> e = MaterialsHandler.getStackAndWeightFromStack(materialsBattery.electrolyte, stack);
						if( e == null )
							return new ActionResult(EnumActionResult.FAIL, stack);
						materialsBattery.electrolyteMP.updateValueAndMaterials(world , materialsBattery.electrolyte.stackReference, Blocks.AIR, e.getValue() <= 1.0D ? 1 : (int) Math.abs(e.getValue()));
						setPlugCapacity(world);
						return stack.getCount() != e.getKey().getCount() ? new ActionResult(EnumActionResult.SUCCESS, ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() -e.getKey().getCount())) : new ActionResult(EnumActionResult.SUCCESS, ItemStack.EMPTY);
					}
				}
			default:
				break;
		}
		
		return new ActionResult(EnumActionResult.PASS, stack);
	}
	
	private void setupMaterials(World world){
		if( mapFacingConductiveItemStack.isEmpty() || mapFacingMaterialAmount.isEmpty() || mapFacingMaterialB3D.isEmpty())
			return;
		ItemStack stco = null;
		EnumFacing fco = null;

		for (Map.Entry<EnumFacing, ItemStack> entry : mapFacingConductiveItemStack.entrySet()){
			if(fco != null){
				materialsBattery.setLimit(mapFacingMaterialB3D.get(fco), mapFacingMaterialB3D.get(entry.getKey()));
				materialsBattery.setConductives(stco, entry.getValue());
			}
			if(mapFacingMaterialItemStack.get(entry.getKey()) != null)
				materialsBattery.setElectrodes(mapFacingMaterialItemStack.get(entry.getKey()).copy(), mapFacingMaterialAmount.get(entry.getKey()), fco == null ? 1 : 2);
			
			stco = entry.getValue();
			fco = entry.getKey();
		}
		materialsBattery.setAnodeAndCathode();
		materialsBattery.setElectrolyte(electrolyteItemStack, amountElectrolyte);
		materialsBattery.setEnergecticValues();
		//interfacePos.stream().forEach((in)->setUpMaterialsInterface(world, in));
	}
	
	/*private void setUpMaterialsInterface(World world, BlockPos in) {
		TileEntity s = world.getTileEntity(in);
		if(s == null || s instanceof TileInterface == false)
			return;
		((TileInterface)s).setUpMaterials(materialsBattery.electrode1, materialsBattery.electrode2, materialsBattery.electrolyte);
		return;
	}*/

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
		amountElectrolyte = 0;
		mapFacingConductiveItemStack.clear();
		mapFacingMaterialItemStack.clear();
		mapFacingMaterialAmount.clear();
		mapFacingMaterialB3D.clear();
		interfacePos.clear();
		dwn = null;
		ues = null;
		isStructured = false;
		isValid = false;
		materialsBattery = new MaterialsBattery();
	}
	
	private boolean checkMaterials(World world) {

		if(dwnElectrolyte == null || uesElectrolyte == null || mapFacingConductiveItemStack.isEmpty())
			return false;
		
		EnumFacing tf = mapFacingConductiveItemStack.entrySet().iterator().next().getKey();
		BlockPos nbp = plugPos.add(plugFacingOpposite.getDirectionVec()).add(tf.getDirectionVec());
		
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
					if(s.getBlock() == Blocks.AIR || isBlockFluidReplaceable(s) )
						continue;
					if( ( b1 != null && MaterialsHandler.areItemStackSameOre(n, b1, true) ) || ( b1 == null && MaterialsHandler.anyMatchElectrode(n) ) ){
						amountb1++;
						if(b1 == null)b1 = n.copy();
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
					if(s.getBlock() == Blocks.AIR || isBlockFluidReplaceable(s) )
						continue;
					if( ( b2 != null && MaterialsHandler.areItemStackSameOre(n, b2, true) ) || ( b2 == null && MaterialsHandler.anyMatchElectrode(n) ) ){
						amountb2++;
						if(b2 == null)b2 = n.copy();
					}else
						return false;
				}
			}
		}
		
		if(b1 != null)
			mapFacingMaterialItemStack.put(fb1, b1);
		mapFacingMaterialAmount.put(fb1, amountb1);
		mapFacingMaterialB3D.put(fb1, new B3D(new BlockPos(dwn.getX()+1, dwn.getY()+1, dwn.getZ()+1),new BlockPos(uesElectrolyte.getX()-1,uesElectrolyte.getY()-1,uesElectrolyte.getZ()-1)));
		
		if(b2 != null)
			mapFacingMaterialItemStack.put(fb2, b2);
		mapFacingMaterialAmount.put(fb2, amountb2);
		mapFacingMaterialB3D.put(fb2, new B3D(new BlockPos(dwnElectrolyte.getX()+1,dwnElectrolyte.getY()+1,dwnElectrolyte.getZ()+1),new BlockPos(ues.getX()-1, ues.getY()-1, ues.getZ()-1)));
		
		return true;
	}

	private boolean checkElectrolyte(World world) {
		int di = plugFacingOpposite.getAxisDirection().getOffset();
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
		
		switch(plugFacingOpposite.getAxis()){
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
		materialsBattery.electrolyteMP.setMaterialLimit(new B3D(new BlockPos(xmin,ymin,zmin),new BlockPos(xmax,ymax,zmax)));
		
		dwnElectrolyte = new BlockPos(xmin+xmina,ymin+ymina,zmin+zmina);
		uesElectrolyte = new BlockPos(xmax+xmaxa,ymax+ymaxa,zmax+zmaxa);
		ItemStack elec = null;
		for (int x = xmin ; x <= xmax ; x++ ){
			for (int y = ymin ; y <= ymax ; y++ ){
				for (int z = zmin ; z <= zmax ; z++ ){
					IBlockState s = world.getBlockState(new BlockPos(x,y,z));
					ItemStack n = new ItemStack(s.getBlock());
					if(s.getBlock() == Blocks.AIR || isBlockFluidReplaceable(s) )
						continue;
					if( ( electrolyteItemStack != null && MaterialsHandler.areItemStackSameOre(n, electrolyteItemStack, true) ) || ( electrolyteItemStack == null && MaterialsHandler.anyMatchElectrolyte(n) ) ){
						amountElectrolyte++;
						if(electrolyteItemStack == null)electrolyteItemStack = n.copy();
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
		if(MaterialsHandler.areItemStackSameOre(conductiveRef, new ItemStack(nextState.getBlock(),1,nextState.getBlock().getMetaFromState(nextState)), true)){
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
		if(s!=null && s.getBlock() instanceof BlockConductive){
			world.setBlockState(bp, s.withProperty(ABlockBBStructure.ISSTRUCT, true).withProperty(BlockConductive.FACING,plugFacingOpposite));
		}
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
		//System.out.println("....................... x");
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
		//System.out.println("....................... y");
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
		//System.out.println("....................... z");
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
		ItemStack ns = new ItemStack(s.getBlock(),1,s.getBlock().getMetaFromState(s));
		boolean alcond = MaterialsHandler.anyMatchConductive(ns);

		if(allowedInterfaceBlock.contains(s.getBlock())){
			interfacePos.add(bp);
		}
		if(!allowedStructureBlock.contains(s.getBlock()) && !alcond)
			return false;
		if(allowedStructureBlock.contains(s.getBlock()) && s.getBlock() instanceof ABlockBBStructure == true){
			if(s.getValue(ABlockBBStructure.ISSTRUCT) == true && !isStructured)
				return false;
			if(s.getValue(ABlockBBStructure.ISSTRUCT) == false && isStructured)
				return false;
		} 
		if(alcond){
			if( mapFacingConductiveItemStack.entrySet().stream().noneMatch((f)->MaterialsHandler.areItemStackSameOre(f.getValue(), ns, true)) == true || isConductiveAllowedHere(bp) == false)
				return false;
		}
		return true;
	}
	
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        /*nbt.setInteger("plugx", plugPos.getX());
        nbt.setInteger("plugy", plugPos.getY());
        nbt.setInteger("plugz", plugPos.getZ());*/
        
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
		
		nbt.setInteger("interfacePosSize", interfacePos.size());
		for (int i = 0; i < interfacePos.size(); i++) {
			NBTTagCompound t = new NBTTagCompound();
	        t.setInteger("ix", interfacePos.get(i).getX());
	        t.setInteger("iy", interfacePos.get(i).getY());
	        t.setInteger("iz", interfacePos.get(i).getZ());
			nbt.setTag("interfacePos"+i, t);
		}
		
    	if(plugFacingOpposite!=null)
    		nbt.setInteger("plugFacingOpposite", plugFacingOpposite.getIndex());
    	if(electrolyteFacing!=null)
    		nbt.setInteger("electrolyteFacing", electrolyteFacing.getIndex());
    	if(electrolyteItemStack!=null)
			nbt.setTag("electrolyteItemStack", electrolyteItemStack.writeToNBT(new NBTTagCompound()));
    	
		nbt.setInteger("amountElectrolyte", amountElectrolyte);
        nbt.setBoolean("isValid", isValid);
        nbt.setBoolean("isStructured", isStructured);
        materialsBattery.writeToNBT(nbt);
        return nbt;
    }
    
    public void readFromNBT(NBTTagCompound nbt) {
    	//plugPos = new BlockPos(nbt.getInteger("plugx"),nbt.getInteger("plugy"),nbt.getInteger("plugz"));
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
        
		for (int i = 0; i < nbt.getInteger("interfacePosSize"); i++) {
			NBTTagCompound t = (NBTTagCompound) nbt.getTag("interfacePos"+i);
			interfacePos.add(new BlockPos(t.getInteger("ix"),t.getInteger("iy"),t.getInteger("iz")));
		}
		
        NBTBase es = nbt.getTag("electrolyteItemStack");
        if(es != null)
        	electrolyteItemStack = new ItemStack((NBTTagCompound)es);

        amountElectrolyte = nbt.getInteger("amountElectrolyte");
        plugFacingOpposite = EnumFacing.getFront(nbt.getInteger("plugFacingOpposite"));
        electrolyteFacing = EnumFacing.getFront(nbt.getInteger("electrolyteFacing"));
        isValid = nbt.getBoolean("isValid");
        isStructured = nbt.getBoolean("isStructured");
        materialsBattery.readFromNBT(nbt);
    }

	public String getStringSize() {
		return "X : "+(ues.getX()-dwn.getX())+" Y : "+(ues.getY()-dwn.getY())+" Z : "+(ues.getZ()-dwn.getZ());
	}
}
