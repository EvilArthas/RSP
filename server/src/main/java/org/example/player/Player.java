package org.example.player;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.example.Message;
import org.example.Move;
import org.example.game.GameMatcher;
import org.example.game.GameSession;

public class Player {
    public static final AttributeKey<Player> KEY = AttributeKey.valueOf("Player");

    private final Channel channel;

    private String name;
    private volatile Move currentMove;
    private volatile PlayerState state;
    private volatile GameSession gameSession;

    public Player(Channel channel) {
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Move getCurrentMove() {
        return currentMove;
    }

    public void setCurrentMove(Move currentMove) {
        this.currentMove = currentMove;
    }

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public void send(Message msg) {
        channel.writeAndFlush(msg.name());
        System.out.println("Out: " + msg + " to: " + this);
    }

    public void close() {
        channel.close();

        GameMatcher.INSTANCE.removePlayer(this);
        if (gameSession != null) {
            gameSession.leave(this);
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "channel=" + channel +
                ", name='" + name + '\'' +
                ", currentMove=" + currentMove +
                ", state=" + state +
                '}';
    }
}
