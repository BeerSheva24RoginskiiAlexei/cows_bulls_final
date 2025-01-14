package telran.game;

import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

import jakarta.persistence.EntityManager;

public class BullsCowsProtocol implements Protocol {
    private final EntityManager em;

    public BullsCowsProtocol(EntityManager em) {
        this.em = em;
    }

    @Override
    public Response getResponse(Request request) {
        try {
            String type = request.requestType(); 
            String data = request.requestData(); 

            switch (type) {
                case "REGISTER":
                    return handleRegister(data);
                case "LOGIN":
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
        return new Response(ResponseCode.OK, "User registered successfully");
    }

    private Response handleLogin(String data) {
        return new Response(ResponseCode.OK, "User logged in successfully");
    }

    private Response handleCreateGame(String data) {
        return new Response(ResponseCode.OK, "Game created successfully");
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
