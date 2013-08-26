package ae_fluids.tiles;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements ITickHandler {

	private static Multimap<World, AEFluidTile> tilesForInit = LinkedHashMultimap.create();
	private static EnumSet<TickType> tickTypes = EnumSet.noneOf(TickType.class);
	
	static {
		tickTypes.add(TickType.WORLD);
		tickTypes.add(TickType.CLIENT);
	}

	@ForgeSubscribe
	public synchronized void worldUnload(WorldEvent.Unload w) {
		if (w.isCanceled())
			return;
		tilesForInit.removeAll(w.world);
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		World world = null;
		if (type.contains(TickType.WORLD)) {
			world = (World)tickData[0];
		} else {
			world = (World)Minecraft.getMinecraft().theWorld;
		}
		List<AEFluidTile> toInit = new LinkedList<AEFluidTile>(tilesForInit.get(world));
		tilesForInit.removeAll(world);
		for (AEFluidTile te : toInit) te.init();
	}
	
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public EnumSet<TickType> ticks() {

		return tickTypes;
	}

	@Override
	public String getLabel() {
		
		return "MENetworkHandler";
	}

	public void unregisterTileToInit(World world, AEFluidTile tile) {
		tilesForInit.remove(world, tile);		
	}
	
	public void registerTileToInit(World world, AEFluidTile tile) {
		tilesForInit.put(world, tile);
	}

}
