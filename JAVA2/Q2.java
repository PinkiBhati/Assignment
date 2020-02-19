import java.util.Arrays;

public class Q2 {

    public static void swap(int i,int j,char[] arr)
    {
        char temp= arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }
    public static void main(String[] args) {

        String str="hello world";
        System.out.println("Original string is : "+ str);
        char arr[] =str.toCharArray();

        for(int i=0;i<arr.length;i++)
        {
            for(int j=i+1;j<arr.length;j++)
            {
                if(Character.toLowerCase(arr[j])< Character.toLowerCase(arr[i]))
                {
                    swap(i,j,arr);
                }
            }
        }

        System.out.println("sorted string :"+ String.valueOf(arr));
    }
}
