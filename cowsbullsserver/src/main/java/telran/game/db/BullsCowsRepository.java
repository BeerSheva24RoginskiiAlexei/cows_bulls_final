package telran.game.db;

import java.time.LocalDate;
import java.util.List;

import telran.game.MoveResult;
import telran.game.entities.Game;

public interface BullsCowsRepository {
    public boolean isGamerExists(String username);

    public void createGamer(String username, LocalDate birthdate);

    public long createGame(String sequence);

    public List<Long> findJoinebleGames(String username);

    public void joinToGame(String username, long gameId);

    public List<Long> findStartebleGames(String username);

    public void startGame(long gameId);

    public void makeMove(String username, long gameId, String sequence, int bulls, int cows);

    public String findWinnerGame(long gameId);

    public void setWinnerAndFinishGame(String username, long gameId);

    public List<MoveResult> findAllMovesGameGamer(String username, long gameId);

    public long getPlayerCountInGame(long gameId);

    public boolean isPlayerInGame(String username, long gameId);

    public boolean isGameStarted(long gameId);

    public String getSecretSequence(long gameId);

    public Game getGame(long gameId);
}
