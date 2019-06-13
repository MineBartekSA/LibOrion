package com.minebarteksa.orion.debugtools;

import com.minebarteksa.orion.Orion;
import com.minebarteksa.orion.TESRRegister;
import com.minebarteksa.orion.blocks.TileEntityBlockBase;
import com.minebarteksa.orion.integrations.IOrionInfoProvider;
import com.minebarteksa.orion.integrations.infoprovider.IPData;
import com.minebarteksa.orion.render.IRender;
import com.minebarteksa.orion.render.RenderHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TessellatorTest extends TileEntityBlockBase<TessellatorTest.TTTE> implements IOrionInfoProvider
{
    public TessellatorTest() { super(Material.ROCK, "tess_test", Orion.ModID); }

    @Override
    public Class<TTTE> getTileEntityClass() { return TTTE.class; }

    @Nullable
    @Override
    public TTTE createTileEntity(World world, IBlockState state) { return new TTTE(); }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        if(!stack.isItemEnchanted())
            stack.addEnchantment(Enchantment.getEnchantmentByID(19), 50);
        tooltip.add(TextFormatting.YELLOW + "LibOrion Debug Tool!");
        tooltip.add(TextFormatting.WHITE + "For now, just a Tessellator test");
        TESR t = new TESR();
    }

    @Override
    public List<String> addInfo(IPData data)
    {
        List<String> r = new ArrayList<>();
        r.add("Tessellator Test");
        return r;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) { ((TTTE)worldIn.getTileEntity(pos)).onBlockDestroyed(); }

    public static class TTTE extends TileEntity implements IRender
    {
        @SideOnly(Side.CLIENT)
        @Override
        public double getMaxRenderDistanceSquared()
        {
            return 65536.0D;
        }

        public TTTE() { RenderHandler.addRender(this); }

        @Override
        public void render(float pTick, WorldClient world, Minecraft mc)
        {
            /*.log.info("R");
            Tessellator ts = Tessellator.getInstance();
            BufferBuilder bb = ts.getBuffer();

            GL11.glPushMatrix();

            //GlStateManager.translate(this.pos.getX(), this.pos.getY(), this.pos.getZ());

            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.glLineWidth(3f);
            GL11.glColor3ub((byte)0xFF, (byte)0x00, (byte)0x00);

            bb.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);

            bb.pos(this.pos.getX(), this.pos.getY(), this.pos.getZ()).endVertex();
            bb.pos(this.pos.getX(), this.pos.getY() + 10, this.pos.getZ()).endVertex();
            ts.draw();

            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.depthMask(true);

            GL11.glPopMatrix();*/
        }

        public void onBlockDestroyed() { RenderHandler.removeRender(this); }
    }

    public static class TESR extends TileEntitySpecialRenderer<TTTE> implements TESRRegister<TESR>
    {
        public Class<?> getTileEntityClass() { return TTTE.class; }
        public TESR getTESR() { return this; }

        public boolean isGlobalRenderer(TTTE te)
        {
            return true;
        }

        @Override
        public void render(TTTE te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
        {
            Tessellator ts = Tessellator.getInstance();
            BufferBuilder bb = ts.getBuffer();

            GL11.glPushMatrix();

            GlStateManager.disableLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.glLineWidth(3f);
            GL11.glColor3ub((byte)0xFF, (byte)0x00, (byte)0x00);

            drawPlane(bb, ts, x - 0.001, y, z);

            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.depthMask(true);

            GL11.glPopMatrix();
        }

        void drawPlane(BufferBuilder bb, Tessellator ts, double x, double y, double z)
        {
            bb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
            bb.pos(x, y, z).endVertex();
            bb.pos(x, y, z + 1).endVertex();
            bb.pos(x, y + 10, z + 1).endVertex();
            bb.pos(x, y + 10, z).endVertex();
            ts.draw();
        }
    }
}
