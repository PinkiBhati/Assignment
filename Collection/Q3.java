import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Q3 {

    public static void countoccurence(String str)
    {
        HashMap<Character, Integer> hasmap= new HashMap<>();
        char[] chararray= str.toCharArray();

        for (char c: chararray)
        {
            if (hasmap.containsKey(c))
            {
                hasmap.put(c,hasmap.get(c)+1);
            }

            else {
                hasmap.put(c,1);
            }
        }

        for (Map.Entry entry: hasmap.entrySet())
        {
            System.out.println(entry.getKey()+" "+ entry.getValue());
        }

    }
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        String str= sc.nextLine();

        countoccurence(str);

    }
}
