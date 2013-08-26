package ae_fluids.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import ae_fluids.AEFluids;
import ae_fluids.AEFluidsUtil;
import ae_fluids.config.ConfigHandler;
import ae_fluids.tiles.AEFluidTile;
import ae_fluids.tiles.METankTileEntity;
import appeng.api.WorldCoord;
import appeng.api.events.GridTileUnloadEvent;
import appeng.api.me.tiles.IGridTileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class METank extends BlockContainer {
	
	public METank() {
		super(ConfigHandler.meTankBlockId, Material.iron);
		setCreativeTab(CreativeTabs.tabBlock);
		setUnlocalizedName("meTank");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegistry) {
		blockIcon = iconRegistry.registerIcon("aefluids:HelloWorld"); 
	}
	
	@Override
	@Deprecated
	public boolean hasTileEntity(int metadata) {
		return true;
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
	
	@Override
	public boolean onBlockActivated(World world, int x, int y,
			int z, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
		
		if (player.isSneaking()) return false;
		if (!world.isRemote) {
			player.openGui(AEFluids.instance, AEFluidsUtil.GUI_IDs.ME_TANK_ID, world, x, y, z);
			return true;
		}
		
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		AEFluidTile newTile = new METankTileEntity(); 
		return newTile;
	}
}
