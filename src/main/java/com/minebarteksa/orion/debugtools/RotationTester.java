package com.minebarteksa.orion.debugtools;

import com.minebarteksa.orion.items.ItemBase;
import com.minebarteksa.orion.Orion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class RotationTester extends ItemBase
{
    public RotationTester() { super("rotation_tester", Orion.ModID); }

    @Override
    @SideOnly(Side.CLIENT)
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(!stack.isItemEnchanted())
            stack.addEnchantment(Enchantment.getEnchantmentByID(19), 255);
        if(worldIn.isRemote)
            if(isSelected)
                Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Rotation Yaw: " + entityIn.getRotationYawHead() + " Math: " + (entityIn.getRotationYawHead() * 100) / 360, false);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if(!stack.isItemEnchanted())
            stack.addEnchantment(Enchantment.getEnchantmentByID(19), 255);
        tooltip.add("\u00A7eLibOrion Debug Tool!");
        tooltip.add("\u00A7fRotation Yaw shows your rotationYawHead parameter");
        tooltip.add("\u00A7fMath shows (rotationYawHead * 100) / 360 equation");
        tooltip.add("\u00A7fBy right clicking on any entity it will show it's rotation values");
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
    {
        if(!playerIn.world.isRemote) {
            if (target != null) {
                playerIn.sendMessage(new TextComponentString(TextFormatting.AQUA + "==============================================="));
                playerIn.sendMessage(new TextComponentString("Entity: " + target.getName()));
                playerIn.sendMessage(new TextComponentString("Rotation Yaw        : " + target.rotationYaw));
                playerIn.sendMessage(new TextComponentString("Math Yaw             : " + (target.rotationYaw * 100) / 360));
                playerIn.sendMessage(new TextComponentString("Rotation Yaw Head : " + target.rotationYawHead));
                playerIn.sendMessage(new TextComponentString("Math Yaw Head     : " + (target.rotationYawHead * 100) / 360));
                playerIn.sendMessage(new TextComponentString("Rotation Pitch       : " + target.rotationPitch));
                playerIn.sendMessage(new TextComponentString(TextFormatting.AQUA + "==============================================="));
            } else
                return false;
        }
        return true;
    }
}
