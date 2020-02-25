//Q4 of assignment


interface ConstRef
{
    public  Employee2 getemp(String name, String city, int age);
}


public class Employee2
{
    int age;
    String name;
    String city;
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getCity() { return city; }

    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setCity(String city) { this.city = city; }

    public Employee2(String name, String city, int age) {
        this.name = name;
        this.city = city;
        this.age = age;
    }

//    @Override
    public String toString() {
        return "Employee2{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public static void main(String[] args) {
        ConstRef constRef= Employee2::new;
        Employee2 emp= constRef.getemp("pinki","delhi",23);
        System.out.println(emp);
    }
}


