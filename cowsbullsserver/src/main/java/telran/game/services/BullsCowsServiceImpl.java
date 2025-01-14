package telran.game.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import telran.game.MoveResult;
import telran.game.db.*;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getListJoinebleGames'");
    }

    @Override
    public void joinToGame(String username, long gameId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'joinToGame'");
    }

    @Override
    public List<Long> getListStartebleGames(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getListStartebleGames'");
    }

    @Override
    public void startGame(String username, long gameId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }

    @Override
    public List<MoveResult> makeMove(String username, long gameId, String sequence) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeMove'");
    }

}
