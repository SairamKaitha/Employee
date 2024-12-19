package com.tt.employee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tt.employee.model.Employee;
import com.tt.employee.repository.EmployeeRepository;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeRepository repo;
	
	@PostMapping("/createEmp")
	//ResponseEntity<?> --> ? takes any type 
	public ResponseEntity<?> createEmployee(@RequestBody Employee emp) {
    if(!isValid(emp)) {
    	return new ResponseEntity<String>("Employee data is missing", HttpStatus.BAD_REQUEST);
    }
		Employee employee = repo.save(emp);
		return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
	}
	
	private boolean isValid(Employee emp) {
		return emp.getName().length()>0 && emp.getAddress().length()>0;
	}

	@GetMapping("/fetchAllEmp")
	public ResponseEntity<?> getAllEmp(){
		List<Employee> list = repo.findAll();
		return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
	}
	
	@PutMapping("/updateEmp/{id}")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee emp, @PathVariable int id) {
		Optional<Employee> data = repo.findById(id);
		if (data.isPresent()) {
			// passing the Optional employee to Employee
			Employee employee = data.get();
			employee.setName(emp.getName());
			employee.setAddress(emp.getAddress());
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		}
		else
		return new ResponseEntity<String>("Employee with  " + id + "  not found", HttpStatus.NOT_FOUND);

	}

      @DeleteMapping("/deleteEmp/{id}")
  	public ResponseEntity<?> deleteEmployee(@PathVariable int id) {
  		Optional<Employee> employee = repo.findById(id);
  		if(employee.isEmpty()) {
  			return new ResponseEntity<String>("Employee not found with id  "+id, HttpStatus.NOT_FOUND);
  		}
  		else
  			repo.deleteById(id);
  			return new ResponseEntity<String>("Employee deleted sucessfully with id  "+id, HttpStatus.NOT_FOUND);
	}
}
