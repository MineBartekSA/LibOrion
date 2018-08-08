package com.minebarteksa.orion.particle;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import com.minebarteksa.orion.Orion;
import net.minecraft.client.util.ITooltipFlag;
import java.util.List;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import com.minebarteksa.orion.items.ItemBase;

public class ParticleTester extends ItemBase
{
  public ParticleTester() { super("particle_tester", Orion.ModID); }

  @Override
  public ParticleTester setCreativeTab(CreativeTabs tab)
  {
    super.setCreativeTab(tab);
    return this;
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
  {
    worldIn.spawnParticle(EnumParticleTypes.NOTE, playerIn.posX, playerIn.posY, playerIn.posZ, 10000, 10000, 10000, null);
    return super.onItemRightClick(worldIn, playerIn, handIn);
  }

  @Override
  public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
  {
    tooltip.add("Debug Tool!");
    tooltip.add("Use Shift+RightClick to change particle type!");
    tooltip.add("Paricle type: ???");
  }
}
