public class Q6 {

    public static void singleNumber(int[] A)
    {
        int ans = A[0];
        for(int i=1;i<A.length;i++)
        {
            ans = ans^A[i];
        }
        System.out.println(ans);


    }
    public static void main(String[] args) {

      int[] a = {2,2,3,3,4,4,5};
      singleNumber(a);
    }
}
