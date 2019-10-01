package com.minebarteksa.orion.debugtools;

import com.minebarteksa.orion.Orion;
import com.minebarteksa.orion.items.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class DebugBase extends ItemBase
{
    public DebugBase() { super("debug_base", Orion.ModID); }

    @Override
    public DebugBase setCreativeTab(CreativeTabs tab)
    {
        super.setCreativeTab(tab);
        return this;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if(!stack.isItemEnchanted())
            stack.addEnchantment(Enchantment.getEnchantmentByLocation("liborion:debug_tool"), 1);
        tooltip.add(TextFormatting.YELLOW + "LibOrion Debug Tool!");
        tooltip.add(TextFormatting.WHITE + "This is a crafting base for all the other debug sticks!");
    }
}
