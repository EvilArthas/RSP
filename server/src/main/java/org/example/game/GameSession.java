package org.example.game;

import org.example.Message;
import org.example.Move;
import org.example.player.Player;
import org.example.player.PlayerState;

import static org.example.Move.PAPER;
import static org.example.Move.ROCK;
import static org.example.Move.SCISSORS;

public class GameSession {

    private final Player player1;
    private final Player player2;

    public GameSession(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void start() {
        player1.setState(PlayerState.IN_GAME);
        player2.setState(PlayerState.IN_GAME);

        sendToBoth(Message.START);
    }

    public void checkMoves() {
        if (player1.getCurrentMove() == null || player2.getCurrentMove() == null) return;

        int result = makeDecision(player1.getCurrentMove(), player2.getCurrentMove());
        System.out.println("Game finished between " + player1.getName() + " and " + player2.getName() + ". Result: " + result);

        if (result == 0) {
            sendToBoth(Message.DRAW);
            resetMoves();
        } else {
            sendResult(result);
            closeChannels();
        }
    }

    public void leave(Player player) {
        if (player == player1) {
            player2.send(Message.LEAVE);

            player2.setGameSession(null);
            player2.close();
        } else {
            player1.send(Message.LEAVE);

            player1.setGameSession(null);
            player1.close();
        }
    }

    private int makeDecision(Move first, Move second) {
        if (first == second) return 0;

        return switch (first) {
            case ROCK -> second == SCISSORS ? 1 : -1;
            case PAPER -> second == ROCK ? 1 : -1;
            case SCISSORS -> second == PAPER ? 1 : -1;
        };
    }

    private void sendResult(int result) {
        player1.send(result == 1 ? Message.WIN : Message.LOSE);
        player2.send(result == -1 ? Message.WIN : Message.LOSE);
    }

    private void resetMoves() {
        player1.setCurrentMove(null);
        player2.setCurrentMove(null);
    }

    private void closeChannels() {
        player1.setGameSession(null);
        player2.setGameSession(null);

        player1.close();
        player2.close();
    }

    private void sendToBoth(Message message) {
        player1.send(message);
        player2.send(message);
    }
}
