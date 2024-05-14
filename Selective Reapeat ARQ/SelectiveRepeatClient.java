import java.net.*;

public class SelectiveRepeatClient {
    static final int PORT = 12345;
    static final int MAX_SEQ_NUM = 7;
    static final int WINDOW_SIZE = 4;
    static final int TIMEOUT = 2000;

    static DatagramSocket senderSocket;
    static InetAddress receiverAddress;
    static int base = 0;
    static int nextSeqNum = 0;
    static String[] data = new String[MAX_SEQ_NUM + 1];
    static boolean[] ackReceived = new boolean[MAX_SEQ_NUM + 1];
    static int framesSent = 0;
    static int framesResent = 0;

    public static void main(String[] args) throws Exception {
        senderSocket = new DatagramSocket();
        receiverAddress = InetAddress.getLocalHost();

        for (int i = 0; i <= MAX_SEQ_NUM; i++) {
            data[i] = "Data_" + i;
        }

        Thread senderThread = new Thread(() -> {
            try {
                send();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        senderThread.start();
    }

    static void send() throws Exception {
        while (base < MAX_SEQ_NUM) {
            for (int i = base; i < Math.min(base + WINDOW_SIZE, MAX_SEQ_NUM); i++) {
                sendPacket(i);
                framesSent++;
            }
            waitForACK();
        }
    }

    static void sendPacket(int seqNum) throws Exception {
        System.out.println("Sending packet " + seqNum);
        byte[] sendData = (seqNum + ":" + data[seqNum]).getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receiverAddress, PORT);
        senderSocket.send(sendPacket);
        nextSeqNum++;
    }

    static void waitForACK() throws Exception {
        senderSocket.setSoTimeout(TIMEOUT);
        while (true) {
            try {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                senderSocket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                int ackNum = Integer.parseInt(message.trim());
                System.out.println("Received ACK " + ackNum);
                ackReceived[ackNum] = true;
                // Slide the window
                while (ackReceived[base]) {
                    base++;
                }
                break;
            } catch (SocketTimeoutException e) {
                System.out.println("Timeout occurred. Resending packets in the window.");
                for (int i = base; i < nextSeqNum; i++) {
                    if (!ackReceived[i]) {
                        sendPacket(i);
                        framesResent++;
                    }
                }
            }
        }
    }
}
