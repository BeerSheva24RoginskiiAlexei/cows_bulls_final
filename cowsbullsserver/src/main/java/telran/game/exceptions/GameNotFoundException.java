package telran.game.exceptions;

import java.util.NoSuchElementException;

public class GameNotFoundException extends NoSuchElementException {
    public GameNotFoundException(long gameId) {
        super(String.format("game with id: %d not found", gameId));
    }
}
