package DynamicProgramming;

public class NumOfSub1498 {


    public int numSubseq(int[] nums, int target) {

        int n = nums.length;

        return helper(0, n, nums, target, 0, 0);
        
    }

    private int helper(int i, int n, int nums[], int target, int minInd, int maxInd) {

        if (i >= n) {
            return 0;
        }

        int currMin = nums[minInd] < nums[i] ? minInd : i;
        int currMax = nums[maxInd] > nums[i] ? maxInd : i;

        if (target - (nums[currMin] + nums[currMax]) >= 0) {
            return 1 + (helper(i + 1, n, nums, target, currMin, currMax)
                    + helper(i + 1, n, nums, target, minInd, maxInd));
        } else {
            return helper(i + 1, n, nums, target, minInd, maxInd);
        }
    }
    
    public static void main(String[] args) {

        NumOfSub1498 obj = new NumOfSub1498();

        int arr[] = { 3, 5, 6, 7, };

        System.out.println(obj.numSubseq(arr, 9));
        
    }
    
}
