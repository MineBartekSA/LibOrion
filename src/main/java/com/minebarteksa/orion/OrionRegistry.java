package com.minebarteksa.orion;

import com.minebarteksa.orion.blocks.BlockBase;
import com.minebarteksa.orion.blocks.TileEntityBlockBase;
import com.minebarteksa.orion.items.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class OrionRegistry
{
    private static List<BlockBase> blockToRegister = new ArrayList<>();
    private static List<ItemBase> itemsToRegister = new ArrayList<>();
    private static List<TileEntityBlockBase> teBlocksToRegister = new ArrayList<>();

    public static void register(BlockBase... blocks) { for(BlockBase b : blocks) blockToRegister.add(b); }

    public static void register(ItemBase... items) { for(ItemBase i : items) itemsToRegister.add(i); }

    public static void register(TileEntityBlockBase... tileEntityBlocks) { for(TileEntityBlockBase te : tileEntityBlocks) teBlocksToRegister.add(te); }

    public void registerItems(IForgeRegistry<Item> registry)
    {
        for(ItemBase i : itemsToRegister)
            registry.register(i);
        for(BlockBase b : blockToRegister)
            registry.register(b.getItemBlock());
        for(TileEntityBlockBase te : teBlocksToRegister)
            registry.register(te.getItemBlock());
    }

    public void registerBlocks(IForgeRegistry<Block> registry)
    {
        for(BlockBase b : blockToRegister)
            registry.register(b);
        for(TileEntityBlockBase te : teBlocksToRegister)
        {
            registry.register(te);
            GameRegistry.registerTileEntity(te.getTileEntityClass(), te.getRegistryName());
        }
    }

    public void registerItemModels()
    {
        for(ItemBase i : itemsToRegister)
            i.registerItemModel();
        for(BlockBase b : blockToRegister)
            b.registerItemModel();
        for(TileEntityBlockBase te : teBlocksToRegister)
            te.registerItemModel();
    }
}
