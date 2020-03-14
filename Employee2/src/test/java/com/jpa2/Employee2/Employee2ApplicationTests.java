package com.jpa2.Employee2;

import com.jpa2.Employee2.Entities.*;
import com.jpa2.Employee2.Repos.Employee1Repository;
import com.jpa2.Employee2.Repos.EmployeeRepository;
import com.jpa2.Employee2.Repos.PaymentRepository;
import com.jpa2.Employee2.Repos.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
class Employee2ApplicationTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private Employee1Repository employee1Repository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCreateEmployee(){

		employeeRepository.save(new RegularEmployee(1,"Pinki","Bhati",1000,23,"Hibernate"));
		employeeRepository.save(new RegularEmployee(2,"Pari","Jain",1200,24,"Spring"));
		employeeRepository.save(new TraineeEmployee(3,"Himanshu","Bhansali",1300,25,70));
		employeeRepository.save(new TraineeEmployee(4,"Azeem","faisal",2000,46,80));
		employeeRepository.save(new TraineeEmployee(5,"Shivam","Singh",1500,30,90));

	}

	@Test
	public  void  testCreatePayment(){

		paymentRepository.save(new CreditCard(1,2000,1345));
		paymentRepository.save(new CreditCard(2,5000,3344));
		paymentRepository.save(new Cheque(1,3000,90009));
		paymentRepository.save(new Cheque(2,4000,32929));
	}


	@Test
	public void testCreatePerson(){

		personRepository.save(new Person(1,"Preeti"));
		personRepository.save(new Coder(2,"Sheena","Hibernate"));
		personRepository.save(new Tester(3,"Shreya",1260));

	}

	@Test
	public void testCreateEmployee1(){


		employee1Repository.save(new Employee1(1,"Heena","Sharma",23,
								new Salary(1200,230,50,340)));


	}


	//native query 1
	@Test
	public void testfindByLastName(){
		List<Object[]> employeeList= employeeRepository.findByLastName();
		for(Object[] objects:employeeList)
		{
			System.out.println(objects[0]);
			System.out.println(objects[1]);
			System.out.println(objects[2]);

		}
	}


	//Native query2
	@Test
	public void testDeleteEmployeeByAge(){
		employeeRepository.deleteEmployee(45);
	}



	//JPQL Q1
	@Test
	public void testFindFirstNameAndLastNameGreaterThanAverageSalary() {
		List<Object[]> emp = employeeRepository.findFirstNameAndLastNameGreaterThanAverageSalary(employeeRepository
				            .findAvgSalary(),Sort.by(Sort.Order.asc("age"),(Sort.Order.desc("salary"))));
		for (Object[] objects : emp) {
			System.out.println("First Name: " + objects[0]);
			System.out.println("Last Name: " + objects[1]);
		}
	}




	//JPQL Q2
	@Test
	public void testUpdateEmployeeWithSalaryGreaterThanAverageSalary(){
		System.out.println(">>>>>>>>>>>>>>>>Average Salary>>>>>>>>>>>>: "+ employeeRepository.findAvgSalary());

		employeeRepository.updateSalary(employeeRepository.findAvgSalary());
		employeeRepository.findAllEmployees().forEach(e-> System.out.println(e));
	}

	//JPQL Q3
	@Test
	public void testDeleteMinSalary(){
		System.out.println(">>>>>>>>>>>>>>>>Minimum Salary>>>>>>>>>>>>>>: "+ employeeRepository.findMinSalary());

		employeeRepository.deleteMinSalaryEmployees(employeeRepository.findMinSalary());
		employeeRepository.findAllEmployees().forEach(e-> System.out.println(e));
	}
}
