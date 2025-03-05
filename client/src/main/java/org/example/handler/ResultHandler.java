package org.example.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.Message;
import org.example.state.ClientState;

import static org.example.netty.ClientHandler.STATE;

public class ResultHandler implements MessageHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, String msg) {
        try {
            Message message = Message.valueOf(msg);

            switch (message) {
                case LEAVE -> System.out.println("\nOpponent left the game. You won technically!");
                case WIN -> System.out.println("You won!");
                case LOSE -> System.out.println("You lost!");
                case MOVE_ACCEPTED -> {
                    System.out.println("Waiting for opponent...");
                    return;
                }
                case DRAW -> {
                    System.out.print("Draw! Play again: ");
                    return;
                }
                default -> {
                    System.out.println("Invalid msg: " + msg);
                    return;
                }
            }

            ctx.channel().attr(STATE).set(ClientState.GAME_OVER);
            System.out.println("Game over. Disconnecting...");
            ctx.close();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid msg: " + msg);
        }
    }
}
