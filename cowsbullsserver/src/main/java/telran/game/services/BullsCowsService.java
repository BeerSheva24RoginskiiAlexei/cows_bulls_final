package telran.game.services;

import java.time.LocalDate;
import java.util.List;
import telran.game.MoveResult;

public interface BullsCowsService {
    public void register(String username, LocalDate birthdate);

    public void login(String username);

    public long createGame();

    public List<Long> getListJoinebleGames(String username);

    public void joinToGame(String username, long gameId);

    public void startGame(long gameId);

    public List<MoveResult> makeMove(String username, long gameId, String sequence);

    public String getSecretSequence(long gameId);

    boolean isPlayerInGame(String username, long gameId);

    boolean isGameStarted(long gameId);
}
