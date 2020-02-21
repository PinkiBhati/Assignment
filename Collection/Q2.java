import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Q2 {

    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        String name= sc.nextLine();

        String[] str= name.split("");

        Set<String> uniqueelements= new LinkedHashSet<String>();

        for (String s1: str)
        {
            uniqueelements.add(s1);
        }

        System.out.println("Total number of unique character are :" +uniqueelements.size()+" And the unique characters are :"+uniqueelements);



    }
}
