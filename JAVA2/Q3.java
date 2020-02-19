class NewFile {
    public void NewFun() {
        System.out.println("NewFile class ");
    }
}


    public class Q3 {

    public static void main(String[] args) {
        try{
            Class.forName("New");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }

       NewFile nf=new NewFile();
       nf.NewFun();
    }
}
