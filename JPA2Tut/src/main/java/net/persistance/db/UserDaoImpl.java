package net.persistance.db;

//import  java.sql.Date.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.*;
import  java.util.List;
import  java.util.Date;

public class UserDaoImpl extends GenericDAO
{	
	private static final Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
	public void createUser(List<User> users){
		log.info("Create a User");
		EntityManager em = createEntityManager();
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction();
			tx.begin();


			for(User u: users){	
				em.persist(u);
			}

			tx.commit();
		}catch(Exception re){
			if(tx!=null)
				log.error("Something went wrong; Discard all partial changes");
			tx.rollback();
			}finally{
				closeEntityManager();
			}
		
	}

	public void createUser(String name, String forename,  Date added){
		log.info("Create a User");
		EntityManager em = createEntityManager();
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction();
			tx.begin();


			User user=new User(name,forename,added);


			em.persist(user);
			tx.commit();
		}catch(Exception re){
			if(tx!=null)
				log.error("Something went wrong; Discard all partial changes");
			tx.rollback();
			}finally{
				closeEntityManager();
			}
	}

	public List<User> findAll(){
		log.info("Create a User");
		EntityManager em = createEntityManager();
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction();
			tx.begin();

			List<User> users =  em.createQuery("SELECT u FROM User u").getResultList(); // http://www.java2s.com/Code/Java/JPA/FindAllObjects.htm

			return users;
			/*em.persist();*/
			/*tx.commit();*/
		}catch(Exception re){
			if(tx!=null)
				log.error("Something went wrong; Discard all partial changes");
			tx.rollback();
			return null;
			}finally{
				closeEntityManager();
			}
	}

	public User findById(int id){
		log.info("Create a User");
		EntityManager em = createEntityManager();
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction();
			tx.begin();


			return (User) em.find(User.class ,id);
		


			/*em.flush();
			tx.commit();*/
		}catch(Exception re){
			if(tx!=null)
				log.error("Something went wrong; Discard all partial changes");
			tx.rollback();
			return null;
			}finally{
				closeEntityManager();
			}
	}

	public void deleteUser(User persistentInstance){
		log.info("Create a User");
		EntityManager em = createEntityManager();
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction();
			tx.begin();
	
			em.remove(persistentInstance);

		
			em.flush();
			tx.commit();
		}catch(Exception re){
			if(tx!=null)
				log.error("Something went wrong; Discard all partial changes");
			tx.rollback();
			}finally{
				closeEntityManager();
			}
	}
}
