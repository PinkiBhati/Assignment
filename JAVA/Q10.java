import java.util.Scanner;

public class Q10
{
    public static Scanner sc = new Scanner(System.in);
    public int add(int a,int b) throws RuntimeException
    {
        return a+b;
    }
    public double add(double a,double b) throws RuntimeException
    {
        return a+b;
    }
    public float multiply(float a,float b) throws RuntimeException
    {
        return a*b;
    }
    public int multiply(int a,int b) throws RuntimeException
    {
        return a*b;
    }
    public String conc(String s1,String s2) throws RuntimeException
    {return s1.concat(s2);}
    public String conc(String s1,String s2,String s3) throws RuntimeException
    {return s1.concat(s2.concat(s3));}
    public static void main(String[] args) {
        int a;
        Q10 m = new Q10();
        System.out.println("enter choice");
        System.out.println("1 for addition of two integers");
        System.out.println("2 for addition of two doubles");
        System.out.println("3 for multiplication of two floats");
        System.out.println("4 for multiplication of two int");
        System.out.println("5 for concatenate two strings");
        System.out.println("6 for concatenate three strings");
        a = sc.nextInt();
        switch (a) {
            case 1:
                System.out.println("enter first integer value");
                int x = sc.nextInt();
                System.out.println("enter second integer value");
                int y = sc.nextInt();
                System.out.println(m.add(x, y));
                break;
            case 2:
                System.out.println("enter first double value");
                double x1 = sc.nextDouble();
                System.out.println("enter second double value");
                double y1 = sc.nextDouble();
                System.out.println(m.add(x1, y1));
                break;
            case 3:
                System.out.println("enter first float value");
                float x2 = sc.nextFloat();
                System.out.println("enter second float value");
                float y2 = sc.nextFloat();
                System.out.println(m.multiply(x2, y2));
                break;
            case 4:
                System.out.println("enter first int value");
                int x3 = sc.nextInt();
                System.out.println("enter second second value");
                int y3 = sc.nextInt();
                System.out.println(m.multiply(x3, y3));
                break;
            case 5:
                System.out.println("enter first String");
                String x4 = sc.next();
                System.out.println("enter second String");
                String y4 = sc.next();
                System.out.println(m.conc(x4, y4));
                break;
            case 6:
                System.out.println("enter first string");
                String  x5 = sc.next();
                System.out.println("enter second string");
                String  y5 = sc.next();
                System.out.println("enter third string");
                String  z5 = sc.next();
                System.out.println(m.conc(x5,y5,z5));
                break;
            default:
                System.out.println("wrong choice");

        }
        }
    }
