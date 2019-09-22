package com.minebarteksa.orion.debugtools;

import com.minebarteksa.orion.Orion;
import com.minebarteksa.orion.items.ItemBase;
import com.minebarteksa.orion.particle.OrionParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ParticleTester extends ItemBase
{
    private int partId = 0;
    private int[] crashId = { 36, 37, 38, 40, 46 };

    public ParticleTester() { super("particle_tester", Orion.ModID); }

    @Override
    public ParticleTester setCreativeTab(CreativeTabs tab)
    {
        super.setCreativeTab(tab);
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if(worldIn.isRemote)
            onRightClick(playerIn);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote)
            onRightClick(player);
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    private void onRightClick(EntityPlayer playerIn)
    {
        if (playerIn.isSneaking()) {
            partId++;
            if (partId > OrionParticles.getMaxIDValue())
                partId = 0;
            Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Current particle: " + OrionParticles.getParticleNameByID(partId), false);
            Orion.log.info("Changed the particle to: " + OrionParticles.getParticleNameByID(partId) + ". ID: " + partId);
        } else {
            if(isIdInCrashList(partId) || OrionParticles.getParticleNameByID(partId).toString() == "null:null")
                Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Nothing! Sorry, but this particle crashes the game!", false);
            else {
                OrionParticles.spawnParticle(OrionParticles.getParticleNameByID(partId), playerIn.posX, playerIn.posY, playerIn.posZ, 1, 0, 0);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if(!stack.isItemEnchanted())
            stack.addEnchantment(Enchantment.getEnchantmentByLocation("liborion:debug_tool"), 1);
        tooltip.add(TextFormatting.YELLOW + "LibOrion Debug Tool!");
        tooltip.add(TextFormatting.WHITE + "Use Shift+RightClick to change particle type!");
        tooltip.add(TextFormatting.WHITE + "Not every particle will work, some like minecraft:iconcrack crash the game!");
        tooltip.add(TextFormatting.DARK_GREEN + "Current Particle type: " + OrionParticles.getParticleNameByID(partId));
    }

    private boolean isIdInCrashList(int id)
    {
        for(int crash : crashId)
            if(crash == id)
                return true;
        return false;
    }
}
