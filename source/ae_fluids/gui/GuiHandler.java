package ae_fluids.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ae_fluids.AEFluidsUtil;
import ae_fluids.tiles.METankTileEntity;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te == null) return null;

		switch(ID) {
			case AEFluidsUtil.GUI_IDs.ME_TANK_ID:
				if (!(te instanceof METankTileEntity)) break;
				return new METankContainer(player.inventory, (METankTileEntity)te);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te == null) return null;

		switch(ID) {
			case AEFluidsUtil.GUI_IDs.ME_TANK_ID:
				if (!(te instanceof METankTileEntity)) break;
				return new METankGUI(new METankContainer(player.inventory, (METankTileEntity)te));
		}
		return null;
	}

}
