package telran.game.exceptions;

import java.util.NoSuchElementException;

public class GamerNotFoundException extends NoSuchElementException {
  public GamerNotFoundException(String username) {
    super(String.format("User %s hot found", username));
  }
}
