package com.minebarteksa.orion.debugtools;

import com.minebarteksa.orion.Orion;
import com.minebarteksa.orion.integrations.IOrionInfoProvider;
import com.minebarteksa.orion.integrations.infoprovider.IPData;
import com.minebarteksa.orion.items.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import javax.annotation.Nullable;
import java.util.List;

public class InfoProviderTester extends ItemBase
{
    public InfoProviderTester() { super("ip_tester", Orion.ModID); }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if(!stack.isItemEnchanted())
            stack.addEnchantment(Enchantment.getEnchantmentByID(19), 50);
        tooltip.add(TextFormatting.YELLOW + "LibOrion Debug Tool!");
        tooltip.add(TextFormatting.WHITE + "This tool allows you to see what info Blocks provide");
        tooltip.add(TextFormatting.WHITE + "Block must use OrionInfoProvider system");
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(!stack.isItemEnchanted())
            stack.addEnchantment(Enchantment.getEnchantmentByID(19), 50);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if(!worldIn.isRemote)
        {
            RayTraceResult rtr = rayTrace(worldIn, playerIn, true);
            if(rtr.typeOfHit == RayTraceResult.Type.BLOCK)
            {
                Block b = worldIn.getBlockState(rtr.getBlockPos()).getBlock();
                if(b instanceof IOrionInfoProvider)
                {
                    playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Info provided by Block:"));
                    for(String s : ((IOrionInfoProvider)b).addInfo(new IPData(rtr.getBlockPos(), rtr.sideHit, playerIn, worldIn)))
                        playerIn.sendMessage(new TextComponentString(s));
                    return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
                }
                else if(b.hasTileEntity(worldIn.getBlockState(rtr.getBlockPos())))
                {
                    if(worldIn.getTileEntity(rtr.getBlockPos()) instanceof IOrionInfoProvider)
                    {
                        playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Info provided by Block's TileEntity:"));
                        for(String s : ((IOrionInfoProvider)worldIn.getTileEntity(rtr.getBlockPos())).addInfo(new IPData(rtr.getBlockPos(), rtr.sideHit, playerIn, worldIn)))
                            playerIn.sendMessage(new TextComponentString(s));
                        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
                    }
                    else
                    {
                        playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "This Block's TileEntity dose not provide any info!"));
                        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
                    }
                }
                else
                {
                    playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "This Block dose not provide any info!"));
                    return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
                }
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
