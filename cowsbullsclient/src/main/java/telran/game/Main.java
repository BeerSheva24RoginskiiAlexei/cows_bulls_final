package telran.game;

import telran.view.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.hibernate.jpa.HibernatePersistenceProvider;

import jakarta.persistence.*;
import jakarta.persistence.spi.PersistenceUnitInfo;

public class Main {
    static InputOutput io = new StandardInputOutput();
    static EntityManager em;

    public static void main(String[] args) {
        Item[] items = getItems();
        Menu menu = new Menu("Query tool", items);
        menu.perform(io);
    }

    private static Item[] getItems() {
        return new Item[] {
                // Item.of("enter JPQL query", Main::queryProcessing),
                // Item.of("Log in", Main::queryProcessing),
                // Item.of("Sing up", Main::queryProcessing),
                Item.ofExit()
        };
    }



    private static List<String> getLines(List<Object> result) {
        return result.stream().map(Object::toString).toList();
    }

    private static List<String> getArrayLines(List<Object[]> result) {
        return result.stream().map(a -> Arrays.deepToString(a)).toList();
    }
}