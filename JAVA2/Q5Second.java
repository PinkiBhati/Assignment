public class Q5Second {
    int one, two,one1,two1;
    Q5Second()
    {
        one =5;
        two =6;
    }
    Q5Second(Q5Second sc )
    {  one1 =sc.one;
         two1 =sc.two;
    }
    public static void main(String[] args) {
        Q5Second obj = new Q5Second();
        Q5Second obj2 =new Q5Second(obj);
        System.out.println(" Values of one and two for obj are : "+(obj.one) +" and "+(obj.two)+"  respectively .");
        System.out.println(" Values of one and two for obj1 are : "+(obj2.one1) +"and  "+(obj2.two1)+" respectively .");
    }
}
