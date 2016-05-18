/*
 * LIMITED USE SOFTWARE LICENSE AGREEMENT
 * This Limited Use Software License Agreement (the "Agreement") is a legal agreement between you, the end-user, and the AlgorithmicsAnonymous Team ("AlgorithmicsAnonymous"). By downloading or purchasing the software materials, which includes source code (the "Source Code"), artwork data, music and software tools (collectively, the "Software"), you are agreeing to be bound by the terms of this Agreement. If you do not agree to the terms of this Agreement, promptly destroy the Software you may have downloaded or copied.
 * AlgorithmicsAnonymous SOFTWARE LICENSE
 * 1. Grant of License. AlgorithmicsAnonymous grants to you the right to use the Software. You have no ownership or proprietary rights in or to the Software, or the Trademark. For purposes of this section, "use" means loading the Software into RAM, as well as installation on a hard disk or other storage device. The Software, together with any archive copy thereof, shall be destroyed when no longer used in accordance with this Agreement, or when the right to use the Software is terminated. You agree that the Software will not be shipped, transferred or exported into any country in violation of the U.S. Export Administration Act (or any other law governing such matters) and that you will not utilize, in any other manner, the Software in violation of any applicable law.
 * 2. Permitted Uses. For educational purposes only, you, the end-user, may use portions of the Source Code, such as particular routines, to develop your own software, but may not duplicate the Source Code, except as noted in paragraph 4. The limited right referenced in the preceding sentence is hereinafter referred to as "Educational Use." By so exercising the Educational Use right you shall not obtain any ownership, copyright, proprietary or other interest in or to the Source Code, or any portion of the Source Code. You may dispose of your own software in your sole discretion. With the exception of the Educational Use right, you may not otherwise use the Software, or an portion of the Software, which includes the Source Code, for commercial gain.
 * 3. Prohibited Uses: Under no circumstances shall you, the end-user, be permitted, allowed or authorized to commercially exploit the Software. Neither you nor anyone at your direction shall do any of the following acts with regard to the Software, or any portion thereof:
 * Rent;
 * Sell;
 * Lease;
 * Offer on a pay-per-play basis;
 * Distribute for money or any other consideration; or
 * In any other manner and through any medium whatsoever commercially exploit or use for any commercial purpose.
 * Notwithstanding the foregoing prohibitions, you may commercially exploit the software you develop by exercising the Educational Use right, referenced in paragraph 2. hereinabove.
 * 4. Copyright. The Software and all copyrights related thereto (including all characters and other images generated by the Software or depicted in the Software) are owned by AlgorithmicsAnonymous and is protected by United States copyright laws and international treaty provisions. AlgorithmicsAnonymous shall retain exclusive ownership and copyright in and to the Software and all portions of the Software and you shall have no ownership or other proprietary interest in such materials. You must treat the Software like any other copyrighted materials. You may not otherwise reproduce, copy or disclose to others, in whole or in any part, the Software. You may not copy the written materials accompanying the Software. You agree to use your best efforts to see that any user of the Software licensed hereunder complies with this Agreement.
 * 5. NO WARRANTIES. AlgorithmicsAnonymous DISCLAIMS ALL WARRANTIES, BOTH EXPRESS IMPLIED, INCLUDING BUT NOT LIMITED TO, IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE WITH RESPECT TO THE SOFTWARE. THIS LIMITED WARRANTY GIVES YOU SPECIFIC LEGAL RIGHTS. YOU MAY HAVE OTHER RIGHTS WHICH VARY FROM JURISDICTION TO JURISDICTION. AlgorithmicsAnonymous DOES NOT WARRANT THAT THE OPERATION OF THE SOFTWARE WILL BE UNINTERRUPTED, ERROR FREE OR MEET YOUR SPECIFIC REQUIREMENTS. THE WARRANTY SET FORTH ABOVE IS IN LIEU OF ALL OTHER EXPRESS WARRANTIES WHETHER ORAL OR WRITTEN. THE AGENTS, EMPLOYEES, DISTRIBUTORS, AND DEALERS OF AlgorithmicsAnonymous ARE NOT AUTHORIZED TO MAKE MODIFICATIONS TO THIS WARRANTY, OR ADDITIONAL WARRANTIES ON BEHALF OF AlgorithmicsAnonymous.
 * Exclusive Remedies. The Software is being offered to you free of any charge. You agree that you have no remedy against AlgorithmicsAnonymous, its affiliates, contractors, suppliers, and agents for loss or damage caused by any defect or failure in the Software regardless of the form of action, whether in contract, tort, includinegligence, strict liability or otherwise, with regard to the Software. Copyright and other proprietary matters will be governed by United States laws and international treaties. IN ANY CASE, AlgorithmicsAnonymous SHALL NOT BE LIABLE FOR LOSS OF DATA, LOSS OF PROFITS, LOST SAVINGS, SPECIAL, INCIDENTAL, CONSEQUENTIAL, INDIRECT OR OTHER SIMILAR DAMAGES ARISING FROM BREACH OF WARRANTY, BREACH OF CONTRACT, NEGLIGENCE, OR OTHER LEGAL THEORY EVEN IF AlgorithmicsAnonymous OR ITS AGENT HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES, OR FOR ANY CLAIM BY ANY OTHER PARTY. Some jurisdictions do not allow the exclusion or limitation of incidental or consequential damages, so the above limitation or exclusion may not apply to you.
 */

package com.sandvoxel.generitech.client.gui.machines;

