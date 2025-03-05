package org.example.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import org.example.handler.MessageHandler;
import org.example.handler.ResultHandler;
import org.example.handler.RegistrationHandler;
import org.example.handler.StartHandler;
import org.example.state.ClientState;

import java.util.Map;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    public static final AttributeKey<ClientState> STATE = AttributeKey.valueOf("ClientState");

    private final Map<ClientState, MessageHandler> messageHandlers;

    public ClientHandler() {
        this.messageHandlers = Map.of(
                ClientState.AWAITING_NAME, new RegistrationHandler(),
                ClientState.IN_QUEUE, new StartHandler(),
                ClientState.AWAITING_MOVE, new ResultHandler());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.channel().attr(STATE).set(ClientState.AWAITING_NAME);
        System.out.print("Enter your name: ");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        ClientState state = ctx.channel().attr(STATE).get();
        messageHandlers.get(state).handle(ctx, msg);
    }
}
