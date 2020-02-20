public class Q10 {
    final Object lock = new Object();
    final Object lock1 = new Object();
    Thread thread = new Thread() {
        public void run() {
            while (true) {
                synchronized (lock) {
                    System.out.println("lock acquired");
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("waiting for lock1");
                    synchronized (lock1) {
                        System.out.println("hello ");
                    }
                }
            }
        }

    };
    Thread thread2 = new Thread() {
        public void run() {
            while (true) {
                synchronized (lock1) {

                    System.out.println("lock1 acquired");

                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("waiting for lock");
                    synchronized (lock) {
                        System.out.println("hii");
                    }
                }
            }
        }
    };

    public static void main(String[] args) {
        Q10 obj = new Q10();
        obj.thread.start();
        obj.thread2.start();
    }
}
