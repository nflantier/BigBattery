package noelflantier.bigbattery.client.gui.manual;

import java.util.ArrayList;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import noelflantier.bigbattery.Ressources;
import noelflantier.bigbattery.client.bases.GuiComponent;
import noelflantier.bigbattery.client.bases.GuiImage;
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
import noelflantier.bigbattery.common.materials.MaterialsHandler.Electrode;
import noelflantier.bigbattery.common.materials.MaterialsHandler.Electrolyte;

public class NIndexManual extends ABaseCategory{
	
	public static final String CAT_BATTERY = "BATTERY";
	public static final String CAT_STRUCTURE = "STRUCTURE";
	public static final String CAT_LAYOUT = "LAYOUT";
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
					addTextAutoWitdh("A full battery will always generate more power, so make sure to fill it up to use the full potential of the battery.", 0, 0, NGuiManual.maxStringWidth);
					addTextAutoWitdh("Battery only generate power if their internal buffer is used.", 0, 0, 100);

					addItemStack(new GuiItemStack(x+100,y+80,new ItemStack(ModBlocks.blockPlug,1,0)));
					for(int i = 0 ; i < ConductiveType.values().length ; i++){
						ItemStack it = new ItemStack(ModBlocks.blockConductive,1,ModBlocks.blockConductive.getMetaFromState(ModBlocks.blockConductive.getDefaultState().withProperty(BlockConductive.CONDUCTIVE_TYPE, ConductiveType.getType(i))));
			    		addItemStack(new GuiItemStack(x+100+i*20,y+96,it));
			    	}
					addItemStack(new GuiItemStack(x+100,y+112,new ItemStack(ModBlocks.blockCasing,1,0)));
					addItemStack(new GuiItemStack(x+20+100,y+112,new ItemStack(ModBlocks.blockCasing,1,2)));
					for(int i = 0 ; i < InterfaceType.values().length ; i++){
			    		addItemStack(new GuiItemStack(x+100+i*20,y+128,new ItemStack(ModBlocks.blockInterface,1,ModBlocks.blockInterface.getMetaFromState(ModBlocks.blockInterface.getDefaultState().withProperty(BlockInterface.INTERFACE_TYPE, InterfaceType.getType(i))))));
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
					addImage(new GuiImage(x+206,y-8,16,16,0F,0F,1F,1F,new ResourceLocation(Ressources.MODID+":textures/blocks/plug_side.png")));
					addImage(new GuiImage(x+52,y+130,16,16,0F,0F,1F,1F,new ResourceLocation(Ressources.MODID+":textures/blocks/plug_up.png")));
					addTextAutoWitdh("Structure obligations : on the plug sides ....... on one axis only through all the length of the battery, you'll have to add conductives "
							+ "( two types one for each sides or one type for both sides, you cant mix conductives on one side ).", 0, 0, NGuiManual.maxStringWidth);
					addTextAutoWitdh("Structure cancellations : any not allowed materials or structure blocks in the structure, any conductive not in the "
							+ "right place.", 0, 0, NGuiManual.maxStringWidth);					
					addTextAutoWitdh("Maximun size for a battery : "+ModConfig.maxSizeBattery+" by "+ModConfig.maxSizeBattery+" by "+ModConfig.maxSizeBattery+".", 0, 0, NGuiManual.maxStringWidth);
					addTextAutoWitdh("Minium size : 3 by 3 by 5.", 0, 0, NGuiManual.maxStringWidth);
					addTextAutoWitdh("To activate your battery right click on the plug with an empty hand, if the structure is valid the battery will be set and textures will connect.", 0, 0, NGuiManual.maxStringWidth);
					addTextAutoWitdh("Plug face ........ should always be outside the structure.", 0, 0, NGuiManual.maxStringWidth);
				}});
			}}
		);


		this.componentList.put(CAT_LAYOUT, new GuiComponent(this.x+10, this.y+50, 100, 10){{
			addText("Layout", 0, 0);
			isLink = true;
		}});
		ResourceLocation layout = new ResourceLocation(Ressources.MODID+":textures/gui/manual/layout.png");
		
		listCategory.put(CAT_LAYOUT, new DummyCategory(CAT_LAYOUT,this.x, this.y){{
			addComponent("in",
				new GuiComponent(x+10, y+30, 100, 10){{
					addTextAutoWitdh("Exemple : 8 x 5 x 3 battery ", 0,0, NGuiManual.maxStringWidth);
					int decy = 10;
					int decx = 0;
					for(int k = 0 ; k < 5 ; k++)
						for(int i = 0 ; i < 8 ; i++)
							addImage(new GuiImage(x+i*8,y+k*8+decy,8,8,0.75F,0F,1F,1F,layout));
					decx += 8*9;
					for(int k = 0 ; k < 5 ; k++)
						for(int i = 0 ; i < 8 ; i++){
							if(k == 0 || k == 4 || i == 0 || i == 7)
								addImage(new GuiImage(x+decx+i*8,y+k*8+decy,8,8,0.75F,0F,1F,1F,layout));
							else
								addImage(new GuiImage(x+decx+i*8,y+k*8+decy,8,8,0.25F,0F,0.5F,1F,layout));
						}
					decx += 8*9;
					for(int k = 0 ; k < 5 ; k++)
						for(int i = 0 ; i < 8 ; i++){
							if(k == 2 && i != 0 && i != 7){
								if(i == 4)
									addImage(new GuiImage(x+decx+i*8,y+k*8+decy,8,8,0.5F,0F,0.75F,1F,layout));
								else
									addImage(new GuiImage(x+decx+i*8,y+k*8+decy,8,8,0F,0F,0.25F,1F,layout));
							}
							else
								addImage(new GuiImage(x+decx+i*8,y+k*8+decy,8,8,0.75F,0F,1F,1F,layout));
						}
					decy += 8*5; 
					addTextAutoWitdh("Layer 1 ............ Layer 2 ............ Layer 3", 0,decy+2-10, NGuiManual.maxStringWidth);
					decy += 20;
					
					addImage(new GuiImage(x,y+decy,8,8,0.75F,0F,1F,1F,layout));
					addItemStack(new GuiItemStack(x+10,y+decy-4,new ItemStack(ModBlocks.blockCasing,1,0)));
					addItemStack(new GuiItemStack(x+10+16,y+decy-4,new ItemStack(ModBlocks.blockCasing,1,2)));
					for(int i = 0 ; i < InterfaceType.values().length ; i++){
			    		addItemStack(new GuiItemStack(x+10+16+i*16,y+decy-4,new ItemStack(ModBlocks.blockInterface,1,ModBlocks.blockInterface.getMetaFromState(ModBlocks.blockInterface.getDefaultState().withProperty(BlockInterface.INTERFACE_TYPE, InterfaceType.getType(i))))));
			    	}					
					decy += 16;
					
					addImage(new GuiImage(x,y+decy,8,8,0.25F,0F,0.5F,1F,layout));
					addTextAutoWitdh("Empty or ", 10,24, NGuiManual.maxStringWidth);
					addComponent(CAT_ELECTRODE,
						new GuiComponent(x+57, y+decy, 55, 10){{
							addText("Electrodes", 0, 0);
							isLink = true;
						}});
					addTextAutoWitdh("or", 116,-10, NGuiManual.maxStringWidth);				
					addComponent(CAT_ELECTROLYTE,
						new GuiComponent(x+130, y+decy, 60, 10){{
							addText("Electrolytes", 0, 0);
							isLink = true;
						}});
					decy += 16;
					
					addImage(new GuiImage(x,y+decy,8,8,0F,0F,0.25F,1F,layout));
					for(int i = 0 ; i < ConductiveType.values().length ; i++){
						ItemStack it = new ItemStack(ModBlocks.blockConductive,1,ModBlocks.blockConductive.getMetaFromState(ModBlocks.blockConductive.getDefaultState().withProperty(BlockConductive.CONDUCTIVE_TYPE, ConductiveType.getType(i))));
			    		addItemStack(new GuiItemStack(x+10+i*16,y+decy-4,it));
			    	}
					decy += 16;
					
					addImage(new GuiImage(x,y+decy,8,8,0.5F,0F,0.75F,1F,layout));
					addItemStack(new GuiItemStack(x+10,y+decy-4,new ItemStack(ModBlocks.blockPlug,1,0)));
										
				}});
			addComponent("te",
				new GuiComponent(x+10, y+162, 100, 10){{
					addTextAutoWitdh("Battery dont have a said direction you can build this layout in any direction.", 0,0, NGuiManual.maxStringWidth);
				}});
			}}
		);
		
		this.componentList.put(CAT_ELECTRODE, new GuiComponent(this.x+10, this.y+60, 100, 10){{
			addText("Electrodes", 0, 0);
			isLink = true;
		}});
		listCategory.put(CAT_ELECTRODE, new DummyCategory(CAT_ELECTRODE,this.x, this.y){{
			addComponent("in",
				new GuiComponent(x+32, y+30, 100, 10){{
					yButtonRecipes = 71;
					xButtonRecipes = 95;
					nbGuiRecipeVertical = 1;
					nbGuiRecipeHorizontal = 3;
					handleRecipesGroup = true;
					int i = 0;
					for(Map.Entry<Integer,Electrode> entry : MaterialsHandler.electrodeListByPotential.entrySet()){
						addRecipe(new GuiRecipe("E:"+entry.getValue().potential+" O :"+entry.getValue().oxydationNumber.size()+"  "+entry.getValue().oxydationNumber.toString(),x,y,GuiRecipe.getGuiItemStack(new ArrayList<ItemStack>(){{add(entry.getValue().stackReference);}}),GuiRecipe.TYPE.VANILLA,GuiRecipe.getGuiItemStack(entry.getValue().listStack),0,5));
						i++;
					}
				}});
			addComponent("te",
				new GuiComponent(x+10, y+125, 100, 10){{
					globalScale = 0.7F;
					addTextAutoWitdh("Electrodes have different potential (E). The bigger the potential difference between your choosen electrodes is, the more RF you will "
							+ "generate. The bigger the amount of oxydation number (O) is, the longer the electrode will last. Anode = least potential from the two choosen "
							+ "electrodes, cathode will be the other one. A best anode to generate more rf will be one with minimal (O) value close to 1 and best cathode "
							+ "to generate more rf will be one with maximal (O) value close to 1.", 0, 0, NGuiManual.maxStringWidth + (int) (NGuiManual.maxStringWidth*(1-globalScale)));
				}});
			}}
		);
		this.componentList.put(CAT_ELECTROLYTE, new GuiComponent(this.x+10, this.y+70, 100, 10){{
			addText("Electrolytes", 0, 0);
			isLink = true;
		}});
		listCategory.put(CAT_ELECTROLYTE, new DummyCategory(CAT_ELECTROLYTE,this.x, this.y){{
			addComponent("in",
				new GuiComponent(x+32, y+30, 100, 10){{
					yButtonRecipes = 71;
					xButtonRecipes = 95;
					nbGuiRecipeVertical = 1;
					nbGuiRecipeHorizontal = 3;
					handleRecipesGroup = true;
					
					int i = 0;
					for(Map.Entry<Integer,Electrolyte> entry : MaterialsHandler.electrolyteListByPotential.entrySet()){
						addRecipe(new GuiRecipe("E:"+entry.getValue().potential+" O :"+entry.getValue().oxydationNumber.size()+"  "+entry.getValue().oxydationNumber.toString(),x,y,GuiRecipe.getGuiItemStack(new ArrayList<ItemStack>(){{add(entry.getValue().stackReference);}}),GuiRecipe.TYPE.VANILLA,GuiRecipe.getGuiItemStack(entry.getValue().listStack),0,5));
						i++;
					}
				}});
			addComponent("te",
				new GuiComponent(x+10, y+125, 100, 10){{
					globalScale = 0.7F;
					addTextAutoWitdh("Electrolyte have different potential (E). The bigger the potential is, the more RF you will generate. The bigger the "
							+ "amount of oxydation number (O) is, the longer the electrolyte will last. A best electrolyte to generate more rf will be one with (O) maximal "
							+ "value close to 1. There is differents type of electrolytes each one have there own ratio for voltage and lasting time : ", 0, 0, NGuiManual.maxStringWidth + (int) (NGuiManual.maxStringWidth*(1-globalScale)));
					String te = "";
					for(int i = 0 ; i < MaterialsHandler.Electrolyte.Type.values().length ; i++){
						te += " "+MaterialsHandler.Electrolyte.Type.getTypeFromIndex(i).name()+" ( V : "+MaterialsHandler.Electrolyte.Type.getTypeFromIndex(i).ratioVoltage+" ,LT: "+MaterialsHandler.Electrolyte.Type.getTypeFromIndex(i).ratioDecay+")";
					}
					addTextAutoWitdh(te, 0,0, NGuiManual.maxStringWidth);
				}});
			}}
		);
		
		this.componentList.put(CAT_CONDUCTIVE, new GuiComponent(this.x+10, this.y+80, 100, 10){{
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

		this.componentList.put(CAT_INTERFACE, new GuiComponent(this.x+10, this.y+90, 100, 10){{
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

		this.componentList.put(CAT_OTHER_MATERIALS, new GuiComponent(this.x+10, this.y+100, 100, 10){{
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
		
		this.componentList.put(CAT_ITEMS_AND_BLOCKS, new GuiComponent(this.x+10, this.y+110, 100, 10){{
			addText("Items and Blocks", 0, 0);
			isLink = true;
		}});
		listCategory.put(CAT_ITEMS_AND_BLOCKS, IAB_MANUAL);
	}
}
