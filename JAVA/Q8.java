import java.util.Scanner;

public class Q8 {
    public static void main(String[] args) {
         String str ="Hello world";
        System.out.println("Input string is :" + str);
         str = new StringBuffer(str).reverse().toString();
        System.out.println("Reversed string is :"+str);
           str = new StringBuffer(str).delete(4,10).toString();
         System.out.println("New string after removing index 4 to 9 is :"+str);

    }
}
