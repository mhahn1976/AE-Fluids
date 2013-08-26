package ae_fluids.config;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class ConfigHandler {
	
	public static int meTankBlockId = -1;
	public static int meFluidImportBusBlockId = -1;
	public static int meFluidExportBusBlockId = -1;
	public static int fluidCellId = -1;
	
	public static void loadConfiguration (File suggestedConfigFile) {
		try {
			Configuration config = new Configuration(suggestedConfigFile);
			
			config.load();
			
			meTankBlockId = config.getBlock("ME Tank", 3821).getInt();
			meFluidImportBusBlockId = config.getBlock("ME Fluid Import Bus", 3822).getInt();
			meFluidExportBusBlockId = config.getBlock("ME Fluid Export Bus", 3823).getInt();
			
			fluidCellId = config.getItem("ME Fluid Cell ID", 8365).getInt();
			
			config.save();
						
		} catch (Exception e) {
			
		}
	}

}
