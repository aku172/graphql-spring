package com.ashwini.graphql.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.graph.Graph;

import com.ashwini.graphql.java.dao.ProductDao;
import com.ashwini.graphql.java.datafetchers.ProductDataFetcher;
import com.ashwini.graphql.java.model.Product;
import com.ashwini.graphql.java.util.HibernateUtil;
import com.github.javafaker.Faker;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

public class ProductMain {

	static ProductDao productDao;
	static Faker faker = new Faker();

	public static void main(String[] args) {
		//Create a few dummy products
		createProducts();

		GraphQLSchema graphSchema = buildGraphQlSchema();
		
		GraphQL build = GraphQL.newGraphQL(graphSchema).build();
		ExecutionResult executionResult = build.execute("{findAll{name}}");
		System.out.println(executionResult.getData().toString());
		
	}

	/**
	 * Build graphQL schema
	 * 
	 * @return
	 */
	private static GraphQLSchema buildGraphQlSchema() {
		SchemaParser schemaParser = new SchemaParser();
		SchemaGenerator schemaGenerator = new SchemaGenerator();

		File schemaFile = loadSchemaFile("product.graphqls");

		TypeDefinitionRegistry registry = schemaParser.parse(schemaFile);

		RuntimeWiring runtimeWiring = buildRuntimeWiring();

		return schemaGenerator.makeExecutableSchema(registry, runtimeWiring);
	}

	private static RuntimeWiring buildRuntimeWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type("Query", typeWiring -> typeWiring.dataFetcher("findAll", new ProductDataFetcher())).build();
	}

	private static File loadSchemaFile(String fileName) {
		return new File("src/main/resources/" + fileName);
	}

	private static void createProducts() {

		List<Product> productList = new ArrayList<Product>();
		for (int i = 0; i < 10; i++) {
			productList.add(new Product(i + 1, faker.name().name(), faker.address().fullAddress()));
		}
		Session session = HibernateUtil.openSession();
		productDao = new ProductDao(session);

		productDao.saveAll(productList);
		HibernateUtil.closeSession(session);
	}

}
