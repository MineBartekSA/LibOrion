package com.minebarteksa.orion.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class SimplePacket<T> implements IMessage
{
    public SimplePacket() {}

    public SimplePacket(T data) { this.data = data; }

    public T data;

    @Override
    public abstract void toBytes(ByteBuf buf);

    @Override
    public abstract void fromBytes(ByteBuf buf);

    public abstract void onMessage(T data, SimplePacket message, MessageContext context);

    public static class SimplePacketHandler implements IMessageHandler<SimplePacket, IMessage>
    {
        @Override
        public IMessage onMessage(SimplePacket message, MessageContext ctx)
        {
            if(ctx.side == Side.SERVER)
                ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> message.onMessage(message.data, message, ctx));
            else
                message.onMessage(message.data, message, ctx);
            return null;
        }
    }
}
