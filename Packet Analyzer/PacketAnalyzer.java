import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PacketAnalyzer {
    public static void main(String[] args) {
        System.out.println("** PACKET ANALYZER **");
        int choice;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("\nEnter which protocol packets you want to see");
            System.out.println("1. Ethernet\n2. IP\n3. TCP\n4. UDP\n0. Exit\nChoice:");
            choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("Exiting...");
                    break;
                case 1:
                    analyzeEthernetPackets();
                    break;
                case 2:
                    analyzeIPPackets();
                    break;
                case 3:
                    analyzeTCPPackets();
                    break;
                case 4:
                    analyzeUDPPackets();
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 4.");
                    break;
            }
        } while (choice != 0);
    }

    private static void analyzeEthernetPackets() {
        System.out.println("Analyzing Ethernet packets...");
        try (BufferedReader reader = new BufferedReader(new FileReader("ethernet_packets.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse Ethernet packet
                System.out.println("Ethernet packet: " + line);
                // Add more parsing logic as needed
            }
        } catch (IOException e) {
            System.err.println("Error reading Ethernet packet file: " + e.getMessage());
        }
    }

    private static void analyzeIPPackets() {
        System.out.println("Analyzing IP packets...");
        try (BufferedReader reader = new BufferedReader(new FileReader("ip_packets.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse IP packet
                System.out.println("IP packet: " + line);
                // Add more parsing logic as needed
            }
        } catch (IOException e) {
            System.err.println("Error reading IP packet file: " + e.getMessage());
        }
    }

    private static void analyzeTCPPackets() {
        System.out.println("Analyzing TCP packets...");
        try (BufferedReader reader = new BufferedReader(new FileReader("tcp_packets.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse TCP packet
                System.out.println("TCP packet: " + line);
                // Add more parsing logic as needed
            }
        } catch (IOException e) {
            System.err.println("Error reading TCP packet file: " + e.getMessage());
        }
    }

    private static void analyzeUDPPackets() {
        System.out.println("Analyzing UDP packets...");
        try (BufferedReader reader = new BufferedReader(new FileReader("udp_packets.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse UDP packet
                System.out.println("UDP packet: " + line);
                // Add more parsing logic as needed
            }
        } catch (IOException e) {
            System.err.println("Error reading UDP packet file: " + e.getMessage());
        }
    }
}