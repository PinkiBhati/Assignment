interface Myinterface1
{
    public default void display()
    {
        System.out.println("hello i am interface 1");
    }
}
interface Myinterface2
{
    public default void display()
    {
        System.out.println("hello i am interface 2");
    }
}

public class Q8 implements Myinterface1, Myinterface2 {

    public void display()
    {
        Myinterface1.super.display();
        Myinterface2.super.display();
    }
    public static void main(String[] args) {
        Q8 obj=new Q8();
        obj.display();
    }
}
