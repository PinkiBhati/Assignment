package com.jpa.Employee;

import com.jpa.Employee.Entities.Employee;
import com.jpa.Employee.Repos.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeApplicationTests {

	@Autowired
	EmployeeRepository  employeeRepository;

	@Test
	void contextLoads() {
	}

//que 3
	@Test
	public void testCreate(){
		Employee employee= new Employee();
		employee.setAge(23);
		employee.setId(1);
		employee.setName("Pinki");
		employee.setLocation("Delhi");


		Employee employee1= new Employee();
		employee1.setAge(22);
		employee1.setId(2);
		employee1.setName("Priya");
		employee1.setLocation("Bihar");

		Employee employee2= new Employee();
		employee2.setAge(24);
		employee2.setId(3);
		employee2.setName("Azeem");
		employee2.setLocation("Noida");

		Employee employee3= new Employee();
		employee3.setAge(28);
		employee3.setId(4);
		employee3.setName("Himanshu");
		employee3.setLocation("Delhi");

		Employee employee4= new Employee();
		employee4.setAge(26);
		employee4.setId(5);
		employee4.setName("Shivam");
		employee4.setLocation("Jammu");

		Employee employee5= new Employee();
		employee5.setAge(24);
		employee5.setId(6);
		employee5.setName("Ankit");
		employee5.setLocation("Sadar");

		employeeRepository.save(employee);
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
		employeeRepository.save(employee3);
		employeeRepository.save(employee4);
		employeeRepository.save(employee5);

	}
//que 4
	@Test
	public void testUpdate(){
		Optional<Employee> optionalEmployee= employeeRepository.findById(1);
		Employee employee= optionalEmployee.get();
		employee.setName("Pari");

		employeeRepository.save(employee);
	}
//Que 5 A
	@Test
	public void testDelete(){
		if(employeeRepository.existsById(1))
		{
			System.out.println("Deleting employee....");
			employeeRepository.deleteById(1);
		}
	}


//Que 5 B
	@Test
	public void testRead(){
		Optional<Employee> optionalEmployee=employeeRepository.findById(2);
		Employee employee= optionalEmployee.get();
		assertNotNull(employee);
		assertEquals("Priya",employee.getName());
		System.out.println(employee.getName());

	}

//Que 6
	@Test
	public void testCount(){
		System.out.println("Total no. of employees are "+ employeeRepository.count());
	}

	//Que 7
	@Test
	public void testFindAllPagingAndSorting(){
		Pageable pageable= PageRequest.of(1,2, Sort.by(Sort.Order.asc("age")));
		Page<Employee> results=employeeRepository.findAll(pageable);
		results.forEach(e-> System.out.println(e.getAge()+" "+ e.getName()));
	}

	//Que 8
	@Test
	public void testFindByName(){
		List<Employee> employees= employeeRepository.findByName("Pinki");
		employees.forEach(e-> System.out.println(e.getName()));
	}

//Que 9
	@Test
	public void testFindByNameStartingWith(){
		List<Employee> employees= employeeRepository.findByNameStartingWith("A");
		employees.forEach(e-> System.out.println(e.getName()));
	}

//Que 10
	@Test
	public void testFindByAgeBetween(){
		List<Employee> employees= employeeRepository.findByAgeBetween(28,32);
		employees.forEach(e-> System.out.println(e.getName()+" "+e.getAge()));
	}
}
