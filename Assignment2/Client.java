
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Server's IP address
        int port = 12345;

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(serverAddress, port);

            // Create input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Send message to the server
            out.println("Hello from client");

            // Receive response from the server
            String response = in.readLine();
            System.out.println("Server says: " + response);

            // Close streams and socket
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
