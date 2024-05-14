import java.net.*;

public class SelectiveRepeatServer {
    static final int PORT = 12345;
    static final int MAX_SEQ_NUM = 7;

    public static void main(String[] args) throws Exception {
        DatagramSocket receiverSocket = new DatagramSocket(PORT);

        while (true) {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            receiverSocket.receive(receivePacket);
            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
            int seqNum = Integer.parseInt(message.split(":")[0]);
            String data = message.split(":")[1];
            System.out.println("Received packet " + seqNum + ": " + data);

            if (new java.util.Random().nextInt(10) < 5) {
                System.out.println("Packet loss detected. Discarding packet " + seqNum);
            } else {
                System.out.println("Sending ACK for packet " + seqNum);
                sendACK(seqNum, receivePacket.getAddress(), receivePacket.getPort());
            }

            if (seqNum == MAX_SEQ_NUM) {
                break;
            }
        }

        receiverSocket.close();
    }

    static void sendACK(int seqNum, InetAddress clientAddress, int clientPort) throws Exception {
        DatagramSocket ackSocket = new DatagramSocket();
        byte[] ackData = String.valueOf(seqNum).getBytes();
        DatagramPacket ackPacket = new DatagramPacket(ackData, ackData.length, clientAddress, clientPort);
        ackSocket.send(ackPacket);
        ackSocket.close();
    }
}
