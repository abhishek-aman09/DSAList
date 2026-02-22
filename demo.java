import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class demo {


  public int minimumDifference(int[] nums, int k) {

    int n = nums.length;

    Arrays.sort(nums);

    if (k == n) {
      return nums[n - 1] - nums[0];
    }

    int ans = Integer.MAX_VALUE;

    int j = 0;

    for (int i = k - 1; i < n; i++) {
      ans = Integer.min(ans, nums[i] - nums[j++]);
    }

    return ans;
        
  }










  public static void main(String[] args) {

    
    int arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 15, 14, 13, 12, 11, 10, 20 };


//     Arrays.stream(arr).forEach( e -> 
//       System.out.print(e + "  ")
//       );

//       // boxed() convert primitive to object to use lambda based comparator.
//       System.out.println();

// // sort in desceding, filter even and print
//     Arrays.stream(arr).boxed().sorted((a, b) -> b - a).filter(el -> el % 2 == 0).forEach(
//         el -> System.out.print(el + "  "));
      
    
//     System.out.println();
      
//     String str[] = { "Aman", "Abhishek", "Rahul", "Krishna" };

//     // reduce perform an action on the stream on certain condition.
//     String concat = Arrays.stream(str).reduce("Initial Value : "
//       , (str1, str2) -> {
//         return str1.concat(str2);
//         });

//         System.out.println(concat);

//     // .collect(Collectors.toList) is responsible to change the type
//     System.out.println(
//         Arrays.stream(arr)
//         .boxed()
//             .collect(Collectors.toMap(
//                 (name) -> {
//                   return name; // key
//             }, 
//                 (name) -> {
//                   return name % 2; // value
//                 }
//           ))
//     );

//     // map is not a terminal, reduce is. Map has one param, reduce two.
//     // mapToLong creates a stream of long.
//     long sumOfDoubles = Arrays.stream(str).mapToLong(el -> el.length()).map((el) -> el * 2).reduce(0, (a, b) -> a + b);

//     System.out.println(sumOfDoubles);


    Map<Integer, Integer> map = new LinkedHashMap<>();

    map.put(5, 6);
    map.put(1, 2);
    map.put(3, 4);


    for (Map.Entry<Integer, Integer> pair : map.entrySet()) {
      System.out.println(pair.getKey() + "  " + pair.getValue());
    }

  }
}

