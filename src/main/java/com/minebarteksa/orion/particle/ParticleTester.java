package com.minebarteksa.orion.particle;

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
  public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
  {
    tooltip.add("Debug Tool!");
    tooltip.add("Use /setparticle command to set particle type");
    tooltip.add("Paricle type: ???");
  }
}
