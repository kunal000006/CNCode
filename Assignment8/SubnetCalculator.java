import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SubnetCalculator {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter IP Address (in format xxx.xxx.xxx.xxx): ");
            String ipAddress = scanner.nextLine();

            System.out.print("Enter number of subnet bits: ");
            int subnetBits = scanner.nextInt();

            String[] parts = ipAddress.split("\\.");
            byte[] ipBytes = new byte[]{
                    (byte) Integer.parseInt(parts[0]),
                    (byte) Integer.parseInt(parts[1]),
                    (byte) Integer.parseInt(parts[2]),
                    (byte) Integer.parseInt(parts[3])
            };

            InetAddress inetAddress = InetAddress.getByAddress(ipBytes);
            byte[] subnetMaskBytes = calculateSubnetMask(subnetBits);

            System.out.println("Subnet Mask: " + InetAddress.getByAddress(subnetMaskBytes).getHostAddress());

            byte[] networkAddressBytes = calculateNetworkAddress(ipBytes, subnetMaskBytes);
            System.out.println("Network Address: " + InetAddress.getByAddress(networkAddressBytes).getHostAddress());

        } catch (UnknownHostException e) {
            System.out.println("Invalid IP Address format.");
        }
    }

    private static byte[] calculateSubnetMask(int subnetBits) {
        int maskBits = 0xFFFFFFFF << (32 - subnetBits);
        byte[] maskBytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            maskBytes[i] = (byte) ((maskBits >> (24 - (i * 8))) & 0xFF);
        }
        return maskBytes;
    }

    private static byte[] calculateNetworkAddress(byte[] ipAddressBytes, byte[] subnetMaskBytes) {
        byte[] networkAddressBytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            networkAddressBytes[i] = (byte) (ipAddressBytes[i] & subnetMaskBytes[i]);
        }
        return networkAddressBytes;
    }
}
