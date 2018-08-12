package com.minebarteksa.orion.debugtools;

import com.minebarteksa.orion.Orion;
import com.minebarteksa.orion.events.OrionEvents;
import com.minebarteksa.orion.events.OrionListener;
import com.minebarteksa.orion.events.OrionMouseEvents;
import com.minebarteksa.orion.items.ItemBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import javax.annotation.Nullable;
import java.util.List;

public class MouseDebug extends ItemBase implements OrionListener
{
    private OrionMouseEvents.MouseButtons lastClick;
    private boolean isRegistered = false;
    private int leftCount = 0;
    private int rightCount = 0;
    private int middleCount = 0;
    private String lastDirection = "None";

    public MouseDebug() { super("mouse_debug", Orion.ModID); }

    @Override
    @SideOnly(Side.CLIENT)
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(!stack.isItemEnchanted())
            stack.addEnchantment(Enchantment.getEnchantmentByID(19), 255);
        if(!isRegistered)
        {
            OrionMouseEvents.LC.addListener(this);
            OrionMouseEvents.RC.addListener(this);
            OrionMouseEvents.MC.addListener(this);
            OrionMouseEvents.SE.addListener(this);
            isRegistered = true;
        }
        if(worldIn.isRemote)
            if(isSelected)
                Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Last Button: " + lastClick, false);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("\u00A7eLibOrion Debug Tool!");
        tooltip.add("\u00A7fThis tools tracks the count of clicks and scroll direction");
        tooltip.add("\u00A7fIt uses LibOrion's own easy Event system");
        tooltip.add("\u00A7fIt's not a useful tool but it's useful as a example of using LibOrion Event system");
        tooltip.add("\u00A72LeftClick count: " + leftCount);
        tooltip.add("\u00A72RightClick count: " + rightCount);
        tooltip.add("\u00A72MiddleClick count: " + middleCount);
        tooltip.add("\u00A72Last scroll direction: " + lastDirection);
    }

    @Override
    public void onOrionEvent(OrionEvents ev) {
        if(ev instanceof OrionMouseEvents.LeftClick && ((OrionMouseEvents.LeftClick)ev).start)
        {
            leftCount++;
            lastClick = OrionMouseEvents.MouseButtons.LeftClick;
        }
        if(ev instanceof OrionMouseEvents.RightClick && ((OrionMouseEvents.RightClick)ev).start)
        {
            rightCount++;
            lastClick = OrionMouseEvents.MouseButtons.RightClick;
        }
        if(ev instanceof OrionMouseEvents.MiddleClick && ((OrionMouseEvents.MiddleClick)ev).start)
        {
            middleCount++;
            lastClick = OrionMouseEvents.MouseButtons.MiddleClick;
        }
        if(ev instanceof  OrionMouseEvents.ScrollEvent)
        {
            if(((OrionMouseEvents.ScrollEvent)ev).value == -120)
                lastDirection = "Left";
            if(((OrionMouseEvents.ScrollEvent)ev).value == 120)
                lastDirection = "Right";
        }
    }
}
