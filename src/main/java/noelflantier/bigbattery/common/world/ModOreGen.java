package noelflantier.bigbattery.common.world;

import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import noelflantier.bigbattery.common.handlers.ModBlocks;

public class ModOreGen implements IWorldGenerator{
	
	public EnrichedClayGen enClayGen = new EnrichedClayGen();
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.getDimension()) {
			case 0:
				generateSurface(random, chunkX * 16, chunkZ * 16, world);
				break;
			default:
				generateSurface(random, chunkX * 16, chunkZ * 16, world);
				break;
		}
	}

	private void generateSurface(Random random, int chunkx, int chunckz, World world) {
		//addOreSpawn(ModBlocks.blockEnrichedClay, Blocks.CLAY, world, random, chunkx, chunckz, 4, 12, 10, 50, 70);
		enClayGen.generate(world, random, chunkx, chunckz);
		addOreSpawn(ModBlocks.blockOreAluminium, Blocks.STONE, world, random, chunkx, chunckz, 3, 14, 10, 40, 80);
		addOreSpawn(ModBlocks.blockOreCopper, Blocks.STONE, world, random, chunkx, chunckz, 4, 16, 10, 40, 80);
		addOreSpawn(ModBlocks.blockOreLead, Blocks.STONE, world, random, chunkx, chunckz, 3, 5, 10, 20, 80);
		addOreSpawn(ModBlocks.blockOreNickel, Blocks.STONE, world, random, chunkx, chunckz, 1, 3, 1, 10, 40);
		addOreSpawn(ModBlocks.blockOrePlatinium, Blocks.STONE, world, random, chunkx, chunckz, 1, 2, 1, 10, 20);
		addOreSpawn(ModBlocks.blockOreSilver, Blocks.STONE, world, random, chunkx, chunckz, 2, 6, 10, 20, 80);
		addOreSpawn(ModBlocks.blockOreTin, Blocks.STONE, world, random, chunkx, chunckz, 3, 8, 10, 20, 80);
		addOreSpawn(ModBlocks.blockOreZinc, Blocks.STONE, world, random, chunkx, chunckz, 2, 6, 10, 20, 80);

	}
	
	private static Predicate<IBlockState> isBlockCanBeReplaced(Class<? extends Block> cl) {
        return p -> p.getBlock().getClass().equals(cl);
    }
	
	private void addOreSpawn(final Block block, final Block blockToReplace, final World world, final Random random, final int chunkXPos, final int chunkZPos, final int minVainSize, final int maxVainSize, final int chancesToSpawn, final int minY, final int maxY) {
		for (int i = 0; i < chancesToSpawn; i++) {
			final int posX = chunkXPos + random.nextInt(16);
			final int posY = minY + random.nextInt(maxY - minY);
			final int posZ = chunkZPos + random.nextInt(16);
			new WorldGenMinable(block.getDefaultState(), minVainSize + random.nextInt(maxVainSize - minVainSize), isBlockCanBeReplaced(blockToReplace.getClass())).generate(world, random, new BlockPos(posX, posY, posZ));
		}
	}
}
