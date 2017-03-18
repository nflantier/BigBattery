package noelflantier.bigbattery.client.gui.manual;

import java.util.ArrayList;
import java.util.Map;

import net.minecraft.item.ItemStack;
import noelflantier.bigbattery.client.bases.GuiComponent;
import noelflantier.bigbattery.client.bases.GuiItemStack;
import noelflantier.bigbattery.client.bases.GuiRecipe;
import noelflantier.bigbattery.common.blocks.BlockConductive;
import noelflantier.bigbattery.common.blocks.BlockInterface;
import noelflantier.bigbattery.common.handlers.ModBlocks;
import noelflantier.bigbattery.common.handlers.ModConfig;
import noelflantier.bigbattery.common.handlers.ModItems;
import noelflantier.bigbattery.common.handlers.ModProperties.ConductiveType;
import noelflantier.bigbattery.common.handlers.ModProperties.InterfaceType;
import noelflantier.bigbattery.common.materials.MaterialsHandler;
import noelflantier.bigbattery.common.materials.MaterialsHandler.Conductive;
import noelflantier.bigbattery.common.materials.MaterialsHandler.Electrode;
import noelflantier.bigbattery.common.materials.MaterialsHandler.Electrolyte;

public class NIndexManual extends ABaseCategory{
	
	public static final String CAT_BATTERY = "BATTERY";
	public static final String CAT_STRUCTURE = "STRUCTURE";
	public static final String CAT_ELECTRODE = "ELECTRODE";
	public static final String CAT_ELECTROLYTE = "ELECTROLYTE";
	public static final String CAT_CONDUCTIVE = "CONDUCTIVE";
	public static final String CAT_INTERFACE = "INTERFACE";
	public static final String CAT_OTHER_MATERIALS = "OTHER_MATERIALS";
	public static final String CAT_ITEMS_AND_BLOCKS = "ITEMS_AND_BLOCKS";
	public static NBlocksAndItems IAB_MANUAL;
	
	public NIndexManual(String name, int x, int y) {
		super(name,x,y);
		IAB_MANUAL = new NBlocksAndItems(CAT_ITEMS_AND_BLOCKS,this.x, this.y);
		initComponent();
	}
	
