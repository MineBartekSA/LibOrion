package com.minebarteksa.orion.integrations.infoprovider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IPData
{
    private BlockPos pos;
    private EnumFacing side;
    private EntityPlayer player;
    private World world;
    private boolean sneek;

    public IPData(BlockPos position, EnumFacing side, EntityPlayer player, World world)
    {
        this.pos = position;
        this.side = side;
        this.player = player;
        this.sneek = player.isSneaking();
        this.world = world;
    }

    public BlockPos getPos() { return pos; }

    public EnumFacing getSide() { return side; }

    public EntityPlayer getPlayer() { return player; }

    public boolean isSneeking() { return sneek; }

    public World getWorld() { return world; }
}
