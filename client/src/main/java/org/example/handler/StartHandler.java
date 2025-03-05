package org.example.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.Message;
import org.example.state.ClientState;

import static org.example.netty.ClientHandler.STATE;

public class StartHandler implements MessageHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, String msg) {
        try {
            Message message = Message.valueOf(msg);

            if (message == Message.START) {
                ctx.channel().attr(STATE).set(ClientState.AWAITING_MOVE);
                System.out.print("Game started! Enter your move (ROCK/PAPER/SCISSORS): ");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid msg: " + msg);
        }
    }
}
