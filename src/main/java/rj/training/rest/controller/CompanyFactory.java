package rj.training.rest.controller;

import java.util.ArrayList;
import java.util.Arrays;

import rj.training.rest.model.Company;
import rj.training.rest.model.CompanyResponse;
import rj.training.rest.model.Department;
import rj.training.rest.model.Employee;

public class CompanyFactory {
private final static CompanyResponse cr = new CompanyResponse();
public static CompanyResponse getResponse() {
	if (cr.getMyCompany() == null) {
		System.out.println("setting company");
	
	Employee d1e1 = new Employee();
	d1e1.setId(1);
	d1e1.setName("ravi");
	d1e1.setSalary(200000l);
	
	Employee d1e2 = new Employee();
	d1e2.setId(2);
	d1e2.setName("sonika");
	d1e2.setSalary(300000l);
	
	Department d1 = new Department();
	d1.setId(1);
	d1.setName("Engineering");
	d1.setEmployees(Arrays.asList(d1e1, d1e2));
	
	Company c1 = new Company();
	c1.setId(1);
	c1.setName("deshaw");
	c1.setDepartments(new ArrayList<>(Arrays.asList(d1)));

cr.setMyCompany(c1);
	}
return cr;
}
}
