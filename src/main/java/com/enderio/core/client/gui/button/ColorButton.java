package com.enderio.core.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemDye;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.enderio.core.api.client.gui.IGuiScreen;
import com.enderio.core.common.util.DyeColor;

public class ColorButton extends IconButton {

    private int colorIndex = 0;

    private String tooltipPrefix = "";

    public ColorButton(IGuiScreen gui, int id, int x, int y) {
        super(gui, id, x, y, null);
    }

    @Override
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
        boolean result = super.mousePressed(par1Minecraft, par2, par3);
        if (result) {
            nextColor();
        }
        return result;
    }

    @Override
    public boolean mousePressedButton(Minecraft mc, int x, int y, int button) {
        boolean result = button == 1 && super.checkMousePress(mc, x, y);
        if (result) {
            prevColor();
        }
        return result;
    }

    @Override
    public boolean mouseScrolled(Minecraft mc, int x, int y, int scrollDelta) {
        if (super.checkMousePress(mc, x, y)) {
            if (scrollDelta > 0) {
                nextColor();
            } else if (scrollDelta < 0) {
                prevColor();
            } else {
                return false;
            }
            return true;
        }
        return false;
    }

    public String getTooltipPrefix() {
        return tooltipPrefix;
    }

    public void setToolTipHeading(String tooltipPrefix) {
        if (tooltipPrefix == null) {
            this.tooltipPrefix = "";
        } else {
            this.tooltipPrefix = tooltipPrefix;
        }
    }

    private void nextColor() {
        colorIndex++;
        if (colorIndex >= ItemDye.field_150923_a.length) {
            colorIndex = 0;
        }
        setColorIndex(colorIndex);
    }

    private void prevColor() {
        colorIndex--;
        if (colorIndex < 0) {
            colorIndex = ItemDye.field_150923_a.length - 1;
        }
        setColorIndex(colorIndex);
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = MathHelper.clamp_int(colorIndex, 0, ItemDye.field_150923_a.length - 1);
        String colStr = DyeColor.values()[colorIndex].getLocalisedName();
        if (tooltipPrefix != null && tooltipPrefix.length() > 0) {
            setToolTip(tooltipPrefix, colStr);
        } else {
            setToolTip(colStr);
        }
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        super.drawButton(mc, mouseX, mouseY);
        if (visible) {
            Tessellator tes = Tessellator.instance;
            tes.startDrawingQuads();

            int x = xPosition + 2;
            int y = yPosition + 2;

            GL11.glDisable(GL11.GL_TEXTURE_2D);

            int col = ItemDye.field_150922_c[colorIndex];
            tes.setColorOpaque_I(col);
            tes.addVertex(x, y + height - 4, zLevel);
            tes.addVertex(x + width - 4, y + height - 4, zLevel);
            tes.addVertex(x + width - 4, y + 0, zLevel);
            tes.addVertex(x, y + 0, zLevel);

            tes.draw();

            GL11.glEnable(GL11.GL_TEXTURE_2D);

        }
    }
}
