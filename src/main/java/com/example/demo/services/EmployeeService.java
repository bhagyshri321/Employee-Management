package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Employee;
import com.example.demo.repositories.EmployeeRepo;


@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepo empRepo;
	
	public List<Employee> getAllEmployees() {
		return empRepo.findAll();
	}
	
	  public Employee saveEmployee(Employee emp) {
	        return empRepo.save(emp);
	    }

	    public void deleteEmployee(int id) {
	        empRepo.deleteById(id);
	    }

	    public Employee updateEmployee(Employee emp) {
	    	System.out.println("updated");
	        return empRepo.save(emp);
	    }
	

}
