public class Q5 {
    public static void main(String[] args) {
        int a[]={3,5,4,2,7,6,6};
        int b[]={3,6,5,7,8,9};
       int i,j;
        for (i=0;i<a.length;i++)
        {
            for (j=0;j<b.length;j++)
            {

                if(a[i]==b[j])
                {
                    b[j]=0;
                    System.out.println(a[i]);
                }
            }
        }

    }
}
