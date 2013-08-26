package ae_fluids.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import ae_fluids.items.MEFluidCell;
import ae_fluids.tiles.METankTileEntity;

public class METankContainer extends Container {
	
	private METankTileEntity meTankEntity;
	
	private class MEFluidCellSlot extends Slot {

		public MEFluidCellSlot(IInventory inventory, int slot, int xOff, int yOff) {
			super(inventory, slot, xOff, yOff);
		}
		
		@Override
		public boolean isItemValid(ItemStack itemStack) {
			
			return itemStack.getItem() instanceof MEFluidCell;
		}
		
		
	}
	
	public METankContainer(InventoryPlayer playerInventory, METankTileEntity meTankEntity) {
		this.meTankEntity = meTankEntity;
		
		addSlotToContainer(new MEFluidCellSlot(meTankEntity, 0, 8, 18));
		
		bindPlayerInventory(playerInventory);
		
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		Slot slot = (Slot)this.inventorySlots.get(i);
		
		ItemStack stackInSlot = slot.getStack();
		if (stackInSlot == null) return null;
		if (i == 0) {	
			if (!this.mergeItemStack(stackInSlot, 1, 37, true)) return null;
		} else {
			
			if (!(stackInSlot.getItem() instanceof MEFluidCell) || !this.mergeItemStack(stackInSlot, 0, 1, false)) return null;
		}
		
		if (stackInSlot.stackSize == 0) {
            slot.putStack(null);
		} else {
            slot.onSlotChanged();
		}
		return stackInSlot;
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                        addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                        8 + j * 18, 44 + i * 18));
                }
        }

        for (int i = 0; i < 9; i++) {
                addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 102));
        }
	}
	
	public FluidTankInfo[] getFluidTankInfo() {
		return meTankEntity.getTankInfo(null);
	}

	public FluidStack getFluid() {
		return meTankEntity.getFluid();
	}
	
}
