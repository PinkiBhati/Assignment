import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {

                String s;
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter a string:");
                s = sc.nextLine();
                System.out.println("You entered String: "+s);
                s = s.toLowerCase();
                String Words[]= s.split(" ");
                int count=0,i,j;
                for(  i=0 ;i<= Words.length;i++) {
                    count = 1;

                    for (j = i + 1; j < Words.length; j++) {
                        if (Words[i].equals(Words[j])) {
                            count++;
                            Words[j] = "0";
                        }
                    }
                    if (count > 1 && Words[i]!="0") {
                        System.out.println(Words[i] + " " + count);
                    }

                }

    }
}
