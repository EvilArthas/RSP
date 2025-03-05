package org.example.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.Message;
import org.example.state.ClientState;

import static org.example.netty.ClientHandler.STATE;

public class RegistrationHandler implements MessageHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, String msg) {
        try {
            Message message = Message.valueOf(msg);

            if (message == Message.NAME_ACCEPTED) {
                ctx.channel().attr(STATE).set(ClientState.IN_QUEUE);
                System.out.println("Waiting for opponent...");
            } else if (message == Message.NAME_REJECTED) {
                System.out.print("Enter your name again: ");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid msg: " + msg);
        }
    }
}
