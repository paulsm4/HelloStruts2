package com.example.rest;

import java.util.Map;

import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ModelDriven;

public class EmployeeController implements ModelDriven<Object> {

	private static final long serialVersionUID = 1L;
	private String id;
	private Object model;
	private EmployeeRepository employeeRepository = new EmployeeRepository();
	
	private static Map<String,Employee> map;
	{
		map = employeeRepository.findAllEmployee();
	}
	
	// @Override  // Compile error with "@Override" for "Object getId()"
	public Object getId() {
		return model;
	}
	
	public HttpHeaders index() {
		model = map;
		return new DefaultHttpHeaders("index").disableCaching();
	}
	
	public String add(){
		Integer empId = Integer.parseInt(id);
		Employee emp = new Employee(empId,"Ramesh", "PQR");
		model = emp;
		return "SUCCESS";
	}
	
	public void setId(String id) {
		model = employeeRepository.getEmployeeById(id);
		this.id = id;
	}

	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
