package net.specialattack.forge.core.client.gui.elements;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IFocusableElement extends IGuiElement {

    boolean canGetFocus();

    void giveFocus();

}
