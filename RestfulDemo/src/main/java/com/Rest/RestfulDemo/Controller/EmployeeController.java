package com.Rest.RestfulDemo.Controller;

import com.Rest.RestfulDemo.ModelClasses.Employee;
import com.Rest.RestfulDemo.DaoService.EmployeeDaoService;
import com.Rest.RestfulDemo.Exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {

   @Autowired
    private EmployeeDaoService employeeDaoService;



   @GetMapping("/Welcome")

   public String welcomeToSpring()
   {
       return "Welcome to spring boot.";
   }

    @GetMapping("/employees")

    public List<Employee> getEmployees()
    {
        return employeeDaoService.findAllEmployees();
    }

    @GetMapping("/employees/{id}")

    public Employee getParticularEmployee(@PathVariable int id)
    {
     Employee employee2= employeeDaoService.findOneEmployee(id);

        if(employee2==null)
        {
            throw  new EmployeeNotFoundException("id"+id);
        }

        EntityModel<Employee> resource = new EntityModel<Employee>(employee2);

       WebMvcLinkBuilder linkTo =
                WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).getEmployees());

        resource.add(linkTo.withRel("all-employees"));



        return employee2;
    }


    @PostMapping("/employees")

   public ResponseEntity createEmployee(@Valid @RequestBody  Employee employee)
    {
       Employee employee1=employeeDaoService.createNewEmployee(employee);

       URI location= ServletUriComponentsBuilder
                     .fromCurrentRequest()
                     .path("/{id}")
                     .buildAndExpand(employee1.getId())
                     .toUri();

       return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/employees/{id}")

    public Employee deleteEmp(@PathVariable int id)
    {
       return employeeDaoService.deleteEmployeeById(id);

    }



    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee employee) {

        Employee emp = employeeDaoService.findOneEmployee(id);
        if (emp == null) {
            throw new EmployeeNotFoundException("id: " + id);
        }


        emp.setId(employee.getId());
        emp.setAge(employee.getAge());
        emp.setName(employee.getName());
        Employee updateEmployee = employeeDaoService.updateEmp(emp);


        return updateEmployee;

    }


}
