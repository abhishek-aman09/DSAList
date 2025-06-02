package DynamicProgramming.OneDimentional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BestTimeToBuySellStocks {

    /*
     * For the given array [ 2, 100, 150, 120],
        The maximum profit can be achieved by buying the stock at minute 0 when its price is Rs. 2 and selling it at minute 2 when its price is Rs. 150.
        So, the output will be 148.
        https://www.naukri.com/code360/problems/stocks-are-profitable_893405?source=youtube&campaign=striver_dp_videos&utm_source=youtube&utm_medium=affiliate&utm_campaign=striver_dp_videos&leftPanelTabValue=PROBLEM

    
     */

    public static int maximumProfit(ArrayList<Integer> prices) {
        int ans = 0, temp = 0;

        for (int i = 1; i < prices.size(); i++) {
            temp += prices.get(i) - prices.get(i - 1);
            temp = Integer.max(temp, 0);
            ans = Integer.max(temp, ans);
        }

        return ans;
    }
    
    // Best Time to Buy and Sell Stock II.  we can buy and sell stocks as many number of times.
    //https://www.naukri.com/code360/problems/best-time-to-buy-and-sell-stock-ii_630282?leftPanelTabValue=PROBLEM
    public static long getMaximumProfit(int n, long[] values) {

        long ans = 0, temp = 0;

        for (int i = 1; i < values.length; i++) {
            long curr = values[i] - values[i - 1];
            if (curr < 0) {
                ans += temp;
                temp = 0;
                continue;
            }

            temp += curr;

            if (i == values.length - 1) {
                ans += temp;
            }
        }

        return ans;
    }
    

    public static int maxProfit(int[] prices) {
        int ans = 0, temp = 0;

        ArrayList<Integer> diff = new ArrayList<>();

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());

        for (int i = 1; i < prices.length; i++) {
            diff.add(prices[i] - prices[i - 1]);
        }

        for (int i = 0; i < diff.size(); i++) {
            int curr = diff.get(i);
            if (temp + curr <= 0) {
                queue.add(temp);
                temp = 0;
                continue;
            }
            queue.remove(temp);
            temp += curr;
            queue.add(temp);
        }

        ans += queue.poll();
        if (!queue.isEmpty()) {
            ans += queue.poll();
        }

        return ans;
    }

    public static void main(String[] args) {
        int arr[] = { 1, 3, 1, 2, 4, 8 };
        System.out.println(maxProfit(arr));
    }
}
