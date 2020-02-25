import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Q5 {
    public static void printList(List<Integer> list, Consumer<Integer> consumer)
    {
        for (int i : list)
        {
            consumer.accept(i);
        }
    }


    public static void main(String[] args) {
        System.out.println();
        Consumer<Integer> consumer= i-> System.out.println(i);
        List<Integer> list= Arrays.asList(1,2,3,4,5);
        printList(list,consumer);

        Supplier<Double> supplier= ()->  Math.random();
        System.out.println(supplier.get());

        Predicate<Integer> predicate= i->(i>10);
        System.out.println(predicate.test(7));

        Function<Integer,Integer> sub = x-> x-3;
        Integer two=sub.apply(6);
        System.out.println(two);

    }
}