	@Override
	public void initComponent() {
		
		this.componentList.put(CAT_BATTERY, new GuiComponent(this.x+10, this.y+30, 100, 10){{
			addText("Battery", 0, 0);
			isLink = true;
		}});
		listCategory.put(CAT_BATTERY, new DummyCategory(CAT_BATTERY,this.x, this.y){{
			addComponent("in",
				new GuiComponent(x+10, y+30, 100, 10){{
					addTextAutoWitdh("Battery are multiblock structure.", 0, 0, NGuiManual.maxStringWidth);
					addTextAutoWitdh("To build a battery you'll need at least a plug, one or two conductive types max and some casing.", 0, 0, NGuiManual.maxStringWidth);
					addTextAutoWitdh("Adding materials and interfaces is optionnal. If you dont have interfaces you'll have to place the materials manualy. "
							+ "Interfaces help you to automate this process.", 0, 0, NGuiManual.maxStringWidth);
					addItemStack(new GuiItemStack(x+0,y+65,new ItemStack(ModBlocks.blockPlug,1,0)));
					for(int i = 0 ; i < ConductiveType.values().length ; i++){
						ItemStack it = new ItemStack(ModBlocks.blockConductive,1,ModBlocks.blockConductive.getMetaFromState(ModBlocks.blockConductive.getDefaultState().withProperty(BlockConductive.CONDUCTIVE_TYPE, ConductiveType.getType(i))));
			    		addItemStack(new GuiItemStack(x+i*20,y+85,it));
			    	}
					addItemStack(new GuiItemStack(x+0,y+105,new ItemStack(ModBlocks.blockCasing,1,0)));
					addItemStack(new GuiItemStack(x+20,y+105,new ItemStack(ModBlocks.blockCasing,1,2)));
					for(int i = 0 ; i < InterfaceType.values().length ; i++){
			    		addItemStack(new GuiItemStack(x+i*20,y+125,new ItemStack(ModBlocks.blockInterface,1,ModBlocks.blockInterface.getMetaFromState(ModBlocks.blockInterface.getDefaultState().withProperty(BlockInterface.INTERFACE_TYPE, InterfaceType.getType(i))))));
			    	}
				}});
			}}
		);

		this.componentList.put(CAT_STRUCTURE, new GuiComponent(this.x+10, this.y+40, 100, 10){{
			addText("Structure", 0, 0);
			isLink = true;
		}});
		listCategory.put(CAT_STRUCTURE, new DummyCategory(CAT_STRUCTURE,this.x, this.y){{
			addComponent("in",
				new GuiComponent(x+10, y+30, 100, 10){{
					addTextAutoWitdh("Structure obligations : next to the plug on one axis only through all the length, you'll have to add conductives "
							+ "( two type one for each sides or one type for both sides, you cant mix conductives on one side ).", 0, 0, NGuiManual.maxStringWidth);
					addTextAutoWitdh("Structure cancellations : any not allowed materials or structure blocks in the structure, any conductive not in the "
							+ "right place.", 0, 0, NGuiManual.maxStringWidth);					
					addTextAutoWitdh("Maximun size for a battery : "+ModConfig.maxSizeBattery+" by "+ModConfig.maxSizeBattery+" by "+ModConfig.maxSizeBattery+".", 0, 0, NGuiManual.maxStringWidth);
					addTextAutoWitdh("Minium size : 3 by 3 by 5.", 0, 0, NGuiManual.maxStringWidth);
					addTextAutoWitdh("To activate your battery right click on the plug with an empty hand, if the structure is valid the battery will be set and texture will connect.", 0, 0, NGuiManual.maxStringWidth);
				}});
			}}
		);
		this.componentList.put(CAT_ELECTRODE, new GuiComponent(this.x+10, this.y+50, 100, 10){{
			addText("Electrodes", 0, 0);
			isLink = true;
		}});
		listCategory.put(CAT_ELECTRODE, new DummyCategory(CAT_ELECTRODE,this.x, this.y){{
			addComponent("in",
				new GuiComponent(x+32, y+30, 100, 10){{
					yButtonRecipes = 70;
					xButtonRecipes = 95;
					nbGuiRecipeVertical = 1;
					nbGuiRecipeHorizontal = 3;
					handleRecipesGroup = true;
					int i = 0;
					for(Map.Entry<Integer,Electrode> entry : MaterialsHandler.electrodeListByPotential.entrySet()){
						addRecipe(new GuiRecipe(entry.getValue().name+" E:"+entry.getValue().potential+" O:"+entry.getValue().oxydationNumber.size(),x,y,GuiRecipe.getGuiItemStack(new ArrayList<ItemStack>(){{add(entry.getValue().stackReference);}}),GuiRecipe.TYPE.VANILLA,GuiRecipe.getGuiItemStack(entry.getValue().listStack)));
						i++;
					}
				}});
			addComponent("te",
				new GuiComponent(x+10, y+130, 100, 10){{
					addTextAutoWitdh("Electrodes have different potential (E). Anode will be the electrode with the least potential, Cathode will be the other one. The bigger the "
							+ "potential difference is the more RF you will generate. The bigger the oxydation number (O) is the longer the electrode will last.", 0, 0, NGuiManual.maxStringWidth);
				}});
			}}
		);
		this.componentList.put(CAT_ELECTROLYTE, new GuiComponent(this.x+10, this.y+60, 100, 10){{
			addText("Electrolytes", 0, 0);
			isLink = true;
		}});
		listCategory.put(CAT_ELECTROLYTE, new DummyCategory(CAT_ELECTROLYTE,this.x, this.y){{
			addComponent("in",
				new GuiComponent(x+32, y+30, 100, 10){{
					yButtonRecipes = 70;
					xButtonRecipes = 95;
					nbGuiRecipeVertical = 1;
					nbGuiRecipeHorizontal = 3;
					handleRecipesGroup = true;
					int i = 0;
					for(Map.Entry<Integer,Electrolyte> entry : MaterialsHandler.electrolyteListByPotential.entrySet()){
						addRecipe(new GuiRecipe(entry.getValue().name+" E:"+entry.getValue().potential+" O:"+entry.getValue().oxydationNumber.size(),x,y,GuiRecipe.getGuiItemStack(new ArrayList<ItemStack>(){{add(entry.getValue().stackReference);}}),GuiRecipe.TYPE.VANILLA,GuiRecipe.getGuiItemStack(entry.getValue().listStack)));
						i++;
					}
				}});
			addComponent("te",
				new GuiComponent(x+10, y+130, 100, 10){{
					addTextAutoWitdh("Electrolyte have different potential (E). The bigger the potential difference is the more RF you will generate. The bigger the "
							+ "oxydation number (O) is the longer the electrolyte will last.", 0, 0, NGuiManual.maxStringWidth);
				}});
			}}
		);
		
		this.componentList.put(CAT_CONDUCTIVE, new GuiComponent(this.x+10, this.y+70, 100, 10){{
			addText("Conductives", 0, 0);
			isLink = true;
		}});
		listCategory.put(CAT_CONDUCTIVE, new DummyCategory(CAT_CONDUCTIVE,this.x, this.y){{
			addComponent("in",
				new GuiComponent(x+10, y+30, 100, 10){{
					for(int i = 0 ; i < ConductiveType.values().length ; i++){
						ItemStack it = new ItemStack(ModBlocks.blockConductive,1,ModBlocks.blockConductive.getMetaFromState(ModBlocks.blockConductive.getDefaultState().withProperty(BlockConductive.CONDUCTIVE_TYPE, ConductiveType.getType(i))));
			    		addItemStack(new GuiItemStack(x+(i%4)*75,y+20*((int)Math.floor(i/4)),it));
			    		MaterialsHandler.Conductive c = MaterialsHandler.getConductiveFromStack(it);
			    		if(c!=null)
			    			addText("RF*"+c.ratioEfficiency, (i%4)*75+18,5-15*(i==0?0:1)+20*((int)Math.floor(i/4)) );
			    	}
					addText("Conductive auto face them selves when they are part", 0, 20);
					addText("of a structure.", 0, 0);
				}});
			}}
		);

		this.componentList.put(CAT_INTERFACE, new GuiComponent(this.x+10, this.y+80, 100, 10){{
			addText("Interfaces", 0, 0);
			isLink = true;
		}});
		listCategory.put(CAT_INTERFACE, new DummyCategory(CAT_INTERFACE,this.x, this.y){{
			addComponent("in",
				new GuiComponent(x+10, y+30, 100, 10){{
					for(int i = 0 ; i < InterfaceType.values().length ; i++){
			    		addItemStack(new GuiItemStack(x+i*20,y,new ItemStack(ModBlocks.blockInterface,1,ModBlocks.blockInterface.getMetaFromState(ModBlocks.blockInterface.getDefaultState().withProperty(BlockInterface.INTERFACE_TYPE, InterfaceType.getType(i))))));
			    	}
				}});
			addComponent("te",
					new GuiComponent(x+10, y+50, 100, 10){{
						addTextAutoWitdh("Interfaces allow you to automate the reffiling of the battery. You can add as many as you want to the structure. Electrode interface accept "
								+ "only electrode materials and can fill both sides. Electrolyte interface accept only electrolyte materials and accept fluid for aqueous electrolyte "
								+ "( you can pipe fluid into it with other mods or you can right click bucket of electrolyte fluid to fill the internal tank ).", 0, 0, NGuiManual.maxStringWidth);
					}});
			}}
		);

		this.componentList.put(CAT_OTHER_MATERIALS, new GuiComponent(this.x+10, this.y+90, 100, 10){{
			addText("Other materials", 0, 0);
			isLink = true;
		}});
		listCategory.put(CAT_OTHER_MATERIALS, new DummyCategory(CAT_OTHER_MATERIALS,this.x, this.y){{
			addComponent("io",
				new GuiComponent(x+30, y+30, 100, 10){{
					addItemStack(new GuiItemStack(x-20,y,new ItemStack(ModItems.itemIonicAgent,1,0)));
					addTextAutoWitdh("Ionic agent is obtained by smelting fermented spider eyes.", 0, 0, NGuiManual.maxStringWidth-20);
				}});
			addComponent("ca",
				new GuiComponent(x+30, y+60, 100, 10){{
					addItemStack(new GuiItemStack(x-20,y,new ItemStack(ModItems.itemDustCalcium,1,0)));
					addTextAutoWitdh("Calicium dust is obtained by crafting bonemeal with water bucket.", 0, 0, NGuiManual.maxStringWidth-20);
				}});
			addComponent("ch",
				new GuiComponent(x+30, y+90, 100, 10){{
					addItemStack(new GuiItemStack(x-20,y,new ItemStack(ModItems.itemDustChlore,1,0)));
					addItemStack(new GuiItemStack(x+78,y+8,new ItemStack(ModItems.itemChloreFilter,1,0)));
					addTextAutoWitdh("Chlore dust is obtained by right clicking chlore filter on water.", 0, 0, NGuiManual.maxStringWidth-20);
				}});
			addComponent("li",
				new GuiComponent(x+30, y+120, 100, 10){{
					addItemStack(new GuiItemStack(x-20,y,new ItemStack(ModItems.itemDustLithium,1,0)));
					addItemStack(new GuiItemStack(x+146,y+28,new ItemStack(ModBlocks.blockEnrichedClay,1,0)));
					addTextAutoWitdh("Lithium dust is obtained by mining enriched clay in the world. Using fortune is almost a must. Enriched "
							+ "clay spawn where clay does normaly spawn but in bigger vain that normal clay.", 0, 0, NGuiManual.maxStringWidth-20);
				}});
			}}
		);
		
		this.componentList.put(CAT_ITEMS_AND_BLOCKS, new GuiComponent(this.x+10, this.y+100, 100, 10){{
			addText("Items and Blocks", 0, 0);
			isLink = true;
		}});
		listCategory.put(CAT_ITEMS_AND_BLOCKS, IAB_MANUAL);
	}
}
