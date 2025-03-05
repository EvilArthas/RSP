package org.example.handler;

import io.netty.channel.ChannelHandlerContext;

public interface MessageHandler {

    void handle(ChannelHandlerContext ctx, String msg);
}
