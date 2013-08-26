package ae_fluids.tiles;

import ae_fluids.AEFluids;
import appeng.api.WorldCoord;
import appeng.api.events.GridTileLoadEvent;
import appeng.api.me.tiles.IGridTileEntity;
import appeng.api.me.util.IGridInterface;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public abstract class AEFluidTile extends TileEntity implements IGridTileEntity {
	
	protected IGridInterface attachedGrid;
	protected boolean isLoaded;
	protected boolean powered;

	
	public AEFluidTile () {
	    this.isLoaded = false;

	}

	public void init() {
		if (this.isLoaded) return;
		if (getWorldObj() == null) return;
		if (this instanceof IGridTileEntity)
			MinecraftForge.EVENT_BUS.post(new GridTileLoadEvent((IGridTileEntity)this, getWorldObj(), new WorldCoord(xCoord, yCoord, zCoord)));
		this.isLoaded = true;
	}
	
	protected void terminate() {
		this.isLoaded = false;
		AEFluids.instance.tickHandler.unregisterTileToInit(getWorldObj(), this);
	}
	
	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		if (this.isLoaded) {
			terminate();
		} else {
			AEFluids.instance.tickHandler.unregisterTileToInit(getWorldObj(), this);
		}
		
	}
	
	@Override
	public WorldCoord getLocation() {
		return new WorldCoord(xCoord, yCoord, zCoord);
	}

	
	@Override
	public void setPowerStatus(boolean hasPower) {
		powered = hasPower;
	}

	@Override
	public boolean isPowered() {
		return powered;
	}
	
	@Override
	public IGridInterface getGrid() {
		return attachedGrid;
	}

	@Override
	public void setGrid(IGridInterface gi) {
		attachedGrid = gi;
	}

	@Override
	public void writeToNBT(NBTTagCompound seralizedGameState) {
		super.writeToNBT(seralizedGameState);
		seralizedGameState.setBoolean("isPowered", powered);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound seralizedGameState) {
		super.readFromNBT(seralizedGameState);
		powered = seralizedGameState.getBoolean("isPowered");
	}
	
	@Override
	public World getWorld() {
		return this.getWorldObj();
	}

	@Override
	public boolean isValid() {
		return true;
	}



}
