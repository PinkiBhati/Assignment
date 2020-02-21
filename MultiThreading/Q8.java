
import java.util.concurrent.*;

class Resource
{
    static int counter = 0;
}

class MyThread extends Thread
{
    Semaphore sem;
    String name;
    public MyThread(Semaphore sem, String name)
    {
        super(name);
        this.sem = sem;
        this.name = name;
    }

    @Override
    public void run() {

        if(this.getName().equals("thread1"))
        {
            System.out.println("Starting.. " + name);
            try
            {
                System.out.println(name + " is waiting");
                sem.acquire();
                System.out.println(name + " gets permit.");

                for(int i=0; i < 2; i++)
                {
                    Resource.counter++;
                    System.out.println(name + "=" + Resource.counter);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }

            System.out.println(name + " releases the permit.");
            sem.release();
        }

        else
        {
            System.out.println("Starting.. " + name);
            try
            {
                System.out.println(name + " is waiting ");
                sem.acquire();

                System.out.println(name + " gets permit.");
                for(int i=0; i < 2; i++)
                {
                    Resource.counter--;
                    System.out.println(name + "=" + Resource.counter);

                    Thread.sleep(1000);
                }
            } catch (InterruptedException exc) {
                System.out.println(exc);
            }
            System.out.println(name + " releases the permit.");
            sem.release();
        }
    }
}

public class Q8
{
    public static void main(String args[]) throws InterruptedException
    {
        Semaphore sem = new Semaphore(1);

        MyThread t1 = new MyThread(sem, "thread1");
        MyThread t2 = new MyThread(sem, "thread2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("count: " + Resource.counter);
    }
}
