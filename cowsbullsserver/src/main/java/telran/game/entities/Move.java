package telran.game.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "move")
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "game_gamer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private GameGamer gameGamer;

    @Column(name = "bulls")
    private int bulls;

    @Column(name = "cows")
    private int cows;

    @Column(name = "sequence")
    private String sequence;

    // Конструктор
    public Move(GameGamer gameGamer, int bulls, int cows, String sequence) {
        this.gameGamer = gameGamer;
        this.bulls = bulls;
        this.cows = cows;
        this.sequence = sequence;
    }

    // Геттеры и сеттеры
    public GameGamer getGameGamer() {
        return gameGamer;
    }

    public void setGameGamer(GameGamer gameGamer) {
        this.gameGamer = gameGamer;
    }

    public int getBulls() {
        return bulls;
    }

    public void setBulls(int bulls) {
        this.bulls = bulls;
    }

    public int getCows() {
        return cows;
    }

    public void setCows(int cows) {
        this.cows = cows;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "Move [id=" + id + ", bulls=" + bulls + ", cows=" + cows + ", sequence=" + sequence + ", gameGamerId="
                + (gameGamer != null ? gameGamer.getId() : "null") + "]";
    }
}
