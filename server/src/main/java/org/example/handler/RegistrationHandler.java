package org.example.handler;

import org.example.Message;
import org.example.game.GameMatcher;
import org.example.player.Player;
import org.example.player.PlayerState;

public class RegistrationHandler implements MessageHandler {

    @Override
    public void handle(Player player, String msg) {
        if (msg.isBlank()) {
            player.send(Message.NAME_REJECTED);
            return;
        }

        player.setName(msg);
        player.send(Message.NAME_ACCEPTED);

        player.setState(PlayerState.IN_QUEUE);
        GameMatcher.INSTANCE.addPlayer(player);
    }
}
