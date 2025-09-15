import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class demo {

  public static void main(String[] args) {

    
    int arr[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 15, 14, 13, 12, 11, 10, 20 };


    Arrays.stream(arr).forEach( e -> 
      System.out.print(e + "  ")
      );

      // boxed() convert primitive to object to use lambda based comparator.
      System.out.println();

// sort in desceding, filter even and print
    Arrays.stream(arr).boxed().sorted((a, b) -> b - a).filter(el -> el % 2 == 0).forEach(
        el -> System.out.print(el + "  "));
      
    
    System.out.println();
      
    String str[] = { "Aman", "Abhishek", "Rahul", "Krishna" };

    // reduce perform an action on the stream on certain condition.
    String concat = Arrays.stream(str).reduce("Initial Value : "
      , (str1, str2) -> {
        return str1.concat(str2);
        });

        System.out.println(concat);

    // .collect(Collectors.toList) is responsible to change the type
    System.out.println(
        Arrays.stream(arr)
        .boxed()
            .collect(Collectors.toMap(
                (name) -> {
                  return name; // key
            }, 
                (name) -> {
                  return name % 2; // value
                }
          ))
    );

    // map is not a terminal, reduce is. Map has one param, reduce two.
    // mapToLong creates a stream of long.
    long sumOfDoubles = Arrays.stream(str).mapToLong(el -> el.length()).map((el) -> el * 2).reduce(0, (a, b) -> a + b);

    System.out.println(sumOfDoubles);


  }
}

