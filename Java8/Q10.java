import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalDouble;

public class Q10 {

    public static void main(String[] args) {
       Optional<Integer> y= Optional.of(Arrays.asList(1, 2, 3, 4, 5, 6,7,8,9,10)
               .stream()
               .filter(e -> (e > 5))
               .mapToInt(e -> e)
               .sum());
        System.out.println(y.get());
    }
}
