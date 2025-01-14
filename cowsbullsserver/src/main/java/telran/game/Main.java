package telran.game;

import telran.game.db.config.BullsCowsPersistenceUnitInfo;
import telran.net.TcpServer;

import java.util.HashMap;
import java.util.Scanner;

import org.hibernate.jpa.HibernatePersistenceProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceUnitInfo;

public class Main {
    static final int PORT = 5000; 
    static EntityManager em;

    public static void main(String[] args) {
        createEntityManager();

        TcpServer tcpServer = new TcpServer(new BullsCowsProtocol(em), PORT);
        new Thread(tcpServer).start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter 'shutdown' to stop the server:");
            String line = scanner.nextLine();
            if ("shutdown".equalsIgnoreCase(line)) {
                tcpServer.shutdown();
                break;
            }
        }
        em.close();
        scanner.close();
    }

    private static void createEntityManager() {
        HashMap<String, Object> hibernateProperties = new HashMap<>();
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");

        PersistenceUnitInfo persistenceUnit = new BullsCowsPersistenceUnitInfo();
        HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
        EntityManagerFactory emf = hibernatePersistenceProvider.createContainerEntityManagerFactory(
                persistenceUnit, hibernateProperties);
        em = emf.createEntityManager();
    }
}
