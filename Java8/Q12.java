import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

public class Q12 {

    public static void main(String[] args) {

        //Arrays.asList(1,2,3,4,5).stream().filter(e-> e%2==0).forEach(System.out::println);

        //Arrays.asList("pinki","pari","yes").stream().filter(e-> e.length()>3).forEach(System.out::println);
        Optional<Integer> b = Arrays.asList(2,4,5,6,7,8,9).stream().filter(e-> (e > 3)).filter(e->e%2==0).findFirst();
        System.out.println(b.get());
    }
}
