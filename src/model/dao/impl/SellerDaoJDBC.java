package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import db.Conexao;
import db.DBException;
import model.Department;
import model.Seller;
import model.dao.SellerDAO;

public class SellerDaoJDBC implements SellerDAO {
	
	private Connection connection = null;
	
	public SellerDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		Seller seller = new Seller();
		Department department = new Department();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement("SELECT * FROM seller s INNER JOIN department d ON s.departmentid = d.id WHERE s.id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				seller.setId(rs.getInt("s.id"));
				seller.setName(rs.getString("s.name"));
				seller.setEmail(rs.getString("s.email"));
				seller.setBirthDate(LocalDateTime.parse(rs.getString("birthdate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				seller.setBaseSalary(rs.getDouble("s.basesalary"));
				department.setId(rs.getInt("d.id"));
				department.setName(rs.getString("d.name"));
				seller.setDepartment(department);
				
			}
			return seller;
		}catch(SQLException error) {
			throw new DBException(error.getMessage());
		}finally{
			Conexao.closeResultSet(rs);
			Conexao.closeStatement(ps);
			Conexao.closeConnection();
		}
	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
