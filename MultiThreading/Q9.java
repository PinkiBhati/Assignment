import java.util.concurrent.CountDownLatch;

public class Q9 {
    public static void main(String args[]) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ClassNew obj = new ClassNew("thread1", countDownLatch);
        ClassNew obj1 = new ClassNew("thread2", countDownLatch);
        Thread t1 = new Thread(obj);
        Thread t2 = new Thread(obj1);
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        try {
            System.out.println("2 threads are running");
            countDownLatch.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ClassNew implements Runnable {
    private String name;
    private CountDownLatch countDownLatch;

    public ClassNew(String name, CountDownLatch countDownLatch) {
        this.name = name;
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            System.out.println(name + " started. ");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(name + " is running.");
        countDownLatch.countDown();
    }
}
