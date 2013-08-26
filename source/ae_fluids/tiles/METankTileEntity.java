package ae_fluids.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import ae_fluids.config.ConfigHandler;
import ae_fluids.items.MEFluidCell;

public class METankTileEntity extends AEFluidTile implements IInventory, IFluidHandler {

	public static final int FLUID_INPUT_SLOT = 0;

	private ItemStack inventory = null;

	public METankTileEntity() {
		super();
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.inventory != null) {
			ItemStack itemstack;

			if (this.inventory.stackSize <= j) {
				itemstack = this.inventory;
				this.inventory = null;
				this.onInventoryChanged();
				return itemstack;
			} else {
				itemstack = this.inventory.splitStack(j);

				if (this.inventory.stackSize == 0) {
					this.inventory = null;
				}

				this.onInventoryChanged();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.inventory != null) {
			ItemStack itemstack = this.inventory;
			this.inventory = null;
			return itemstack;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.inventory = itemstack;

		if (itemstack != null
				&& itemstack.stackSize > this.getInventoryStackLimit()) {
			itemstack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "ME Tank";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		getWorldObj().notifyBlockChange(xCoord, yCoord, zCoord, ConfigHandler.meTankBlockId);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return isPowered();
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return itemstack.getItem() instanceof MEFluidCell;
	}


	@Override
	public void updateEntity() {
		if (!isLoaded) {
			init();
		}
		super.updateEntity();
	}

	@Override
	public void writeToNBT(NBTTagCompound seralizedGameState) {
		super.writeToNBT(seralizedGameState);
		
        NBTTagList seralizedItemSlots = new NBTTagList();

        if (this.inventory != null) {
           NBTTagCompound serializedItemSlot = new NBTTagCompound();
           serializedItemSlot.setByte("Slot", (byte) 0);
           this.inventory.writeToNBT(serializedItemSlot);
           seralizedItemSlots.appendTag(serializedItemSlot);
        }

        seralizedGameState.setTag("Items", seralizedItemSlots);
		
			
	}

	@Override
	public void readFromNBT(NBTTagCompound seralizedGameState) {
		super.readFromNBT(seralizedGameState);
        NBTTagList seralizedItemSlots = seralizedGameState.getTagList("Items");

        for (int i = 0; i < seralizedItemSlots.tagCount(); ++i)
        {
            NBTTagCompound seralizedItemSlot = (NBTTagCompound)seralizedItemSlots.tagAt(i);
            int j = seralizedItemSlot.getByte("Slot") & 255;

            if (j >= 0 && j < 1){
                this.inventory = ItemStack.loadItemStackFromNBT(seralizedItemSlot);
            }
        }
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (inventory == null || !(inventory.getItem() instanceof MEFluidCell))
			return 0;
		MEFluidCell fluidCell = (MEFluidCell) inventory.getItem();
		
		return fluidCell.fill(inventory, resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		if (inventory == null || !(inventory.getItem() instanceof MEFluidCell))
			return null;
		MEFluidCell fluidCell = (MEFluidCell) inventory.getItem();

		if (resource == null || !resource.isFluidEqual(fluidCell.getFluid(inventory))) {
			return null;
		}
		return fluidCell.drain(inventory, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (inventory == null || !(inventory.getItem() instanceof MEFluidCell))
			return null;
		MEFluidCell fluidCell = (MEFluidCell) inventory.getItem();
		return fluidCell.drain(inventory, maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return isPowered();
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return isPowered();
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if (inventory == null || !(inventory.getItem() instanceof MEFluidCell))
			return null;
		MEFluidCell fluidCell = (MEFluidCell) inventory.getItem();

		return new FluidTankInfo[] { new FluidTankInfo(
				fluidCell.getFluid(inventory),
				fluidCell.getCapacity(inventory)) };
	}

	public FluidStack getFluid() {
		if (inventory == null || !(inventory.getItem() instanceof MEFluidCell))
			return null;
		MEFluidCell fluidCell = (MEFluidCell) inventory.getItem();

		return fluidCell.getFluid(inventory);
	}

}
