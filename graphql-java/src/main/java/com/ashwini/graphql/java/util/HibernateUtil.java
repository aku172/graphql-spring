package com.ashwini.graphql.java.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Utiity class for configuring session-factory and getting session
 * 
 * @author ashwini
 *
 */
public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	private static StandardServiceRegistry serviceRegistry;
	
	private static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
            try {
                // Create registry
            	serviceRegistry = new StandardServiceRegistryBuilder().configure().build();

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(serviceRegistry);

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                e.printStackTrace();
                if (serviceRegistry != null) {
                    StandardServiceRegistryBuilder.destroy(serviceRegistry);
                }
            }
        }
        return sessionFactory;
	}
	
	public static Session openSession() {
		return getSessionFactory().openSession();
	}
	
	public static void closeSession(Session session) {
		session.close();		
	}

}
