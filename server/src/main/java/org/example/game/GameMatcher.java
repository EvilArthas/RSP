package org.example.game;

import org.example.player.Player;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameMatcher {
    public static final GameMatcher INSTANCE = new GameMatcher();

    private final Queue<Player> waitingPlayers;

    public GameMatcher() {
        this.waitingPlayers = new ConcurrentLinkedQueue<>();
    }

    public void addPlayer(Player player) {
        waitingPlayers.add(player);
        tryMatchPlayers();
    }

    public void removePlayer(Player player) {
        waitingPlayers.remove(player);
    }

    private void tryMatchPlayers() {
        while (waitingPlayers.size() >= 2) {
            Player p1 = waitingPlayers.poll();
            Player p2 = waitingPlayers.poll();

            GameSession session = new GameSession(p1, p2);
            p1.setGameSession(session);
            p2.setGameSession(session);

            session.start();
            System.out.println("Game started between " + p1.getName() + " and " + p2.getName());
        }
    }
}
