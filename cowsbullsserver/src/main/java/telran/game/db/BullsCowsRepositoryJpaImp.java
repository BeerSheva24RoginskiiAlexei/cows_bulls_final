package telran.game.db;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import telran.game.MoveResult;
import telran.game.entities.*;
import telran.game.exceptions.*;

public class BullsCowsRepositoryJpaImp implements BullsCowsRepository {
    private final EntityManager em;

    public BullsCowsRepositoryJpaImp(EntityManager em) {
        this.em = em;
    }

    @Override
    public void createGamer(String username, LocalDate birthdate) {
        if (isGamerExists(username)) {
            throw new GamerAlreadyExistsException(username);
        }
        var transaction = em.getTransaction();
        transaction.begin();
        try {
            Gamer gamer = new Gamer(username, birthdate);
            em.persist(gamer);
            transaction.commit();
            System.out.println("Gamer saved successfully: " + gamer);
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public boolean isGamerExists(String username) {
        Gamer gamer = em.find(Gamer.class, username);
        return gamer != null;
    }

    private Gamer getGamer(String username) {
        Gamer gamer = em.find(Gamer.class, username);
        if (gamer == null) {
            throw new GamerNotFoundException(username);
        }
        return gamer;
    }

    @Override
    public long createGame(String sequence) {
        var transaction = em.getTransaction();
        transaction.begin();
        try {
            Game game = new Game(sequence);
            em.persist(game);
            transaction.commit();
            return game.getId();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public List<Long> findJoinebleGames(String username) {
        TypedQuery<Long> query = em.createQuery(
                "select game.id from GameGamer where gamer.username != ?1 and game.dateTime is null",
                Long.class);
        query.setParameter(1, username);
        return query.getResultList();
    }

    public void joinToGame(String username, long gameId) {
        var transaction = em.getTransaction();
        transaction.begin();
        try {
            Game game = getGame(gameId);
            Gamer gamer = getGamer(username);
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(gg) FROM GameGamer gg WHERE gg.game.id = :gameId AND gg.gamer.username = :username",
                    Long.class);
            query.setParameter("gameId", gameId);
            query.setParameter("username", username);
            long count = query.getSingleResult();

            if (count > 0) {
                throw new RuntimeException("Player already joined this game");
            }

            GameGamer gameGamer = new GameGamer(game, gamer);
            em.persist(gameGamer);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error during game join: " + e.getMessage());
            throw new RuntimeException("Error while committing the transaction", e);
        }
    }

    @Override
    public Game getGame(long gameId) {
        Game game = em.find(Game.class, gameId);
        if (game == null) {
            throw new GameNotFoundException(gameId);
        }
        return game;
    }

    @Override
    public List<Long> findStartebleGames(String username) {
        TypedQuery<Long> query = em.createQuery(
                "select game.id from GameGamer where gamer.username != ?1 and game.dateTime is null",
                Long.class);
        query.setParameter(1, username);
        return query.getResultList();
    }

    @Override
    public void startGame(long gameId) {
        Game game = getGame(gameId);
        if (game == null) {
            throw new EntityNotFoundException("Game with ID " + gameId + " not found.");
        }

        var transaction = em.getTransaction();
        transaction.begin();
        try {
            game.setStartGame(LocalDateTime.now());
            em.merge(game);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public boolean isPlayerInGame(String username, long gameId) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(gg) FROM GameGamer gg WHERE gg.gamer.username = :username AND gg.game.id = :gameId",
                Long.class);
        query.setParameter("username", username);
        query.setParameter("gameId", gameId);
        return query.getSingleResult() > 0;
    }

    @Override
    public boolean isGameStarted(long gameId) {
        Game game = getGame(gameId);
        return game != null && game.getStartGame() != null;
    }

    @Override
    public void makeMove(String username, long gameId, String sequence, int bulls, int cows) {
        // if (gameGamer == null) {
        //     throw new EntityNotFoundException("Player not found in the game.");
        // }

        var transaction = em.getTransaction();
        transaction.begin();
        try {
            GameGamer gameGamer = getGameGamer(username, gameId);
            Move move = new Move(gameGamer, bulls, cows, sequence);
            em.persist(move);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Error while committing the transaction: " + e.getMessage(), e);
        }
    }

    @Override
    public String findWinnerGame(long gameId) {
        TypedQuery<String> query = em.createQuery(
                "SELECT gamer.username FROM GameGamer where game.id = ?1 and is_winner = true", String.class);
        query.setParameter(1, gameId);
        List<String> list = query.getResultList();
        return list.isEmpty() ? "" : list.get(0);
    }

    @Override
    public void setWinnerAndFinishGame(String username, long gameId) {
        Game game = getGame(gameId);
        GameGamer gameGamer = getGameGamer(username, gameId);
        var transaction = em.getTransaction();
        transaction.begin();
        try {
            gameGamer.setWinner(true);
            em.persist(gameGamer);
            game.setGameIsFinished(true);
            em.persist(game);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    private GameGamer getGameGamer(String username, long gameId) {
        TypedQuery<GameGamer> query = em.createQuery(
                "SELECT g FROM GameGamer g WHERE g.game.id = :gameId AND g.gamer.username = :username",
                GameGamer.class);
        query.setParameter("gameId", gameId);
        query.setParameter("username", username);
        List<GameGamer> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<MoveResult> findAllMovesGameGamer(String username, long gameId) {
        TypedQuery<Move> query = em.createQuery(
                "SELECT m FROM Move m JOIN m.gameGamer gg WHERE gg.game.id = :gameId AND gg.gamer.username = :username",
                Move.class);
        query.setParameter("gameId", gameId);
        query.setParameter("username", username);

        List<Move> moves = query.getResultList();
        return moves.stream()
                .map(move -> new MoveResult(move.getSequence(), move.getBulls(), move.getCows()))
                .toList();
    }

    @Override
    public long getPlayerCountInGame(long gameId) {
        TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(gc) FROM GameGamer gc WHERE gc.game.id = :gameId", Long.class);
        query.setParameter("gameId", gameId);
        return query.getSingleResult();
    }

    @Override
    public String getSecretSequence(long gameId) {
        Game game = em.find(Game.class, gameId);
        if (game == null) {
            throw new EntityNotFoundException("Game with ID " + gameId + " not found.");
        }
        return game.getSequence();
    }

}
