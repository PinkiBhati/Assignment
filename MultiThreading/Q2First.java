public class Q2First extends Thread{
    public void run()
    {
        System.out.println("This is a Thread class \n");
    }
    public static void main(String[] args) {
        Q2First obj=new Q2First();
        obj.start();
    }
}
