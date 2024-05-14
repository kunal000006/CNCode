import java.util.Scanner;

public class IPAddressClassification {

    public static boolean isValid(int[] octetValues){
        for (int value : octetValues) {
            if (value < 0 || value > 255) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {

            System.out.print("Enter IP Address:");
            String ipAddress = sc.nextLine();

            String[] octets = ipAddress.split("\\.");

            int[] octetValues = new int[octets.length];

            for (int i = 0; i < octets.length; i++) {
                
                if (octets[i].startsWith("0") && octets[i].length() > 1) {
                    System.out.println("Leading zeros are not allowed.");
                    return; 
                }
                octetValues[i] = Integer.parseInt(octets[i]);
            }

            if((octetValues[0]>=0 && octetValues[0]<=127) && isValid(octetValues)){
                System.out.println("Class A found");
            }
            else if((octetValues[0]>=128 && octetValues[0]<=191) && isValid(octetValues)){
                System.out.println("Class B found");
            }
            else if((octetValues[0]>=192 && octetValues[0]<=223) && isValid(octetValues)){
                System.out.println("Class C found");
            }
            else if((octetValues[0]>=224 && octetValues[0]<=239) && isValid(octetValues)){
                System.out.println("Class D found");
            }
            else if((octetValues[0]>=240 && octetValues[0]<=255) && isValid(octetValues)){
                System.out.println("Class E found");
            }
            else{
                System.out.println("Invalid IP Address");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid IP Address - Please enter a valid IP address format.");
        }
    }
}
