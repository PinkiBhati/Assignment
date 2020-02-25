interface Myinterface5
{
    default void show()
    {
        System.out.println("Default interface of interface5");
    }
}
public class Q7 implements Myinterface5{
    public void show()
    {
        Myinterface5.super.show();
    }
    public static void main(String[] args) {
        Q7 obj=new Q7();
        obj.show();
    }
}
