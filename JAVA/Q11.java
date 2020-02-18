 abstract class Bank{
     float rateofinterest;
     String branchname;
    public  abstract  void  getdetails();
}
class SBI extends Bank{

    public void getdetails()
    {   rateofinterest=20.0f;
        branchname="palam";
        System.out.println("Rate of interest of SBI bank is :" +rateofinterest);
        System.out.println("Branch Name of SBI bank is :"+ branchname);
    }

}
class BOI extends Bank{

    public void getdetails()
    {   rateofinterest = 30.23f;
        branchname="sadar";
        System.out.println("Rate of interest of BOI bank is :" +rateofinterest);
        System.out.println("Branch Name of BOI bank is :"+ branchname);
    }
    }


class ICICI extends Bank{

    public void getdetails()
    {   rateofinterest=50.9f;
        branchname="noida";
        System.out.println("Rate of interest of ICICI bank is :" +rateofinterest);
        System.out.println("Branch Name of ICICI bank is :"+ branchname);
    }
    }


public class Q11 {
    public static void main(String[] args) {

       Bank b= new SBI();
          b.getdetails();
        System.out.println("\n");
          b= new BOI();
          b.getdetails();
        System.out.println("\n");
          b= new ICICI();
          b.getdetails();
    }
}
