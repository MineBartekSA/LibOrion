package com.minebarteksa.orion;

import com.minebarteksa.orion.blocks.BlockBase;
import com.minebarteksa.orion.blocks.TileEntityBlockBase;
import com.minebarteksa.orion.items.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public class OrionRegistry
{
    private static List<BlockBase> blockToRegister = new ArrayList<>();
    private static List<ItemBase> itemsToRegister = new ArrayList<>();
    private static List<TileEntityBlockBase> teBlocksToRegister = new ArrayList<>();
    private static List<SoundEvent> soundsToRegister = new ArrayList<>();

    public static void register(BlockBase... blocks) { blockToRegister.addAll(Arrays.asList(blocks)); }

    public static void register(ItemBase... items) { itemsToRegister.addAll(Arrays.asList(items)); }

    public static void register(TileEntityBlockBase... tileEntityBlocks) { teBlocksToRegister.addAll(Arrays.asList(tileEntityBlocks)); }

    public static void register(SoundEvent... sounds) { soundsToRegister.addAll(Arrays.asList(sounds)); }

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

    public void registerSounds(IForgeRegistry<SoundEvent> registry) { for(SoundEvent s : soundsToRegister) registry.register(s); }
}
