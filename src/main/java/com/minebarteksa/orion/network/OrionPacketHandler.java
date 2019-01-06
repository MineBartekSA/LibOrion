package com.minebarteksa.orion.network;

import com.minebarteksa.orion.Orion;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class OrionPacketHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Orion.ModID);

    public static class PotionPacket implements IMessage
    {
        public PotionPacket() {}

        private int entityID;

        public PotionPacket(int entityID) { this.entityID = entityID; }

        @Override
        public void toBytes(ByteBuf buf) { buf.writeInt(this.entityID); }

        @Override
        public void fromBytes(ByteBuf buf) { this.entityID = buf.readInt(); }

        public static class PotionPacketHandler implements IMessageHandler<PotionPacket, IMessage>
        {
            @Override
            public IMessage onMessage(PotionPacket message, MessageContext ctx)
            {
                ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
                    Orion.tp.updateScrollValue(message.entityID);
                });
                return null;
            }
        }
    }
}

