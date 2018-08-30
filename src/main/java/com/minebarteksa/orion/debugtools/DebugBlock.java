package com.minebarteksa.orion.debugtools;

import com.minebarteksa.orion.Orion;
import com.minebarteksa.orion.blocks.TileEntityBlockBase;
import com.minebarteksa.orion.multiblock.MultiBlock;
import com.minebarteksa.orion.multiblock.MultiBlockInfo;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import javax.annotation.Nullable;
import java.util.List;

public class DebugBlock extends TileEntityBlockBase<DebugBlock.DebugBlockEntity>
{
    public DebugBlock() { super(Material.ROCK, "debug_block", Orion.ModID); }

    @Override
    public Class<DebugBlockEntity> getTileEntityClass() { return DebugBlockEntity.class; }

    @Nullable
    @Override
    public DebugBlockEntity createTileEntity(World world, IBlockState state) { return new DebugBlockEntity(); }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        if(!stack.isItemEnchanted())
            stack.addEnchantment(Enchantment.getEnchantmentByID(19), 255);
        tooltip.add("\u00A7eLibOrion Debug Tool!");
        tooltip.add("\u00A7fThis tool is an example of how to create a MultiBlock structure with OrionMultiBlocks system");
    }

    public static class DebugBlockEntity extends MultiBlock
    {
        public DebugBlockEntity() { super(new ResourceLocation(Orion.ModID, "debug_block")); }

        @Override
        public void onCompleted()
        {
            Orion.log.info("Created the '" + getCurrentMultiBlockName() + "' MultiBlock!");
            if(!getWorld().isRemote)
            {
                if(mbInfo.type == MultiBlockInfo.MultiBlockType.Single)
                    getWorld().getMinecraftServer().getPlayerList().sendMessage(new TextComponentString("MultiBlock Successfully created! Created " + getCurrentMultiBlockName()));
                else
                    getWorld().getMinecraftServer().getPlayerList().sendMessage(new TextComponentString("MultiMultiBlock Successfully created! Created " + getCurrentMultiBlockName()));
            }
        }

        @Override
        public void onDestroyedMultiBlock()
        {
            if(!getWorld().isRemote)
                getWorld().getMinecraftServer().getPlayerList().sendMessage(new TextComponentString("MultiBlock Destroyed!"));
        }
    }
}
