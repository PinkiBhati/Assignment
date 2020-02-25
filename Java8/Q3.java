interface Add
{
    void addfunc(int x, int y);
}

interface Sub
{
    void subfunc(int x,int y);
}
interface Mul
{
    void mulfunc(int x);
}

public class Q3 {
    static void Addmethod(int a, int b)
    {
        System.out.println("Sum of two numbers is "+(a+b));
    }

    void Submethod(int a,int b)
    {
        System.out.println("Difference between two numbers is "+(a-b));
    }

    void Mulmethod(int a)
    {
        System.out.println("Multiplication of two numbers is "+(a*a));
    }
    public static void main(String[] args) {

        Add add= Q3::Addmethod;
        add.addfunc(6,6);

        Sub sub= new Q3()::Submethod;
        sub.subfunc(6,3);

        Mul mul = new Q3()::Mulmethod;
        mul.mulfunc(5);


    }
}
