package ae_fluids;

import ae_fluids.blocks.METank;
import ae_fluids.config.ConfigHandler;
import ae_fluids.gui.GuiHandler;
import ae_fluids.items.MEFluidCell;
import ae_fluids.proxy.CommonProxy;
import ae_fluids.tiles.METankTileEntity;
import ae_fluids.tiles.TickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = AEFluidsUtil.MOD_ID, name = "Applied Energistics - Fluid Expansion", version = "1.0", dependencies = "after:AppliedEnergistics")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class AEFluids {

	public TickHandler tickHandler;

	private static METank meTank;
	
	private static MEFluidCell me1kFluidCell;

	@Instance(AEFluidsUtil.MOD_ID)
	public static AEFluids instance;

	@SidedProxy(clientSide = "ae_fluids.proxy.ClientProxy", serverSide = "ae_fluids.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.loadConfiguration(event.getSuggestedConfigurationFile());
		
		GameRegistry.registerTileEntity(METankTileEntity.class, "METank");
		tickHandler = new TickHandler();

		meTank = new METank();
		GameRegistry.registerBlock(meTank, "meTank");
		LanguageRegistry.addName(meTank, "ME Tank");
		
		me1kFluidCell = new MEFluidCell(1022*1000); 
		GameRegistry.registerItem(me1kFluidCell, "me1kFluidStorage");
		LanguageRegistry.addName(me1kFluidCell, "ME 1k Fluid Storage");
		
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler(instance,
				new GuiHandler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}
