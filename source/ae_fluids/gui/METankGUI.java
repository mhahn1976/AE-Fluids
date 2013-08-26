package ae_fluids.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import ae_fluids.AEFluidsUtil;

public class METankGUI extends GuiContainer {
	static ResourceLocation guiBackground = new ResourceLocation(AEFluidsUtil.MOD_ID.toLowerCase(), "/gui/meTank.png");
	
	METankContainer container;
	
	private static final int tankWidth = 138;
	private static final int tankLeftOffset = 30;
	private static final int tankTopOffset = 20;
	private static final int tankHeight = 12;

	public METankGUI(METankContainer meTankContainer) {
		super(meTankContainer);
		this.container = meTankContainer;
		xSize = 175;
		ySize = 125;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        
        FluidStack fluidStack = container.getFluid();
        FluidTankInfo[] tankInfo = container.getFluidTankInfo();
        mc.renderEngine.func_110577_a(guiBackground);
        
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        if (fluidStack != null) {
        	Icon icon = fluidStack.getFluid().getStillIcon();
        	int capacity = tankInfo[0].capacity;
        	long amount = fluidStack.amount;
        	int fillWidth = (int)((amount * tankWidth) / capacity);
        	mc.renderEngine.func_110577_a(TextureMap.field_110575_b);
        	int start = guiLeft + tankLeftOffset;
        	while (fillWidth > 0) {
        		int width = 16;
        		if (fillWidth < 16) {
        			width = fillWidth;
        		}
        		drawTexturedModelRectFromIcon(start, guiTop + tankTopOffset, icon, width, tankHeight);
        		start += 16;
        		fillWidth -= 16;
        	}
            //this.drawString(fontRenderer, fluidStack.getFluid().getName() + amount + "/" + capacity, guiLeft + tankLeftOffset, guiTop + 5, 0xFFFFFF);		
        }
	}

}
