package ae_fluids.tiles;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import appeng.api.TileRef;
import appeng.api.exceptions.AppEngTileMissingException;
import appeng.api.me.tiles.IGridMachine;

public class MEFluidImportBusTileEntity extends AEFluidTile {
	
	ForgeDirection facing;
	
	@Override
	public void updateEntity() {
		TileEntity tileEntity = worldObj.getBlockTileEntity(xCoord + facing.offsetX, yCoord + facing.offsetY, zCoord + facing.offsetZ);
		if (!(tileEntity instanceof IFluidHandler)) return;
		IFluidHandler fluidContainer = (IFluidHandler)tileEntity;
		FluidStack possDrain = fluidContainer.drain(facing.getOpposite(), 1000, false);
		
		List<TileRef<IGridMachine>> machineRefs = this.attachedGrid.getMachines();
		for (TileRef<IGridMachine> machineRef : machineRefs) {
			try {
				IGridMachine machine = machineRef.getTile();
				if (machine instanceof METankTileEntity) {
					METankTileEntity meTank = (METankTileEntity)machine;
					FluidTankInfo[] meTankContent = meTank.getTankInfo(facing);
					if (meTankContent.length > 0) {
						if (meTankContent[0].fluid.amount > 0) {
							int fillAmt = meTank.fill(facing, possDrain, false);
							if (fillAmt > 0) {
								FluidStack drain = fluidContainer.drain(facing.getOpposite(), fillAmt, true);
								meTank.fill(facing, drain, true);
								possDrain.amount -= drain.amount;
								if (possDrain.amount <= 0) break;
							}
						}
					}
				}
			} catch (AppEngTileMissingException ex) {} //Do nothing, since the tile is not loaded.
		}
		if (possDrain.amount > 0) {
			for (TileRef<IGridMachine> machineRef : machineRefs) {
				try {
					IGridMachine machine = machineRef.getTile();
					if (machine instanceof METankTileEntity) {
						METankTileEntity meTank = (METankTileEntity)machine;
						FluidTankInfo[] meTankContent = meTank.getTankInfo(facing);
						if (meTankContent.length > 0) {
							int fillAmt = meTank.fill(facing, possDrain, false);
							if (fillAmt > 0) {
								FluidStack drain = fluidContainer.drain(facing.getOpposite(), fillAmt, true);
								meTank.fill(facing, drain, true);
								possDrain.amount -= drain.amount;
								if (possDrain.amount <= 0) break;
							}
						}
					}
				} catch (AppEngTileMissingException ex) {} //Do nothing, since the tile is not loaded.
			}
		}
	}
	
}
