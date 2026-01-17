package BitManipulation;

public class OneTwoCompliment {

    public int minBitFlips(int start, int goal) {

        int ans = 0, xor = 0;

        xor = start ^ goal;

        while (xor > 0) {
            ans++;
            xor = xor & xor - 1;
        }

        return ans;
        
    }
}
