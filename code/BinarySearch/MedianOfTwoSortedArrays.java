package BinarySearch;

public class MedianOfTwoSortedArrays {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int n = nums1.length;
        int m = nums1.length;

        if (n == 0) {
            if (m % 2 != 0) {
                return nums2[(int) (m / 2)];
            }
            return (nums2[(int) (m / 2)] + nums2[(int) (m / 2) - 1]) / 2;
        }

        if (m == 0) {
            if (n % 2 != 0) {
                return nums1[(int) (n / 2)];
            }
            return (nums1[(int) (n / 2)] + nums1[(int) (n / 2) - 1]) / 2;
        }

        if (n > m) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int l = 0, r = n - 1;

        int len = m + n;

        if (len % 2 == 0) {
            len = len / 2;
        } else {
            len = (len + 1) / 2;
        }

        while (l <= r) {
            int mid = (l + r) / 2;

            int ind2 = 0;

            if (mid < len) {
                ind2 = len - mid - 1;
            }

            
        }
        
    }
    
}
