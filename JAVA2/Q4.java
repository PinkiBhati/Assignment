class Singleton {
        static Singleton single ; // Everytime this object will be called.
        public String str;
        private  Singleton() {
                str=" This is singleton class";
                /* hinder other classes to create an object of this class. */
           }

        public static Singleton getInstance()
        {
            if(null==single)
            {
                single = new Singleton();
            }

         return single;
        }
                }

public class Q4 {

    public static void main(String[] args) {

        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        s1.str= (s1.str).toUpperCase();
        System.out.println("String of s1 is : "+s1.str);
        System.out.println("String of s2 is : "+s2.str);

       // Singleton s2= new Singleton();/* Can't be created because Singleton() has private access in Singleton.*/

    }
}
