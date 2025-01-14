package telran.game.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue
    long id;
    @Column(name = "date_time")
    LocalDateTime dateTime;
    @Column(name = "is_finished")
    boolean isFinished;
    String sequence;

    public void setGameIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    @Override
    public String toString() {
        return "Game [id=" + id + ", dateTime=" + dateTime + ", isFinished=" + isFinished + ", sequence=" + sequence
                + "]";
    }

    public Game(String sequence) {
        this.sequence = sequence;
    }

    public long getId() {
        return id;
    }

    public void setStartGame(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Game() {
    }

}
