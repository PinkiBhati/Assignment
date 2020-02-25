interface FuncInter
{
    boolean number(int x, int y);
  }
interface Incremented
{
    int incremented(int x);
}

interface ConcatString
{
    String concatinate( String s, String s2);
}

interface UpperString
{
  String upper(String s);
}

public class Q1 {
    public static void main(String[] args) {
        FuncInter obj= (int x,int y)-> {
            if (x > y)
                return true;
            else
                return false;


        };

        Incremented obj2=x->  x=x+1;

        ConcatString obj3=( String s, String s2)->{
           return s.concat(s2);

        };

        UpperString obj4= s->s.toUpperCase();

        System.out.println(obj.number(5,6));
        System.out.println(obj2.incremented(5));
        System.out.println(obj3.concatinate("pinki","pari"));
        System.out.println(obj4.upper("pinki"));

    }


}
