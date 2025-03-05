package org.example.input;

import io.netty.channel.Channel;
import org.example.Move;
import org.example.state.ClientState;

import java.util.Scanner;

import static org.example.netty.ClientHandler.STATE;

public class KeyboardInput implements UserInput {

    private final Scanner scanner;

    public KeyboardInput() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void process(Channel channel) {
        new Thread(() -> {
            while (channel.isActive()) {
                if (scanner.hasNextLine()) {
                    String input = scanner.nextLine();
                    handleUserInput(channel, input);
                }
            }
        }).start();
    }

    private void handleUserInput(Channel channel, String input) {
        ClientState state = channel.attr(STATE).get();

        switch (state) {
            case AWAITING_NAME -> channel.writeAndFlush(input);
            case AWAITING_MOVE -> {
                if (isValidMove(input)) {
                    channel.writeAndFlush(input.toUpperCase());
                } else {
                    System.out.print("Invalid move! Use ROCK/PAPER/SCISSORS: ");
                }
            }
            default -> System.out.println("Command not allowed in current state");
        }
    }

    private boolean isValidMove(String input) {
        return input.equalsIgnoreCase(Move.ROCK.name())
                || input.equalsIgnoreCase(Move.PAPER.name())
                || input.equalsIgnoreCase(Move.SCISSORS.name());
    }
}
