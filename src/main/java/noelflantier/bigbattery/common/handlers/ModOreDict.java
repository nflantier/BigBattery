package noelflantier.bigbattery.common.handlers;

import net.minecraftforge.oredict.OreDictionary;

public class ModOreDict {

	public static void preInitCommon() {
		OreDictionary.registerOre("blockAluminium", ModBlocks.blockAluminium);
		OreDictionary.registerOre("blockCopper", ModBlocks.blockCopper);
		OreDictionary.registerOre("blockGraphite", ModBlocks.blockGraphite);
		OreDictionary.registerOre("blockLead", ModBlocks.blockLead);
		OreDictionary.registerOre("blockNickel", ModBlocks.blockNickel);
		OreDictionary.registerOre("blockPlatinium", ModBlocks.blockPlatinium);
		OreDictionary.registerOre("blockSilver", ModBlocks.blockSilver);
		OreDictionary.registerOre("blockTin", ModBlocks.blockTin);
		OreDictionary.registerOre("blockZinc", ModBlocks.blockZinc);
		
		OreDictionary.registerOre("oreAluminium", ModBlocks.blockOreAluminium);
		OreDictionary.registerOre("oreCopper", ModBlocks.blockOreCopper);
		OreDictionary.registerOre("oreLead", ModBlocks.blockOreLead);
		OreDictionary.registerOre("oreNickel", ModBlocks.blockOreNickel);
		OreDictionary.registerOre("orePlatinium", ModBlocks.blockOrePlatinium);
		OreDictionary.registerOre("oreSilver", ModBlocks.blockOreSilver);
		OreDictionary.registerOre("oreTin", ModBlocks.blockOreTin);
		OreDictionary.registerOre("oreZinc", ModBlocks.blockOreZinc);
		
		OreDictionary.registerOre("ingotAluminium", ModItems.itemIngotAluminium);
		OreDictionary.registerOre("ingotCopper", ModItems.itemIngotCopper);
		OreDictionary.registerOre("ingotGraphite", ModItems.itemIngotGraphite);
		OreDictionary.registerOre("ingotLead", ModItems.itemIngotLead);
		OreDictionary.registerOre("ingotNickel", ModItems.itemIngotNickel);
		OreDictionary.registerOre("ingotPlatinium", ModItems.itemIngotPlatinium);
		OreDictionary.registerOre("ingotSilver", ModItems.itemIngotSilver);
		OreDictionary.registerOre("ingotSteel", ModItems.itemIngotSteel);
		OreDictionary.registerOre("ingotTin", ModItems.itemIngotTin);
		OreDictionary.registerOre("ingotZinc", ModItems.itemIngotZinc);
	}
}
