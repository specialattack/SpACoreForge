package net.specialattack.forge.core.client.gui.deprecated.style.background;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.specialattack.forge.core.client.gui.Color;
import net.specialattack.forge.core.client.gui.GuiHelper;
import net.specialattack.forge.core.client.gui.deprecated.SizeContext;
import net.specialattack.forge.core.client.gui.deprecated.element.SGComponent;

@SideOnly(Side.CLIENT)
public class SolidBackground implements IBackground {

    private Color color;

    public SolidBackground(Color color) {
        this.color = color;
    }

    @Override
    public void drawBackground(SGComponent component) {
        GlStateManager.disableTexture2D();
        int left = component.getLeft(SizeContext.INNER);
        int top = component.getTop(SizeContext.INNER);
        GuiHelper.drawColoredRect(left, top, left + component.getWidth(SizeContext.INNER), top + component.getHeight(SizeContext.INNER), this.color.colorHex, component.getZLevel());
    }
}
