package rj.training.rest.model;

import java.util.ArrayList;
import java.util.List;

public class Department {
Integer id;
String name;
@Override
public String toString() {
	return "Department [id=" + id + ", name=" + name + ", employees=" + employees + "]";
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public List<Employee> getEmployees() {
	return employees;
}
public void setEmployees(List<Employee> employees) {
	this.employees = employees;
}
List<Employee> employees = new ArrayList<>();
}
