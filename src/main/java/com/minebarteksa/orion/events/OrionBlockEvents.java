package com.minebarteksa.orion.events;

import com.minebarteksa.orion.Orion;
import net.minecraft.util.math.BlockPos;

public class OrionBlockEvents
{
    //public static BlockUpdateEvent BUE = new BlockUpdateEvent();
    public static BlockBreak BB = new BlockBreak();

    public static class BlockBreak extends OrionEvent
    {
        public BlockPos pos;
        public void invokeWithValue(BlockPos pos)
        {
            Orion.log.info("BlockBreak event - Pos: " + pos);
            this.pos = pos;
            this.invokeListeners();
        }
    }

    /*public static class BlockUpdateEvent extends OrionEvent
    {
        private List<BlockListenerPair> blocks = new ArrayList<>();

        public BlockPos pos;
        public NextTickListEntry update;

        public void checkForUpdates(World world)
        {
            for(BlockListenerPair pair : blocks)
            {
                for(NextTickListEntry up : world.getPendingBlockUpdates(world.getChunkFromBlockCoords(pair.pos), false))
                {
                    if(up.position == pair.pos)
                    {
                        pos = pair.pos;
                        update = up;
                        pair.listener.onOrionEvent(this);
                    }
                }
            }
        }

        public void addBlock(BlockPos block, IOrionListener listener)
        {
            BlockListenerPair pair = new BlockListenerPair(block, listener);
            if(blocks.contains(pair))
                return;
            blocks.add(pair);
        }

        public void removeBlock(BlockPos block, IOrionListener listener)
        {
            BlockListenerPair pair = new BlockListenerPair(block, listener);
            if(!blocks.contains(pair))
                return;
            blocks.remove(pair);
        }

        @Override
        public void addListener(IOrionListener listener) { return; }

        private class BlockListenerPair
        {
            public BlockPos pos;
            public IOrionListener listener;
            public BlockListenerPair(BlockPos pos, IOrionListener list)
            {
                this.pos = pos;
                this.listener = list;
            }
        }
    }*/
}
