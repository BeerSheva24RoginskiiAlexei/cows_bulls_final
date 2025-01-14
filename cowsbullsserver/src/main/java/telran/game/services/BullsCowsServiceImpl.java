package telran.game.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import telran.game.MoveResult;
import telran.game.db.*;
import telran.game.entities.Game;
import telran.game.exceptions.*;

public class BullsCowsServiceImpl implements BullsCowsService {
    private static final long N_DIGITS = 4;
    private final BullsCowsRepository repo;

    public BullsCowsServiceImpl(EntityManager em) {
        this.repo = new BullsCowsRepositoryJpaImp(em);
    }

    @Override
    public void register(String username, LocalDate birthdate) {
        repo.createGamer(username, birthdate);
    }

    @Override
    public void login(String username) {
        if (!repo.isGamerExists(username)) {
            throw new GamerNotFoundException(username);
        }
    }

    @Override
    public long createGame() {
        return repo.createGame(generateSequence());
    }

    public String generateSequence() {
        return new Random().ints(0, 10).distinct().limit(N_DIGITS).boxed()
                .map(i -> i.toString()).collect(Collectors.joining());
    }

    @Override
    public List<Long> getListJoinebleGames(String username) {
        return repo.findJoinebleGames(username);
    }

    @Override
    public void joinToGame(String username, long gameId) {
        repo.joinToGame(username, gameId);
    }

    @Override
    public List<Long> getListStartebleGames(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getListStartebleGames'");
    }

    @Override
    public void startGame(long gameId) {
        if (repo.getPlayerCountInGame(gameId) > 0) {
            repo.startGame(gameId);
        } else {
            throw new IllegalArgumentException("Game must have at least one player to start.");
        }
    }

    @Override
    public List<MoveResult> makeMove(String username, long gameId, String sequence) {
        if (!repo.isPlayerInGame(username, gameId)) {
            throw new IllegalArgumentException("Player is not in this game.");
        }

        if (!repo.isGameStarted(gameId)) {
            throw new IllegalStateException("Game is not started yet.");
        }

        String secretSequence = repo.getSecretSequence(gameId);
        int bulls = calculateBulls(sequence, secretSequence);
        int cows = calculateCows(sequence, secretSequence);

        repo.makeMove(username, gameId, sequence, bulls, cows);

        if (bulls == secretSequence.length()) {
            repo.setWinnerAndFinishGame(username, gameId);
        }

        return repo.findAllMovesGameGamer(username, gameId);
    }

    @Override
    public String getSecretSequence(long gameId) {
        Game game = repo.getGame(gameId);
        if (game == null) {
            throw new EntityNotFoundException("Game with ID " + gameId + " not found.");
        }
        return game.getSequence();
    }

    private int calculateBulls(String sequence, String secretSequence) {
        int bulls = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (sequence.charAt(i) == secretSequence.charAt(i)) {
                bulls++;
            }
        }
        return bulls;
    }

    private int calculateCows(String sequence, String secretSequence) {
        int cows = 0;
        boolean[] secretUsed = new boolean[secretSequence.length()];
        boolean[] guessUsed = new boolean[sequence.length()];

        for (int i = 0; i < sequence.length(); i++) {
            if (sequence.charAt(i) == secretSequence.charAt(i)) {
                secretUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        for (int i = 0; i < sequence.length(); i++) {
            if (!guessUsed[i]) {
                for (int j = 0; j < secretSequence.length(); j++) {
                    if (!secretUsed[j] && sequence.charAt(i) == secretSequence.charAt(j)) {
                        cows++;
                        secretUsed[j] = true;
                        break;
                    }
                }
            }
        }
        return cows;
    }

    @Override
    public boolean isPlayerInGame(String username, long gameId) {
        return repo.isPlayerInGame(username, gameId);
    }

    @Override
    public boolean isGameStarted(long gameId) {
        Game game = repo.getGame(gameId);
        return game.getStartGame() != null;
    }

}
