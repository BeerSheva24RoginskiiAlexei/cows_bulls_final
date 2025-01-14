package telran.game;

import telran.game.services.BullsCowsService;
import telran.game.services.BullsCowsServiceImpl;
import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

import java.time.LocalDate;

import jakarta.persistence.EntityManager;

public class BullsCowsProtocol implements Protocol {
    private final EntityManager em;

    public BullsCowsProtocol(EntityManager em) {
        this.em = em;
    }

    @Override
    public Response getResponse(Request request) {
        System.out.println("Received request: " + request);
        try {
            String type = request.requestType();
            String data = request.requestData();

            switch (type) {
                case "REGISTER":
                    return handleRegister(data);
                case "SIGN_IN":
                    return handleLogin(data);
                case "CREATE_GAME":
                    return handleCreateGame(data);
                case "JOIN_GAME":
                    return handleJoinGame(data);
                case "START_GAME":
                    return handleStartGame(data);
                case "MAKE_MOVE":
                    return handleMakeMove(data);
                case "VIEW_GAMES":
                    return handleViewGames(data);
                default:
                    return new Response(ResponseCode.WRONG_TYPE, "Unknown command: " + type);
            }
        } catch (Exception e) {
            return new Response(ResponseCode.WRONG_DATA, e.getMessage());
        }
    }

    private Response handleRegister(String data) {
        String[] parts = data.split(":");
        if (parts.length != 2) {
            return new Response(ResponseCode.WRONG_DATA, "Invalid data format for register");
        }
    
        String username = parts[0];
        LocalDate birthdate;
        try {
            birthdate = LocalDate.parse(parts[1]);
        } catch (Exception e) {
            return new Response(ResponseCode.WRONG_DATA, "Invalid birthdate format");
        }
    
        try {
            BullsCowsService service = new BullsCowsServiceImpl(em);
            service.register(username, birthdate);
            return new Response(ResponseCode.OK, "User registered successfully");
        } catch (Exception e) {
            return new Response(ResponseCode.WRONG_DATA, e.getMessage());
        }
    }
    

    private Response handleLogin(String data) {
        String[] parts = data.split(":");
        if (parts.length != 1) {
            return new Response(ResponseCode.WRONG_DATA, "Invalid data format for login");
        }
    
        String username = parts[0];
    
        try {
            BullsCowsService service = new BullsCowsServiceImpl(em);
            service.login(username); 
            return new Response(ResponseCode.OK, "User logged in successfully");
        } catch (Exception e) {
            return new Response(ResponseCode.WRONG_DATA, e.getMessage());
        }
    }

    private Response handleCreateGame(String data) {
        try {
            BullsCowsService service = new BullsCowsServiceImpl(em);
    
            long gameId = service.createGame();
    
            return new Response(ResponseCode.OK, "Game created successfully. Game ID: " + gameId);
        } catch (Exception e) {
            return new Response(ResponseCode.WRONG_DATA, "Error creating game: " + e.getMessage());
        }
    }

    private Response handleJoinGame(String data) {
        return new Response(ResponseCode.OK, "Joined game successfully");
    }

    private Response handleStartGame(String data) {
        return new Response(ResponseCode.OK, "Game started successfully");
    }

    private Response handleMakeMove(String data) {
        return new Response(ResponseCode.OK, "Move made successfully");
    }

    private Response handleViewGames(String data) {
        return new Response(ResponseCode.OK, "Games retrieved successfully");
    }
}
