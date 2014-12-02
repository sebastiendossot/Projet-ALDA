package net.persistance.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public abstract class GenericDAO
{
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Users");

//Users est un nom générique (Rien a voir avec la classe user)et fait réference a un fichier dans resources un fichier xml :
// <persistance-unit name = Users><provider>....</provider><class>...
//Ne pas oublier de modifier ce fichier
	private EntityManager em;
	public EntityManager createEntityManager(){
		this.em = emf.createEntityManager();
		return em;
	}

	public void closeEntityManager(){
		this.em.close();
	}
}
