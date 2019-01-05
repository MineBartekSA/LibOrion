package com.minebarteksa.orion.debugtools;

import com.minebarteksa.orion.Orion;
import com.minebarteksa.orion.events.OrionEvent;
import com.minebarteksa.orion.events.IOrionListener;
import com.minebarteksa.orion.events.OrionMouseEvents;
import com.minebarteksa.orion.items.ItemBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import javax.annotation.Nullable;
import java.util.List;

public class MouseDebug extends ItemBase implements IOrionListener
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
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(!stack.isItemEnchanted())
            stack.addEnchantment(Enchantment.getEnchantmentByID(19), 50);
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
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if(!stack.isItemEnchanted())
            stack.addEnchantment(Enchantment.getEnchantmentByID(19), 50);
        tooltip.add(TextFormatting.YELLOW + "LibOrion Debug Tool!");
        tooltip.add(TextFormatting.WHITE + "This tools tracks the count of clicks and scroll direction");
        tooltip.add(TextFormatting.WHITE + "It uses LibOrion's own easy Event system");
        tooltip.add(TextFormatting.WHITE + "It's not a useful tool but it's useful as a example of using LibOrion Event system");
        tooltip.add(TextFormatting.DARK_GREEN + "LeftClick count: " + leftCount);
        tooltip.add(TextFormatting.DARK_GREEN + "RightClick count: " + rightCount);
        tooltip.add(TextFormatting.DARK_GREEN + "MiddleClick count: " + middleCount);
        tooltip.add(TextFormatting.DARK_GREEN + "Last scroll direction: " + lastDirection);
    }

    @Override
    public void onOrionEvent(OrionEvent ev)
    {
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
                lastDirection = "Right";
            if(((OrionMouseEvents.ScrollEvent)ev).value == 120)
                lastDirection = "Left";
        }
    }
}
