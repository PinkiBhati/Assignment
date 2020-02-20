public class Q3Second {

    private int i=30;

    public synchronized void decrement()
    {
        i--;
    }
    public static void main(String[] args) throws InterruptedException {
        Q3Second obj=new Q3Second();
        obj.performdecrement();
    }

    public void performdecrement() throws InterruptedException {
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int j=0;j<10;j++)
                {
                    decrement();
                }
            }
        });

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int j=0;j<10;j++)
                {
                    decrement();
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(i);
    }
}
