public class Q6 {
    private int counter=0;

    public static void main(String[] args) throws InterruptedException {
        Q6 obj= new Q6();
        obj.increment();

    }

    public void increment() throws InterruptedException {
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<500;i++)
                {
                    counter++;

                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<500;i++)
                {
                    counter++;

                }

            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(counter);
    }
}
