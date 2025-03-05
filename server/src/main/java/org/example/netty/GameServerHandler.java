package org.example.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.game.GameMatcher;
import org.example.handler.MessageHandler;
import org.example.handler.MoveHandler;
import org.example.handler.RegistrationHandler;
import org.example.player.Player;
import org.example.player.PlayerState;

import java.util.Map;

public class GameServerHandler extends SimpleChannelInboundHandler<String> {

    private final Map<PlayerState, MessageHandler> messageHandlers;

    public GameServerHandler() {
        this.messageHandlers = Map.of(
                PlayerState.AWAITING_NAME, new RegistrationHandler(),
                PlayerState.IN_GAME, new MoveHandler());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Player player = new Player(ctx.channel());
        player.setState(PlayerState.AWAITING_NAME);
        ctx.channel().attr(Player.KEY).set(player);

        System.out.println("New client connected: " + player);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        Player player = ctx.channel().attr(Player.KEY).get();

        if (player != null) {
            System.out.println("In: " + msg + " from: " + player);
            messageHandlers.get(player.getState()).handle(player, msg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Player player = ctx.channel().attr(Player.KEY).get();

        if (player != null) {
            player.close();
            System.out.println("Client disconnected: " + player);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Player player = ctx.channel().attr(Player.KEY).get();

        if (player != null) {
            player.close();
            System.out.println("Client disconnected: " + player + " due to: " + cause.getMessage());
        }
    }
}
