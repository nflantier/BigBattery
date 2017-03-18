package noelflantier.bigbattery.client.gui.manual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noelflantier.bigbattery.client.bases.GuiComponent;
import noelflantier.bigbattery.client.bases.GuiRecipe;
import noelflantier.bigbattery.common.blocks.BlockConductive;
import noelflantier.bigbattery.common.blocks.BlockInterface;
import noelflantier.bigbattery.common.handlers.ModBlocks;
import noelflantier.bigbattery.common.handlers.ModItems;
import noelflantier.bigbattery.common.handlers.ModProperties.ConductiveType;
import noelflantier.bigbattery.common.handlers.ModProperties.InterfaceType;

public class NBlocksAndItems  extends ABaseCategory{

	public static final String PRE_IB = "PRE_IB";
	public static float scale = 0.6F;
	public NBlocksAndItems(String name, int x, int y) {
		super(name,x,y);
		initComponent();
	}
	
	@Override
	public void initComponent() {
		Comparator<ItemStack> comparator = (i1,i2)-> (int)I18n.format(i1.getItem().getUnlocalizedName(i1)+".name").charAt(0)-(int)I18n.format(i2.getItem().getUnlocalizedName(i2)+".name").charAt(0);
		List<ItemStack> vanilla = new ArrayList<ItemStack>();
		vanilla.add(new ItemStack(ModBlocks.blockAluminium,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockCalcium,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockCopper,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockGraphite,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockLead,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockLithium,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockLithiumGraphite,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockNickel,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockPlatinium,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockSilver,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockTin,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockZinc,1,0));

		vanilla.add(new ItemStack(ModBlocks.blockPlug,1,0));
		for(int i = 0 ; i < ConductiveType.values().length ; i++){
    		vanilla.add(new ItemStack(ModBlocks.blockConductive,1,ModBlocks.blockConductive.getMetaFromState(ModBlocks.blockConductive.getDefaultState().withProperty(BlockConductive.CONDUCTIVE_TYPE, ConductiveType.getType(i)))));
    	}
    	for(int i = 0 ; i < InterfaceType.values().length ; i++){
    		vanilla.add(new ItemStack(ModBlocks.blockInterface,1,ModBlocks.blockInterface.getMetaFromState(ModBlocks.blockInterface.getDefaultState().withProperty(BlockInterface.INTERFACE_TYPE, InterfaceType.getType(i)))));
    	}
		vanilla.add(new ItemStack(ModBlocks.blockCasing,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockCasing,1,2));
		vanilla.add(new ItemStack(ModBlocks.blockIonicCalcium,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockIonicChlore,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockIonicDragonBreath,1,0));
		vanilla.add(new ItemStack(ModBlocks.blockIonicLithium,1,0));
		
		vanilla.add(new ItemStack(ModItems.itemChloreFilter,1,0));
		vanilla.add(new ItemStack(ModItems.itemDustCalcium,1,0));
		vanilla.add(new ItemStack(ModItems.itemIngotSteel,1,0));
		
		
		int dec = 0 ;
		this.componentList.put("vanilla", new GuiComponent(this.x+10+100*(int)((dec)/25), this.y+30+(int)(10*scale)*((dec)%25), 90, (int)(10*scale)){{
			globalScale = scale;
			addText("VANILLA RECIPES :", 0, 0);
		}});
		dec+=1;
		for(int i = 0 ; i < vanilla.size() ; i++){
			ItemStack it = vanilla.get(i);
			String strID = PRE_IB+it.getItem().getUnlocalizedName(it);
			String translated = I18n.format(it.getItem().getUnlocalizedName(it)+".name");
			this.componentList.put(strID, new GuiComponent(this.x+10+100*(int)((i+dec)/25), this.y+30+(int)(10*scale)*((i+dec)%25), 90, (int)(10*scale)){{
				globalScale = scale;
				addText(translated, 0, 0);
				isLink = true;
			}});
			listCategory.put(strID, new DummyCategory(strID,this.x, this.y){{
				addComponent("in",
					new GuiComponent(x+10, y+30, 100, 10){{
						addRecipe(new GuiRecipe(translated, this.x,this.y,it,GuiRecipe.TYPE.VANILLA));
					}});
				}}
			);
		}

		dec = dec+vanilla.size()+1;
		this.componentList.put("furnace", new GuiComponent(this.x+10+100*(int)((dec)/25), this.y+30+(int)(10*scale)*((dec)%25), 90, (int)(10*scale)){{
			globalScale = scale;
			addText("FURNACE RECIPES :", 0, 0);
		}});
		dec+=1;
		Map<ItemStack,List<ItemStack>> furnace = new HashMap<ItemStack,List<ItemStack>>();
		
		furnace.put(new ItemStack(ModItems.itemIonicAgent,8,0), Arrays.asList(new ItemStack(Items.FERMENTED_SPIDER_EYE,1,0)));
		furnace.put(new ItemStack(ModItems.itemIngotSteel,1,0), Arrays.asList(new ItemStack(Items.IRON_INGOT,1,0)));
		furnace.put(new ItemStack(ModItems.itemIngotGraphite,1,0), Arrays.asList(new ItemStack(Items.COAL,1,0), new ItemStack(Items.COAL,1,1)));
		furnace.put(new ItemStack(ModItems.itemIngotAluminium,1,0), Arrays.asList(new ItemStack(ModBlocks.blockOreAluminium,1,0)));
		furnace.put(new ItemStack(ModItems.itemIngotCopper,1,0), Arrays.asList(new ItemStack(ModBlocks.blockOreCopper,1,0)));
		furnace.put(new ItemStack(ModItems.itemIngotLead,1,0), Arrays.asList(new ItemStack(ModBlocks.blockOreLead,1,0)));
		furnace.put(new ItemStack(ModItems.itemIngotNickel,1,0), Arrays.asList(new ItemStack(ModBlocks.blockOreNickel,1,0)));
		furnace.put(new ItemStack(ModItems.itemIngotPlatinium,1,0), Arrays.asList(new ItemStack(ModBlocks.blockOrePlatinium,1,0)));
		furnace.put( new ItemStack(ModItems.itemIngotSilver,1,0), Arrays.asList(new ItemStack(ModBlocks.blockOreSilver,1,0)));
		furnace.put(new ItemStack(ModItems.itemIngotTin,1,0), Arrays.asList(new ItemStack(ModBlocks.blockOreTin,1,0)));
		furnace.put(new ItemStack(ModItems.itemIngotZinc,1,0), Arrays.asList(new ItemStack(ModBlocks.blockOreZinc,1,0)));
		
		
		int j = 0 ;
		for(Map.Entry<ItemStack, List<ItemStack>> entry : furnace.entrySet()){
			String strID = PRE_IB+entry.getKey().getItem().getUnlocalizedName(entry.getKey());
			String translated = I18n.format(entry.getKey().getItem().getUnlocalizedName(entry.getKey())+".name");
			this.componentList.put(strID, new GuiComponent(this.x+10+100*(int)((j+dec)/25), this.y+30+(int)(10*scale)*((j+dec)%25), 90, (int)(10*scale)){{
				globalScale = scale;
				addText(translated, 0, 0);
				isLink = true;
			}});
			listCategory.put(strID, new DummyCategory(strID,this.x, this.y){{
				for(int k = 0 ; k < entry.getValue().size() ; k++){
					int f = k;
					addComponent("in"+f,
						new GuiComponent(x+10+80*f, y+30, 100, 10){{
							addRecipe(new GuiRecipe(translated, this.x,this.y,GuiRecipe.getGuiItemStack(new ArrayList<ItemStack>(){{add(entry.getKey());}}),GuiRecipe.TYPE.FURNACE,GuiRecipe.getGuiItemStack(new ArrayList<ItemStack>(){{add(entry.getValue().get(f));}})));
						}});
				}
				}}
			);
			j++;
		}
	}

}
