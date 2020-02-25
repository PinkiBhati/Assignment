import java.util.Arrays;
import java.util.OptionalDouble;

public class Q11 {
    public static void main(String[] args) {
        OptionalDouble y = Arrays.asList(1,2,3,4,5,6)
                                 .stream()
                                 .map(e->e*2)
                                 .mapToInt(e->e)
                                 .average();
        System.out.println(y.getAsDouble());
    }
}
