package telran.game.services;

import java.time.LocalDate;
import java.util.List;

import telran.game.MoveResult;
import telran.game.db.*;
import telran.game.exceptions.*;

public class BullsCowsServiceImpl implements BullsCowsService {
    private static final long N_DIGITS = 4;
     BullsCowsRepository repo = new BullsCowsRepositoryJpaImp();
    String username = "";

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createGame'");
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
