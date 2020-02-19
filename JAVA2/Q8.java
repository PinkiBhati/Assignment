import java.util.Scanner;

public class Q8 {
    public static void main(String[] args) {

        System.out.println("Using while loop :: ");
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter a word : ");
        String str = keyboard.next();
        while(!str.equals("done"))
        {
            if(str.charAt(0) == str.charAt(str.length()-1))
              {
                  System.out.println("First and last character are equal in the str: " + str);
              }
        else
            {
                  System.out.println("First and last character are not equal in the str: " + str);
             }

            str = keyboard.next();
        }
        System.out.println("Now Using do-while loop :: ");

        Scanner keyboard1= new Scanner(System.in);
        System.out.println("Enter a word : ");
        String str1 = keyboard.next();
        do {
            if(str1.charAt(0) == str1.charAt(str1.length() - 1))
            {
                System.out.println("First and last character are equals for the str: " + str1);
            }
            else
            { System.out.println("First and last character are NOT equals for the str: " + str1);
            }
            str1 = keyboard.next();
        }
        while(!str1.equals("done"));
    }


}
