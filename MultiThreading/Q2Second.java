public class Q2Second implements Runnable {
    public void run()
    {
        System.out.println("Thread is running.....");
    }
    public static void main(String[] args) {
        Q2Second obj=new Q2Second();
        Thread t1= new Thread(obj);
        t1.start();

    }
}
