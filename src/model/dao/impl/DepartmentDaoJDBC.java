package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.Conexao;
import db.DBException;
import model.Department;
import model.dao.DepartmentDAO;

public class DepartmentDaoJDBC implements DepartmentDAO {

	private Connection connection = null;

	public DepartmentDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Department department) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("INSERT INTO department(name) values(?)");
			ps.setString(1, department.getName());
			ps.executeUpdate();
		} catch (SQLException error) {
			throw new DBException(error.getMessage());
		} finally {
			Conexao.closeStatement(ps);
		}

	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Department department = new Department();
		try {
			ps = connection.prepareStatement("SELECT * FROM department Where id = ?");
			rs = ps.executeQuery();
			while (rs.next()) {
				department.setId(rs.getInt("id"));
				department.setName(rs.getString("name"));
			}
			return department;
		} catch (SQLException error) {
			throw new DBException(error.getMessage());
		} finally {
			Conexao.closeResultSet(rs);
			Conexao.closeStatement(ps);
		}
	}

	@Override
	public void update(Department department) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("UPDATE department SET name = ? WHERE id = ?");
			ps.setString(1, department.getName());
			ps.setInt(2, department.getId());
			ps.executeUpdate();
		} catch (SQLException error) {
			throw new DBException(error.getMessage());
		} finally {
			Conexao.closeStatement(ps);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("DELETE FROM department where id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException error) {
			throw new DBException(error.getMessage());
		} finally {
			Conexao.closeStatement(ps);
		}

	}

	@Override
	public List<Department> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Department> listaDepartment = new ArrayList<>();
		try {
			ps = connection.prepareStatement("SELECT * FROM department");
			rs = ps.executeQuery();
			while (rs.next()) {
				Department department = new Department();
				department.setId(rs.getInt("id"));
				department.setName(rs.getString("name"));
				listaDepartment.add(department);
			}
			return listaDepartment;
		} catch (SQLException error) {
			throw new DBException(error.getMessage());
		} finally {
			Conexao.closeResultSet(rs);
			Conexao.closeStatement(ps);
		}
	}

}
