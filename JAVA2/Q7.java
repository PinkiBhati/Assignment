import java.util.Scanner;

public class Q7 {

    static void ConvertSec(int seconds)
    {
        int days= seconds/(24 * 3600);
        seconds = seconds % (24 * 3600);

        int hours = seconds/3600;

        seconds = seconds% 3600;

        int minutes = seconds / 60;

        seconds %= 60;
        int Seconds = seconds;

        System.out.println(days + " days "+ hours +" hours "+ minutes + " minutes " + seconds + " seconds");

    }
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter Seconds: \t");
        int sec= sc.nextInt();
        ConvertSec(sec);
    }
}
