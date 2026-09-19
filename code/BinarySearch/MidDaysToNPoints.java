package BinarySearch;

public class MidDaysToNPoints {

    public int minDays(int n) {

        int ans = 0;

        while(n > 0) {
            int lim = (int)(Math.sqrt(n));

            int minDays = 0;

            int l = 1, r = lim;

            System.out.print("curr n : " + n);

            System.out.print(" curr l and r : " + l + "  " + r);

            while(l <= r) {
                int mid = l + (r - l) / 2;
                long currConDays = getConDays(mid);

                System.out.println(" curr Days : " + currConDays);

                if(currConDays > n) {
                    r = mid - 1;
                } else if(currConDays == n) {
                    ans += mid;
                    n = 0;
                } else {
                    minDays = mid; 
                    l = mid + 1;
                }
            }

            System.out.println(" curr minDays : " + minDays);

            if(n > 0) {
                n -= (int)(getConDays(minDays));
                ans += (minDays + 1);
            }

            System.out.println(" curr ans : " + ans);
        }

        return ans;
        
    }

    private long getConDays(int mid) {
        return (long) (((mid + 1) * mid) / 2);
    }
    
    public static void main(String[] args) {
        MidDaysToNPoints obj = new MidDaysToNPoints();

        System.out.println(obj.minDays(12));
    }
    
}
