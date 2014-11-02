package net.specialattack.forge.core.client.gui.style;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.specialattack.forge.core.client.gui.Color;
import net.specialattack.forge.core.client.gui.GuiHelper;

@SideOnly(Side.CLIENT)
public class SolidFilledBorder implements IBorder {

    private Color color;
    private int width;

    public SolidFilledBorder(Color color, int width) {
        this.color = color;
        this.width = width;
    }

    @Override
    public void draw(int startX, int startY, int endX, int endY, float zLevel) {
        GuiHelper.drawGradientRect(startX - width, startY - width, endX + width, endY + width, this.color.colorHex, this.color.colorHex, zLevel);
    }

}