import com.sandvoxel.generitech.api.util.MachineTier;
import com.sandvoxel.generitech.client.gui.GuiBase;
import com.sandvoxel.generitech.common.container.machines.ContainerPulverizer;
import com.sandvoxel.generitech.common.tileentities.machines.TileEntityPulverizer;
import com.sandvoxel.generitech.common.util.GuiHelper;
import com.sandvoxel.generitech.common.util.LanguageHelper;
import com.sandvoxel.generitech.common.util.LogHelper;
import net.darkhax.tesla.api.BaseTeslaContainer;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.darkhax.tesla.lib.TeslaUtils;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GuiPulverizer extends GuiBase {
    private TileEntityPulverizer tileEntity;
    private GuiHelper guiHelper = new GuiHelper();
    private MachineTier machineTier;
    private HashMap<Rectangle, List<String>> tooltips = new HashMap<Rectangle, List<String>>();
    ITeslaHolder container;
    Rectangle powerBar;

    public GuiPulverizer(InventoryPlayer inventoryPlayer, TileEntityPulverizer tileEntity) {
        super(new ContainerPulverizer(inventoryPlayer, tileEntity));
        this.xSize = 176;
        this.ySize = 166;
        this.tileEntity = tileEntity;
        this.machineTier = MachineTier.byMeta(tileEntity.getBlockMetadata());

        if(machineTier == MachineTier.TIER_0)
        {
        }
        else
        {
            this.container = tileEntity.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, EnumFacing.DOWN);
            powerBar = new Rectangle(9, 25, 14, 55);
        }
    }

    @Override
    public void drawBG(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        bindTexture("gui/machines/pulverizer1.png");
        drawTexturedModalRect(paramInt1, paramInt2, 0, 0, this.xSize, this.ySize);

        drawTexturedModalRect(paramInt1 + 25, paramInt2 + 63, 47, 34, 18, 18);

        if(machineTier == MachineTier.TIER_0)
        {
            drawTexturedModalRect(paramInt1 + 28, paramInt2 + 38, 176, 31, 14, 14);
            int fireOffset = tileEntity.getFuelOffset() + 1; // (x + 1) 1 and 11
            drawTexturedModalRect(paramInt1 + 28, paramInt2 + 38 + fireOffset, 176, 18 + fireOffset, 14, 14 - fireOffset);
        }
        else if(machineTier == MachineTier.TIER_1)
        {
            drawTexturedModalRect(paramInt1 + 151, paramInt2 + 63, 47, 34, 18, 18);
        }
        else if(machineTier == MachineTier.TIER_2)
        {
            drawTexturedModalRect(paramInt1 + 151, paramInt2 + 63, 47, 34, 18, 18);
            drawTexturedModalRect(paramInt1 + 133, paramInt2 + 63, 47, 34, 18, 18);
        }
        else if(machineTier == MachineTier.TIER_3)
        {
            drawTexturedModalRect(paramInt1 + 151, paramInt2 + 63, 47, 34, 18, 18);
            drawTexturedModalRect(paramInt1 + 133, paramInt2 + 63, 47, 34, 18, 18);
            drawTexturedModalRect(paramInt1 + 115, paramInt2 + 63, 47, 34, 18, 18);
        }



    }

    @Override
    public void drawFG(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        this.fontRendererObj.drawString(tileEntity.hasCustomName() ? tileEntity.getCustomName() : LanguageHelper.NONE.translateMessage(tileEntity.getUnlocalizedName()), 16, 12, 0xFFFFFF);

        int timeTotal = tileEntity.getTotalProcessTime();
        int timeCurrent = tileEntity.getTicksRemaining();
        float timePercent = ((((float) timeTotal - (float) timeCurrent) / (float) timeTotal)) * 100;

        if(machineTier == MachineTier.TIER_0)
        {

        }
        else
        {
            long powerCapacity = container.getCapacity();
            long powerCurrent = container.getStoredPower();

            int powerPercent = powerCurrent > 0 ? (int)(powerCurrent * 100d / powerCapacity) : 0;

            guiHelper.drawVerticalProgressBar(12, 28, 8, 50, powerPercent, colorBackground, colorBorder, colorProgressBackground);
        }


        guiHelper.drawHorizontalProgressBar(72, 39, 40, 8, Math.round(timePercent), colorBackground, colorBorder, colorProgressBackground);
        String progressLabel = String.format("%d%%", Math.round(timePercent));
        guiHelper.drawCenteredStringWithShadow(30, 39, 126, progressLabel, colorFont);

    }

    @Override
    public void drawScreen(int mouse_x, int mouse_y, float btn) {
        super.drawScreen(mouse_x, mouse_y, btn);

        Point currentMouse = new Point(mouse_x - guiLeft, mouse_y - guiTop);
        if(tooltips != null) {
            for (Rectangle rectangle : tooltips.keySet()) {
                if (rectangle.contains(currentMouse)) {
                    ArrayList<String> messages = new ArrayList<String>(tooltips.get(rectangle));
                    renderToolTip(messages, mouse_x, mouse_y);
                }
            }
        }

        if(machineTier == MachineTier.TIER_0)
        {

        }
        else
        {
            if(powerBar.contains(currentMouse))
            {
                ArrayList<String> powerMessage = new ArrayList<String>();
                powerMessage.add(TeslaUtils.getDisplayableTeslaCount(container.getStoredPower()));
                renderToolTip(powerMessage, mouse_x, mouse_y);
            }
        }
    }
}