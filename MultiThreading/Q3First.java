class Institute
{
    public void classRoom(String facultyname)
    {
        synchronized (this)
        {
            for (int i=1;i<5;i++)
            {
                System.out.println(i + " class taken by "+ facultyname);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class NewThread extends Thread
{
    Institute institute;
    String faculty;

    @Override
    public void run() {
        institute.classRoom(faculty);
    }
    NewThread(Institute institute,String faculty)
    {
        this.institute=institute;
        this.faculty=faculty;
    }


}

public class Q3First{
    public static void main(String[] args) {
        Institute institute1=new Institute();
        Institute institute2=new Institute();
        NewThread nt1=new NewThread(institute1,"Pinki");
        NewThread nt2=new NewThread(institute2,"Chetna");
        nt1.start();
        nt2.start();

    }
}
