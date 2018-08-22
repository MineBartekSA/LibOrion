package com.minebarteksa.orion.items;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.annotation.Inherited;

public class ItemBase extends Item
{
	protected String name;
	protected String modelDomain;

	public ItemBase(String name, String modelDomain)
	{
		this.name = name;
		this.modelDomain = modelDomain;
		setUnlocalizedName(name);
		setRegistryName(name);
	}

	@SideOnly(Side.CLIENT)
	public void registerItemModel() { ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(modelDomain + ":" + name, "inventory")); }

	@Override
	public ItemBase setCreativeTab( CreativeTabs tab)
	{
		super.setCreativeTab(tab);
		return this;
	}
}
