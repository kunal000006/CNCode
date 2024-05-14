public class HammingCode {

    public static int[] generateHammingCode(int[] data) {
        int m = data.length;
        int r = 0;
        while (Math.pow(2, r) < m + r + 1) {
            r++;
        }

        int[] hammingCode = new int[m + r];
        int j = 0;
        for (int i = 1; i <= m + r; i++) {
            if ((i & (i - 1)) != 0) {
                hammingCode[i - 1] = data[j++];
            }
        }

        for (int i = 0; i < r; i++) {
            int position = (int) Math.pow(2, i);
            int xor = 0;
            for (int k = position - 1; k < m + r; k += 2 * position) {
                for (int l = k; l < Math.min(k + position, m + r); l++) {
                    xor ^= hammingCode[l];
                }
            }
            hammingCode[position - 1] = xor;
        }

        return hammingCode;
    }

    public static void main(String[] args) {
        int[] data = {0, 1, 1, 0}; // Example data
        int[] hammingCode = generateHammingCode(data);
        System.out.print("Hamming code: ");
        for (int bit : hammingCode) {
            System.out.print(bit);
        }
    }
}
