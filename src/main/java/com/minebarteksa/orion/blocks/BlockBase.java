package com.minebarteksa.orion.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements BlockRegister
{
	protected String name;
	protected String modelDomain;

	public BlockBase(Material mat, String name, String modelDomain)
	{
		super(mat);
		this.name = name;
		this.modelDomain = modelDomain;
		setUnlocalizedName(name);
		setRegistryName(name);
	}

	@SideOnly(Side.CLIENT)
	public void registerItemModel() { ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(modelDomain + ":" + name, "inventory")); }

	public Item getItemBlock()
	{
		return new ItemBlock(this).setRegistryName(getRegistryName());
	}

	@Override
	public BlockBase setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}

	@Override
	public Block getBlock() { return this; }
}
