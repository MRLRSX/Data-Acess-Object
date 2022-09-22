package model.dao;

import java.util.List;

import model.Seller;

public interface SellerDAO {
	public void insert(Seller seller);
	
	public Seller findById(Integer id);
	
	public void update(Seller seller);
	
	public void deleteById(Integer id);
	
	public List<Seller> findAll();
}
