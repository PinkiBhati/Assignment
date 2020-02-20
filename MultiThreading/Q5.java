class Numbers
{
    public void even() throws InterruptedException {
        System.out.println("Entered into even function ...");
        synchronized (this) {
            for (int i = 1; i < 5; i++) {
                System.out.println(2 * i);
                wait(200);
            }

        }
            System.out.println("resumed..");

    }

    public void odd() throws InterruptedException {
        Thread.sleep(30);
                synchronized (this){
                    System.out.println("Entered into odd function...");
            for (int i=1;i<5;i++)
            {
                System.out.println(2*i-1);
            }
                    System.out.println("completed");
                    notify();
                }

    }
}


public class Q5 {
    public static void main(String[] args) throws InterruptedException {

        Numbers numbers = new Numbers();
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    numbers.even();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    numbers.odd();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
