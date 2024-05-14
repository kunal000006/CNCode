package Assignment4;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class GoBackN {
    private final int windowSize;
    private final Deque<Integer> senderBuffer;
    private final Deque<Integer> receiverBuffer;
    private int base;
    private int nextSeqNum;
    private final int timeout; // Timeout in milliseconds
    private Timer timer; // Timer instance

    public GoBackN(int windowSize, int timeout) {
        this.windowSize = windowSize;
        this.senderBuffer = new ArrayDeque<>();
        this.receiverBuffer = new ArrayDeque<>();
        this.base = 0;
        this.nextSeqNum = 0;
        this.timeout = timeout;
    }

    public void send(int packet) {
        senderBuffer.offer(packet);
        System.out.println("Sending packet with sequence number " + packet + "...");
    }

    public void receive(int packet) {
        System.out.println("Received acknowledgment for packet " + packet + ".");
        while (!senderBuffer.isEmpty() && senderBuffer.peek() <= packet) {
            senderBuffer.poll();
            base++;
        }
    }

    public void startTransmission() {
        while (!senderBuffer.isEmpty()) {
            // Send packets up to the window size
            for (int i = nextSeqNum; i < base + windowSize && i < senderBuffer.size(); i++) {
                send(i);
            }

            // Start timer for acknowledgment
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Timeout: No acknowledgment received. Retransmitting...");
                    for (int i = base; i < nextSeqNum; i++) {
                        send(i);
                    }
                }
            }, timeout);

            // Simulate acknowledgment reception
            simulateAcknowledgment();

            // Move to the next window
            nextSeqNum = base + windowSize;
        }
    }

    private void simulateAcknowledgment() {
        try {
            Thread.sleep(timeout / 2); // Simulating acknowledgment delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Simulate acknowledgment for each packet in the window
        for (int i = nextSeqNum - windowSize; i < nextSeqNum && i < senderBuffer.size(); i++) {
            receive(i);
        }
        timer.cancel(); // Cancel timer as acknowledgment is received
    }
}

public class GoBackNExample {
    public static void main(String[] args) {
        int windowSize = 4;
        int timeout = 5000; // Timeout in milliseconds
        GoBackN goBackN = new GoBackN(windowSize, timeout);
        for (int i = 0; i < 15; i++) {
            goBackN.send(i);
        }
        goBackN.startTransmission();
    }
}
