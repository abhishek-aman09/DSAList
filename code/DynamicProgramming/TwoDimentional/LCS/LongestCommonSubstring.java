package DynamicProgramming.TwoDimentional.LCS;

public class LongestCommonSubstring {
    
    // https://www.naukri.com/code360/problems/longest-common-substring_1214702?leftPanelTabValue=PROBLEM
    static int LCSubStr(String str1, String str2) {

        int n = str1.length();
        int m = str2.length();

        int dp[][] = new int[n + 1][m + 1];

        for(int i = 0; i <= n; i++) dp[i][0] = 0;
        for (int i = 0; i <= m; i++) dp[0][i] = 0;
        
        int ans = 0;

        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= m; j++) {
                if(str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    // This condition make sure that we don't carry any longest
                    // common subsequence result from previous comparison/
                    // if there were no matching char for any index, mark it 0
                    dp[i][j] = 0;
                }
                ans = Integer.max(ans, dp[i][j]);
            }
        }

        return ans;
    }
}
