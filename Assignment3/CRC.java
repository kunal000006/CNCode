package Assignment3;
import java.util.*;

public class CRC {
    public static void main(String args[]) {
        try (Scanner scan = new Scanner(System.in)) {
            int size;
            System.out.println("Enter the data bits as a binary string: ");
            String dataStr = scan.nextLine();
            int[] data = parseBinaryString(dataStr);
            
            System.out.println("Enter the divisor bits as a binary string:");
            String divisorStr = scan.nextLine();
            int[] divisor = parseBinaryString(divisorStr);
            
            int rem[] = divideDataWithDivisor(data, divisor);
            System.out.println("Generated CRC code is: " + arrayToString(data) + arrayToString(rem));
            
            System.out.println("Enter bits to be sent as a binary string: ");
            String sentDataStr = scan.nextLine();
            int[] sentData = parseBinaryString(sentDataStr);
            
            receiveData(sentData, divisor);
        }
    }

    static int[] divideDataWithDivisor(int oldData[], int divisor[]) {
        int rem[] = new int[divisor.length];
        int i;
        int data[] = new int[oldData.length + divisor.length];
        System.arraycopy(oldData, 0, data, 0, oldData.length);
        System.arraycopy(data, 0, rem, 0, divisor.length);
        for(i = 0; i < oldData.length; i++) {
            if(rem[0] == 1) {
                for(int j = 1; j < divisor.length; j++) {
                    rem[j-1] = exorOperation(rem[j], divisor[j]);
                }
            }
            else {
                for(int j = 1; j < divisor.length; j++) {
                    rem[j-1] = exorOperation(rem[j], 0);
                }
            }
            rem[divisor.length-1] = data[i+divisor.length];
        }
        return rem;
    }

    static int exorOperation(int x, int y) {
        if(x == y) {
            return 0;
        }
        return 1;
    }

    static void receiveData(int data[], int divisor[]) {
        int rem[] = divideDataWithDivisor(data, divisor);
        for(int i = 0; i < rem.length; i++) {
            if(rem[i] != 0) {
                System.out.println("Corrupted data received...");
                return;
            }
        }
        System.out.println("Data received without any error.");
    }
    
    static int[] parseBinaryString(String binaryStr) {
        int[] array = new int[binaryStr.length()];
        for (int i = 0; i < binaryStr.length(); i++) {
            array[i] = Character.getNumericValue(binaryStr.charAt(i));
        }
        return array;
    }
    
    static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int num : array) {
            sb.append(num);
        }
        return sb.toString();
    }
}
