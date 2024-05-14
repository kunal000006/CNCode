
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 12345;

        try {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            // Accept client connections
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from " + clientSocket.getInetAddress().getHostAddress());

            // Create input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Receive message from the client
            String message = in.readLine();
            System.out.println("Received from client: " + message);

            // Send response to the client
            out.println("Hello from server 4");

            // Close streams and sockets
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

