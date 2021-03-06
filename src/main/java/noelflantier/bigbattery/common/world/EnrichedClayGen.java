package noelflantier.bigbattery.common.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import noelflantier.bigbattery.common.handlers.ModBlocks;
import noelflantier.bigbattery.common.handlers.ModConfig;

public class EnrichedClayGen extends WorldGenerator{
	
	public static final int ymin = 55;
	public static final int ymax = 65;
	public static int trueGen = 3;
	//2807071691121312404
	public void generate(World worldIn, Random rand, int xch, int zch) {
		List<Integer> l = new ArrayList<Integer>();
		int i = 0;
		while(l.size() < ModConfig.chanceSpawningEnrichedClayPerChunk){
			if(i >= 50)
				break;
			int r = rand.nextInt(ymax-ymin)+ymin;
			if(!l.contains(r))
				l.add(r);
			i++;
		}
		
		int cpt = 0;
		for(int j = 0 ; j < l.size() ; j++){
			if( generate(worldIn, rand, new BlockPos(xch + rand.nextInt(16),l.get(j),zch + rand.nextInt(16))) )
				cpt += 1;
			if( cpt > trueGen)
				break;
		}
		
	}
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		
		/*float f = rand.nextFloat() * (float)Math.PI;
        double d0 = (double)((float)(position.getX() + 8) + MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
        double d1 = (double)((float)(position.getX() + 8) - MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
        double d2 = (double)((float)(position.getZ() + 8) + MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
        double d3 = (double)((float)(position.getZ() + 8) - MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
        double d4 = (double)(position.getY() + rand.nextInt(3) - 2);
        double d5 = (double)(position.getY() + rand.nextInt(3) - 2);

        for (int i = 0; i < this.numberOfBlocks; ++i)
        {
            float f1 = (float)i / (float)this.numberOfBlocks;
            double d6 = d0 + (d1 - d0) * (double)f1;
            double d7 = d4 + (d5 - d4) * (double)f1;
            double d8 = d2 + (d3 - d2) * (double)f1;
            double d9 = rand.nextDouble() * (double)this.numberOfBlocks / 16.0D;
            double d10 = (double)(MathHelper.sin((float)Math.PI * f1) + 1.0F) * d9 + 1.0D;
            double d11 = (double)(MathHelper.sin((float)Math.PI * f1) + 1.0F) * d9 + 1.0D;
            int j = MathHelper.floor(d6 - d10 / 2.0D);
            int k = MathHelper.floor(d7 - d11 / 2.0D);
            int l = MathHelper.floor(d8 - d10 / 2.0D);
            int i1 = MathHelper.floor(d6 + d10 / 2.0D);
            int j1 = MathHelper.floor(d7 + d11 / 2.0D);
            int k1 = MathHelper.floor(d8 + d10 / 2.0D);

            for (int l1 = j; l1 <= i1; ++l1)
            {
                double d12 = ((double)l1 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D)
                {
                    for (int i2 = k; i2 <= j1; ++i2)
                    {
                        double d13 = ((double)i2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D)
                        {
                            for (int j2 = l; j2 <= k1; ++j2)
                            {
                                double d14 = ((double)j2 + 0.5D - d8) / (d10 / 2.0D);

                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D)
                                {
                                    BlockPos blockpos = new BlockPos(l1, i2, j2);
                                    IBlockState state = worldIn.getBlockState(blockpos);
                            		if (worldIn.getBlockState(position).getBlock() == Blocks.CLAY || worldIn.getBlockState(position).getBlock() == Blocks.WATER)
                                    {
                                    	worldIn.setBlockState(blockpos, ModBlocks.blockEnrichedClay.getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }*/
		
        if (/*worldIn.getBlockState(position).getMaterial() != Material.WATER &&*/ worldIn.getBlockState(position).getBlock() != Blocks.CLAY)
        {
            return false;
        }
        else
        {
            int i = rand.nextInt(ModConfig.maximumEnrichedClayVainSize - 5) + 5;
            int j = 1;

            for (int k = position.getX() - i; k <= position.getX() + i; ++k)
            {
                for (int l = position.getZ() - i; l <= position.getZ() + i; ++l)
                {
                    int i1 = k - position.getX();
                    int j1 = l - position.getZ();

                    if (i1 * i1 + j1 * j1 <= i * i)
                    {
                        for (int k1 = position.getY() - 1; k1 <= position.getY() + 1; ++k1)
                        {
                            BlockPos blockpos = new BlockPos(k, k1, l);
                            Block block = worldIn.getBlockState(blockpos).getBlock();

                            if (block == Blocks.DIRT || block == Blocks.CLAY)
                            {
                                worldIn.setBlockState(blockpos, ModBlocks.blockEnrichedClay.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
	}

}
