package com.example.springDemo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringDemoApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(SpringDemoApplication.class, args);
		Student student= applicationContext.getBean(Student.class);
		student.displayCourses();
		student.setScore(70);
		student.setAge(23);
		student.setName("Pinki");
		student.display();


		User user= applicationContext.getBean(User.class);
		user.typeOfUser();

	}

}
