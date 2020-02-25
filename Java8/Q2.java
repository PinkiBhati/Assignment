
interface NumberFunc{
    int Number(int x,int y);

}

public class Q2 {
    public static void main(String[] args) {

        NumberFunc numberFunc= (int x, int y)->{
            if(x>y)
                return x;
            else
                return y;
        };

        System.out.println(numberFunc.Number(5,7));

    }


}
