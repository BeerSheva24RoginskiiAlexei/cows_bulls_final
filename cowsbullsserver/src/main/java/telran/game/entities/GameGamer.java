package telran.game.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "game_gamer")
public class GameGamer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "gamer_id")
    private Gamer gamer;

    @Column(name = "is_winner")
    private boolean isWinner;

    public GameGamer(Game game, Gamer gamer) {
        this.game = game;
        this.gamer = gamer;
    }

    public GameGamer() {
    }

    public long getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public Gamer getGamer() {
        return gamer;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setGamer(Gamer gamer) {
        this.gamer = gamer;
    }

    public void setWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }

    @Override
    public String toString() {
        return "GameGamer [id=" + id + ", game=" + game.getId() + ", gamer=" + gamer.getUsername() + ", isWinner=" + isWinner + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameGamer gameGamer = (GameGamer) o;
        return id == gameGamer.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
