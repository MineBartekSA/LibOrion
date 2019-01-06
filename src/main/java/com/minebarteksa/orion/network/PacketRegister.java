package com.minebarteksa.orion.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;

public class PacketRegister
{
    public IMessageHandler<? super IMessage, ? extends IMessage> handler;
    public Class<? extends IMessage> message;
    public Side side;

    public PacketRegister(Class<? extends IMessageHandler> handler, Class<? extends IMessage> message, Side side)
    {
        try { this.handler = handler.newInstance(); }
        catch (Exception e) { throw new RuntimeException(e); }
        this.message = message;
        this.side = side;
    }

    public PacketRegister(IMessageHandler handler, Class<? extends IMessage> message, Side side)
    {
        this.handler = handler;
        this.message = message;
        this.side = side;
    }
}
