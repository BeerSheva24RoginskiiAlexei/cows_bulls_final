package telran.game.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
@Entity
@Table(name="gamer")
public class Gamer {
    @Id
    String username;
    LocalDate birthdate;

    public Gamer() {
    }

    public Gamer(String username, LocalDate birthdate) {
        this.username = username;
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Gamer [username=" + username + ", birthdate=" + birthdate + "]";
    }
}
