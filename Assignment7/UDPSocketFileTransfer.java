import java.io.*;
import java.net.*;

public class UDPSocketFileTransfer {
    public static void main(String[] args) {
        // Sender
        new Thread(() -> {
            try {
                DatagramSocket socket = new DatagramSocket();
                InetAddress receiverAddress = InetAddress.getByName("192.168.115.131");
                int receiverPort = 9876; // Change this to the receiver's port

                // Send script file
                sendFile(socket, receiverAddress, receiverPort, "script.txt");

                // Send audio file
                sendFile(socket, receiverAddress, receiverPort, "audio.mp3");

                // Send video file
                sendFile(socket, receiverAddress, receiverPort, "video.mp4");

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Receiver
        new Thread(() -> {
            try {
                DatagramSocket socket = new DatagramSocket(9876); // Receiver's port
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Receive script file
                receiveFile(socket, packet, "received_script.txt");

                // Receive audio file
                receiveFile(socket, packet, "received_audio.mp3");

                // Receive video file
                receiveFile(socket, packet, "received_video.mp4");

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void sendFile(DatagramSocket socket, InetAddress receiverAddress, int receiverPort, String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            DatagramPacket packet = new DatagramPacket(buffer, bytesRead, receiverAddress, receiverPort);
            socket.send(packet);
        }
        fis.close();
    }

    private static void receiveFile(DatagramSocket socket, DatagramPacket packet, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        int bytesRead;
        while (true) {
            socket.receive(packet);
            bytesRead = packet.getLength();
            fos.write(packet.getData(), 0, bytesRead);
            if (bytesRead < 1024) break; // Last packet received
        }
        fos.close();
    }
}