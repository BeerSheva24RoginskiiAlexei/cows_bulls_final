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
            String type = request.requestType(); // Используем метод для получения типа запроса
            String data = request.requestData(); // Используем метод для получения данных запроса

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
        // Логика регистрации пользователя
        return new Response(ResponseCode.OK, "User registered successfully");
    }

    private Response handleLogin(String data) {
        // Логика авторизации пользователя
        return new Response(ResponseCode.OK, "User logged in successfully");
    }

    private Response handleCreateGame(String data) {
        // Логика создания игры
        return new Response(ResponseCode.OK, "Game created successfully");
    }

    private Response handleJoinGame(String data) {
        // Логика подключения к игре
        return new Response(ResponseCode.OK, "Joined game successfully");
    }

    private Response handleStartGame(String data) {
        // Логика начала игры
        return new Response(ResponseCode.OK, "Game started successfully");
    }

    private Response handleMakeMove(String data) {
        // Логика выполнения хода
        return new Response(ResponseCode.OK, "Move made successfully");
    }

    private Response handleViewGames(String data) {
        // Логика просмотра игр (доступных и активных)
        return new Response(ResponseCode.OK, "Games retrieved successfully");
    }
}
