package model.dao;

import db.Conexao;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
  
	public static SellerDAO createSeller() {
		return new SellerDaoJDBC(Conexao.getConnection());
	}
	
	public static DepartmentDAO createDepartmentDao() {
		return new DepartmentDaoJDBC();
	}
}
