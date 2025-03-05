package org.example.handler;

import org.example.player.Player;

public interface MessageHandler {

    void handle(Player player, String msg);
}
