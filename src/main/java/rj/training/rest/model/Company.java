package rj.training.rest.model;

import java.util.ArrayList;
import java.util.List;

public class Company {
Integer id;
@Override
public String toString() {
	return "Company [id=" + id + ", name=" + name + ", departments=" + departments + "]";
}
String name;
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
public List<Department> getDepartments() {
	return departments;
}
public void setDepartments(List<Department> departments) {
	this.departments = departments;
}
	List<Department> departments = new ArrayList<>(); 
}
