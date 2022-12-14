package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		PreparedStatement st = null;

		try {
			st = connection.prepareStatement(
					"INSERT INTO seller (name, email, birthdate, basesalary, departmentid) VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, conversorTime(seller.getBirthDate()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());

		} catch (SQLException error) {
			throw new DBException(error.getMessage());
		} finally {
			Conexao.closeStatement(st);
		}

	}

	@Override
	public Seller findById(Integer id) {
		Seller seller = new Seller();
		Department department = new Department();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(
					"SELECT * FROM seller s INNER JOIN department d ON s.departmentid = d.id WHERE s.id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				seller.setId(rs.getInt("s.id"));
				seller.setName(rs.getString("s.name"));
				seller.setEmail(rs.getString("s.email"));
				seller.setBirthDate(LocalDateTime.parse(rs.getString("birthdate"),
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				seller.setBaseSalary(rs.getDouble("s.basesalary"));
				department.setId(rs.getInt("d.id"));
				department.setName(rs.getString("d.name"));
				seller.setDepartment(department);

			}
			return seller;
		} catch (SQLException error) {
			throw new DBException(error.getMessage());
		} finally {
			Conexao.closeResultSet(rs);
			Conexao.closeStatement(ps);
		}
	}

	@Override
	public void update(Seller seller) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(
					"UPDATE seller SET name = ?, email = ?, birthdate = ?, departmentid = ? WHERE id = ? ");
			ps.setString(1, seller.getName());
			ps.setString(2, seller.getEmail());
			ps.setDate(3, conversorTime(seller.getBirthDate()));
			ps.setInt(4, seller.getDepartment().getId());
			ps.setInt(5, seller.getId());
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
			ps = connection.prepareStatement("DELETE FROM seller WHERE id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException error) {
			throw new DBException(error.getMessage());
		} finally {
			Conexao.closeStatement(ps);
		}

	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		List<Seller> listaSeller = new ArrayList<>();

		try {
			st = connection.prepareStatement(
					"SELECT * FROM seller INNER JOIN department ON seller.DepartmentId = department.Id");
			rs = st.executeQuery();

			while (rs.next()) {
				Seller seller = new Seller();
				Department department = new Department();
				seller.setId(rs.getInt("id"));
				seller.setName(rs.getString("name"));
				seller.setEmail(rs.getString("email"));
				seller.setBirthDate(LocalDateTime.parse(rs.getString("birthdate"),
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				seller.setBaseSalary(rs.getDouble("basesalary"));
				department.setId(rs.getInt("department.id"));
				department.setName(rs.getString("department.name"));
				seller.setDepartment(department);
				listaSeller.add(seller);
			}
			return listaSeller;
		} catch (SQLException error) {
			throw new DBException(error.getMessage());
		} finally {
			Conexao.closeResultSet(rs);
			Conexao.closeStatement(st);
		}
	}

	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name");

			st.setInt(1, department.getId());

			rs = st.executeQuery();

			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {
				Department dep = map.get(rs.getInt("departmentid"));
				if (dep == null) {
					dep = new Department(rs.getInt("departmentid"), rs.getString("depname"));
					map.put(rs.getInt("departmentid"), dep);
				}
				Seller seller = new Seller();
				seller.setId(rs.getInt("seller.id"));
				seller.setName(rs.getString("seller.name"));
				seller.setEmail(rs.getString("seller.email"));
				seller.setBirthDate(LocalDateTime.parse(rs.getString("seller.birthdate"),
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				seller.setBaseSalary(rs.getDouble("seller.basesalary"));
				seller.setDepartment(dep);
				list.add(seller);
			}
			return list;

		} catch (SQLException error) {
			throw new DBException(error.getMessage());
		} finally {
			Conexao.closeStatement(st);
			Conexao.closeResultSet(rs);
		}
	}

	/** STACK-OVER-FLOW SAVE THE DAY */
	private static java.sql.Date conversorTime(LocalDateTime dt) {
		LocalDate locald = LocalDate.of(dt.getYear(), dt.getMonth(), dt.getDayOfMonth());
		Date date = Date.valueOf(locald);
		return new java.sql.Date(date.getTime());
	}

}
