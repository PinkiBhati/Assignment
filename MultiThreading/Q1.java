import java.util.Scanner;

class MyClass extends Thread
{
    private volatile boolean counting=true;
    private  int counter=1;

    public void run()
    {
        while (counting)
        {
            System.out.println(counter);
            counter++;
            try{
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void stopCounting()
    {
        counting=false;
    }

}

public class Q1
{
    public static void main(String[] args) {
        MyClass my=new MyClass();
        my.start();
        Scanner sc= new Scanner(System.in);
        sc.nextLine();
        my.stopCounting();

    }
}