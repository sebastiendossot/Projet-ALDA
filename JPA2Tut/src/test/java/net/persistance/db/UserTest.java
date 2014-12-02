package net.persistance.db;

import org.dbunit.dataset.ITable;
import org.dbunit.util.TableFormatter;
import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.junit.*;
import org.junit.Assert.*;

import javax.persistence.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.Date.*;

import java.util.List;
import java.util.Date;
import java.util.Calendar;

import java.io.*;



public class UserTest{
	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Users");
	public static EntityManager em;
	public static EntityTransaction tx = null;

	public UserTest(){
		
	}
	
	@BeforeClass
	public static void initEntityManager()throws Exception {
		em = emf.createEntityManager();
	}

	@AfterClass
	public static void closeEntityManager()throws Exception {
		em.close();
	}

	@Before
	public void initTransaction() throws Exception {
		tx = em.getTransaction();
		seedData();
	}

		private static final Logger LOG = LoggerFactory.getLogger(UserTest.class);


	protected void seedData() throws Exception {
		tx.begin();
		Connection connection = em.unwrap(java.sql.Connection.class);
		try {
			IDatabaseConnection dbUnitCon = new DatabaseConnection(connection);
			dbUnitCon.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
			IDataSet dataset;
			FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
			flatXmlDataSetBuilder.setColumnSensing(true);
			InputStream in =Thread.currentThread()
			.getContextClassLoader()
			.getResourceAsStream("data/dataset.xml");
			if(in !=null){
				LOG.warn("DataSet found");
				dataset = flatXmlDataSetBuilder.build(in);
			} else {
				LOG.warn("DataSet not found");
				dataset= new DefaultDataSet();
			}
			DatabaseOperation.REFRESH.execute(dbUnitCon, dataset);
		} finally {
			tx.commit();
		}
	}


	@Test
	public final void testFindAll() {
		LOG.info("testFindAll");
		try{
			tx.begin();
			List<User> list = em.createQuery("select u from User u").getResultList();
			LOG.debug("find by example successful, result size: "+ list.size());
			Assert.assertEquals("did not get expected number of entities ", 5, list.size());
		} catch (RuntimeException re) {
			LOG.error("find by example failed", re);
			throw re;
		}finally{
			tx.commit();
		}
	}

	/*@Test
	public void addTwoUsers() throws Exception {
		LOG.info("addTwoUsers");
		try{
			tx.begin();
			User user1=new User("James","Gosling",new Date(2100,10,10));
			em.persist(user1);
			User user2=new User("Dennis","Ritchie",new Date(2100,10,10));
			em.persist(user2);
		}catch (RuntimeException re) {
			LOG.error("find by example failed", re);
		throw re;
		}finally{
			tx.commit();
		}
	}*/
}

