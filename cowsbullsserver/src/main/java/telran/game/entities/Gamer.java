package telran.game.entities;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name="gamer")
public class Gamer {
    @Id
    private String username;
    private LocalDate birthdate;

    public Gamer() {
    }

    public Gamer(String username, LocalDate birthdate) {
        this.username = username;
        this.birthdate = birthdate;
    }

    // Геттер для username
    public String getUsername() {
        return username;
    }

    // Геттер для birthdate (если нужен)
    public LocalDate getBirthdate() {
        return birthdate;
    }

    @Override
    public String toString() {
        return "Gamer [username=" + username + ", birthdate=" + birthdate + "]";
    }
}
