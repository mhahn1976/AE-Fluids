package ae_fluids.blocks;

import ae_fluids.config.ConfigHandler;
import ae_fluids.tiles.MEFluidImportBusTileEntity;
import appeng.api.WorldCoord;
import appeng.api.events.GridTileUnloadEvent;
import appeng.api.me.tiles.IGridTileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class MEFluidImportBus extends BlockContainer {
	
	public MEFluidImportBus () {
		super(ConfigHandler.meFluidImportBusBlockId, Material.iron);
		setCreativeTab(CreativeTabs.tabBlock);
		setUnlocalizedName("meFluidImport");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new MEFluidImportBusTileEntity();
	}
	
	@Override
	public void breakBlock(World par1World, int x, int y, int z,
			int par5, int par6) {
		TileEntity tile = par1World.getBlockTileEntity(x, y, z);
		if (tile != null && tile instanceof IGridTileEntity) {
			MinecraftForge.EVENT_BUS.post(new GridTileUnloadEvent((IGridTileEntity)tile, par1World, new WorldCoord(x, y, z)));
		}
		super.breakBlock(par1World, x, y, z, par5, par6);
	}

}
