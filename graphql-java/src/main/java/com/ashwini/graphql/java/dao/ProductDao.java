package com.ashwini.graphql.java.dao;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ashwini.graphql.java.model.Product;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class ProductDao {
	
	private Session session;
	
	public void save(Product product) {
		Transaction tx = session.beginTransaction();
		try {			
			session.save(product);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			tx.rollback();
		} finally {
			tx.commit();
		}
	}
	
	public void saveAll(List<Product> productList) {
		Transaction tx = session.beginTransaction();
		try {			
			productList.stream().forEach(x -> session.save(x));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			tx.rollback();
		} finally {
			tx.commit();
		}
	}
	
	public Product findOne(long id) {
		return session.get(Product.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> findAll() {
		return session.createQuery("from "+Product.class.getName()).list();
	}
	
	

}
