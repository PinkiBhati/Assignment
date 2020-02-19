class CheckClone implements Cloneable{
    int i;
    int j;

    @Override
    public String toString() {
        return "CheckClone{" + "i=" + i + ", j=" + j + '}';
    }
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}


public class Q5First {

    public static void main(String[] args) throws CloneNotSupportedException{
        CheckClone clone= new CheckClone();
        clone.i=5;
        clone.j=7;

        CheckClone clone1= (CheckClone) clone.clone();

         System.out.println(clone);
        System.out.println(clone1);

    }
}
