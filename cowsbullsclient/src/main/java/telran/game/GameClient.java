package telran.game;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import org.json.JSONObject;

import telran.net.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GameClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;

    public GameClient() throws IOException {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        scanner = new Scanner(System.in);
    }

    public void sendRequest(Request request) {
        out.println(request);
    }

    public Response receiveResponse() {
        try {
            String responseStr = in.readLine();
            if (responseStr != null && !responseStr.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(responseStr, Response.class);
            } else {
                return new Response(ResponseCode.INTERNAL_SERVER_ERROR, "No response from server");
            }
        } catch (IOException e) {
            System.err.println("Error reading response: " + e.getMessage());
            return new Response(ResponseCode.INTERNAL_SERVER_ERROR, "Error reading response from server");
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid response format: " + e.getMessage());
            return new Response(ResponseCode.INTERNAL_SERVER_ERROR, "Invalid response format from server");
        }
    }

    public void signUp() {
        System.out.print("Enter username for registration: ");
        String username = scanner.nextLine();
        System.out.print("Enter birthdate (yyyy-mm-dd): ");
        String birthdate = scanner.nextLine();

        Request request = new Request("REGISTER", username + ":" + birthdate);
        sendRequest(request);

        Response response = receiveResponse();
        System.out.println("Response Code: " + response.responseCode());
        System.out.println("Response Message: " + response.responseData());

        if (response.responseCode() == ResponseCode.OK) {
            System.out.println("Registration successful: " + response.responseData());
        } else {
            System.out.println("Error: " + response.responseCode() + " - " + response.responseData());
        }
    }

    public void signIn() {
        System.out.print("Enter username for login: ");
        String username = scanner.nextLine();

        Request request = new Request("SIGN_IN", username);
        sendRequest(request);

        Response response = receiveResponse();
        System.out.println("Response Code: " + response.responseCode());
        System.out.println("Response Message: " + response.responseData());

        if (response.responseCode() == ResponseCode.OK) {
            System.out.println("Login successful: " + response.responseData());
        } else {
            System.out.println("Error: " + response.responseCode() + " - " + response.responseData());
        }
    }

    public void createGame() {
        Request request = new Request("CREATE_GAME", "");
    
        sendRequest(request);
    
        Response response = receiveResponse();
        System.out.println("Response Code: " + response.responseCode());
        System.out.println("Response Message: " + response.responseData());
    
        if (response.responseCode() == ResponseCode.OK) {
            System.out.println("Game created successfully: " + response.responseData());
        } else {
            System.out.println("Error: " + response.responseCode() + " - " + response.responseData());
        }
    }

   public void joinGame() {
    System.out.print("Enter game ID to join: ");
    long gameId = scanner.nextLong();
    scanner.nextLine();  

    System.out.print("Enter your username: ");
    String username = scanner.nextLine();

    JSONObject jsonData = new JSONObject();
    jsonData.put("gameId", gameId);
    jsonData.put("username", username);

    Request request = new Request("JOIN_GAME", jsonData.toString());
    sendRequest(request);

    Response response = receiveResponse();
    System.out.println("Response Code: " + response.responseCode());
    System.out.println("Response Message: " + response.responseData());

    if (response.responseCode() == ResponseCode.OK) {
        System.out.println("Joined game successfully: " + response.responseData());
    } else {
        System.out.println("Error: " + response.responseCode() + " - " + response.responseData());
    }
}

    public void makeMove() {
        System.out.print("Enter game ID: ");
        long gameId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter your guess sequence: ");
        String sequence = scanner.nextLine();

        System.out.print("Enter number of bulls: ");
        int bulls = scanner.nextInt();

        System.out.print("Enter number of cows: ");
        int cows = scanner.nextInt();
        scanner.nextLine();

        Request request = new Request("MAKE_MOVE", gameId + ":" + sequence + ":" + bulls + ":" + cows);
        sendRequest(request);

        Response response = receiveResponse();
        System.out.println("Response Code: " + response.responseCode());
        System.out.println("Response Message: " + response.responseData());

        if (response.responseCode() == ResponseCode.OK) {
            System.out.println("Move made successfully: " + response.responseData());
        } else {
            System.out.println("Error: " + response.responseCode() + " - " + response.responseData());
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            GameClient client = new GameClient();

            while (true) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Sign Up");
                System.out.println("2. Sign In");
                System.out.println("3. Create Game");
                System.out.println("4. Join Game");
                System.out.println("5. Make Move");
                System.out.println("6. Exit");

                int choice = client.scanner.nextInt();
                client.scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        client.signUp();
                        break;
                    case 2:
                        client.signIn();
                        break;
                    case 3:
                        client.createGame();
                        break;
                    case 4:
                        client.joinGame();
                        break;
                    case 5:
                        client.makeMove();
                        break;
                    case 6:
                        client.close();
                        return; 
                    default:
                        System.out.println("Invalid choice, try again.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error initializing client: " + e.getMessage());
        }
    }
}
