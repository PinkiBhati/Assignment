import java.util.Scanner;

public class Q4 {

    static void charpercent(String str)
    {   int total= str.length();

        int i, upper=0,lower=0,digit=0,others=0;

        for(i=0;i<str.length();i++)
        {
            char ch= str.charAt(i);

            if(Character.isUpperCase(ch))
            {
                upper++;
            }

            else if(Character.isLowerCase(ch))
            {
                lower++;
            }

            else if(Character.isDigit(ch))
            {
                digit++;
            }
            else
            {
                others++;
            }
        }

        double upperpercentage = (upper*100.0)/total;
        double lowerpercentage = (lower*100.0)/total;
        double digitpercentage = (digit*100.0)/total;
        double otherpercentage = (others*100.0)/total;

        System.out.println("Input string is " + str +" : ");

        System.out.println("No. of upper case letters are:\t"+upper +"\t And its percentage is\tH :"+upperpercentage);
        System.out.println("No. of lower case letters are:\t"+lower +"\t And its percentage is\tH :"+lowerpercentage);
        System.out.println("No. of digits are:\t"+digit +"\t And its percentage is\tH :"+digitpercentage);
        System.out.println("No. of other characters are:\t"+others +"\t And its percentage is\tH :"+otherpercentage);


    }
    public static void main(String[] args)
        {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the string: \t");
            String s = sc.nextLine();
            charpercent(s);


        }

}
