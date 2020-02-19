public class Q6 {
    public static void main(String[] args) {
        try{
            int arr[]=new int[5];
            arr[5]=2/0;

        }
        catch(ArithmeticException e)
        {
            System.out.println("Arithmetic Exception occurs \n");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("ArrayIndexOutOfBounds Exception occurs \n ");
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }

        finally {
            System.out.println("This is finally block \n");
        }
    }
}
