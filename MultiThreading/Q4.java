import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
class EvenThread implements Runnable{
    public void run()
    {
        System.out.println("printing even numbers...");
        for(int i=0;i<10;i++)
        {
            if(i%2==0)
            {
                System.out.println(i);
            }
        }
    }
}
class OddThread implements Runnable{
    @Override
    public void run() {

        System.out.println("printing odd numbers...");
        for(int j=0;j<10;j++)
        {
            if(j%2!=0)
            {
                System.out.println(j);
            }
        }
    }
}
public class Q4
{
    public static void main(String[] args)  {
        ExecutorService execserv;
        execserv = Executors.newFixedThreadPool(2);
        Runnable r1=new OddThread();
        Runnable r2=new EvenThread();
        execserv.execute(r1);
        execserv.execute(r2);
        execserv.shutdown();
    }
}
