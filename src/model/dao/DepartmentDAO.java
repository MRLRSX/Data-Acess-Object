package model.dao;

import java.util.List;

import model.Department;

public interface DepartmentDAO {
  
	public void insert(Department department);
	
	public Department findById(Integer id);
	
	public void update(Department department);
	
	public void deleteById(Integer id);
	
	public List<Department> findAll();
}
