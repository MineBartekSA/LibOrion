package com.minebarteksa.orion;

import com.minebarteksa.orion.blocks.BlockRegister;
import com.minebarteksa.orion.blocks.TERegister;
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
    private static List<BlockRegister> blockToRegister = new ArrayList<>();
    private static List<ItemBase> itemsToRegister = new ArrayList<>();
    private static List<TERegister> teBlocksToRegister = new ArrayList<>();
    private static List<SoundEvent> soundsToRegister = new ArrayList<>();

    public static void register(BlockRegister... blocks) { blockToRegister.addAll(Arrays.asList(blocks)); }

    public static void register(ItemBase... items) { itemsToRegister.addAll(Arrays.asList(items)); }

    public static void register(TERegister... tileEntityBlocks) { teBlocksToRegister.addAll(Arrays.asList(tileEntityBlocks)); }

    public static void register(SoundEvent... sounds) { soundsToRegister.addAll(Arrays.asList(sounds)); }

    public void registerItems(IForgeRegistry<Item> registry)
    {
        for(ItemBase i : itemsToRegister)
            registry.register(i);
        for(BlockRegister b : blockToRegister)
            registry.register(b.getItemBlock());
        for(TERegister te : teBlocksToRegister)
            registry.register(te.getItemBlock());
    }

    public void registerBlocks(IForgeRegistry<Block> registry)
    {
        for(BlockRegister b : blockToRegister)
            registry.register(b.getBlock());
        for(TERegister te : teBlocksToRegister)
        {
            registry.register(te.getBlock());
            GameRegistry.registerTileEntity(te.getTileEntityClass(), te.getRegistryName());
        }
    }

    public void registerItemModels()
    {
        for(ItemBase i : itemsToRegister)
            i.registerItemModel();
        for(BlockRegister b : blockToRegister)
            b.registerItemModel();
        for(TERegister te : teBlocksToRegister)
            te.registerItemModel();
    }

    public void registerSounds(IForgeRegistry<SoundEvent> registry) { for(SoundEvent s : soundsToRegister) registry.register(s); }
}
