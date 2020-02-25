interface Myinterface3
{
    default void display(String str)
    {
        System.out.println(str);
    }

    static void square(int x)
    {
        System.out.println(x*x);
    }
}

public class Q6 implements Myinterface3{

    public static void main(String[] args) {
        Q6 obj= new Q6();
        Myinterface3.square(3);
        obj.display("abc");


    }
}
