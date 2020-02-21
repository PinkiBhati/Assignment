
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Students
{
    double age;
    double score;
    String name;

    public Students(double age, double score, String name) {
        this.age = age;
        this.score = score;
        this.name = name;
    }

    public double getAge() {
        return age;
    }

    public double getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class SortStudents implements Comparator<Students>
{
    @Override
    public int compare(Students student, Students student1) {
        if(student.getScore()> student1.getScore())
            return 1;
        else if (student.getScore()<student1.getScore())
            return -1;
        else
            return student.getName().compareTo(student1.getName());
    }
}


public class Q6 {
    public static void main(String[] args) {
        List<Students> stud= new ArrayList<Students>();

        Students s1= new Students(21,2300,"pinki");
        Students s2=new Students(23,2009,"pari");
        Students s3=new Students(30,1000,"john");
        Students s4=new Students(24,1000,"shivam");
        Students s5=new Students(25,1000,"abhinav");
        stud.add(s1);
        stud.add(s2);
        stud.add(s3);
        stud.add(s4);
        stud.add(s5);


        Collections.sort(stud,new SortStudents());

        for(Students stud1:stud)
        {
            System.out.println(stud1.getName()+" "+ stud1.getScore());
        }
    }

}
