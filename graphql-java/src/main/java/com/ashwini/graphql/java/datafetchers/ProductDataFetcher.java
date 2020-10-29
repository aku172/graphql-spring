package com.ashwini.graphql.java.datafetchers;

import java.util.List;

import com.ashwini.graphql.java.dao.ProductDao;
import com.ashwini.graphql.java.model.Product;
import com.ashwini.graphql.java.util.HibernateUtil;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class ProductDataFetcher implements DataFetcher<List<Product>>{
	
	public static List<Product> fetchAllProducts() {
		ProductDao productDao = new ProductDao(HibernateUtil.openSession());
		return productDao.findAll();
	}

	@Override
	public List<Product> get(DataFetchingEnvironment environment) throws Exception {
		return fetchAllProducts();
	}

}
