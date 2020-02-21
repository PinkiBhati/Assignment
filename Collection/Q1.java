import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Q1 {

    public static void main(String[] args) {
            float sum=0.0f;
        List<Float>  arraylist=new ArrayList<Float>();
        arraylist.add(4.5f);
        arraylist.add(5.5f);
        arraylist.add(6.5f);
        arraylist.add(7.5f);
        arraylist.add(8.5f);


        Iterator<Float> iterator= arraylist.iterator();
        System.out.println("sum of elements in list is:");
        while (iterator.hasNext())
        {
            sum+=iterator.next();
        }
        System.out.println(sum);
    }
}
