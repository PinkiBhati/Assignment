
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Employee
{
    double age;
    double salary;
    String name;

    public Employee(double age, double salary, String name) {
        this.age = age;
        this.salary = salary;
        this.name = name;
    }

    public double getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class SortEmployee implements Comparator<Employee>
{
    @Override
    public int compare(Employee employee, Employee employee1) {
      if(employee.getSalary()< employee1.getSalary())
            return 1;
            else if (employee.getSalary()> employee1.getSalary())
                return -1;
           else
                return 0;
    }


}


public class Q5 {
    public static void main(String[] args) {
        List<Employee> emp= new ArrayList<Employee>();

        Employee e1= new Employee(21,2300,"pinki");
        Employee e2=new Employee(23,2009,"pari");
        Employee e3=new Employee(30,1000,"john");
        emp.add(e1);
        emp.add(e2);
        emp.add(e3);


        Collections.sort(emp,new SortEmployee());

        for(Employee emp1:emp)
        {
            System.out.println(emp1.getName()+" "+ emp1.getSalary());
        }
    }

}
