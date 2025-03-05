package org.example.handler;

import org.example.Message;
import org.example.Move;
import org.example.player.Player;

public class MoveHandler implements MessageHandler {

    @Override
    public void handle(Player player, String msg) {
        try {
            player.setCurrentMove(Move.valueOf(msg));
            player.send(Message.MOVE_ACCEPTED);

            player.getGameSession().checkMoves();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid msg: " + msg + " from: " + player);
            player.send(Message.MOVE_REJECTED);
        }
    }
}
