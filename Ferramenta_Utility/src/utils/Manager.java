package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Manager {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public EntityManager getManager() {return em;}
	
	public void open() {
		this.emf=Persistence.createEntityManagerFactory("ferramenta");
		this.em=this.emf.createEntityManager();
		this.em.getTransaction().begin();
	}
	
	public void close() {
		em.close();
		emf.close();
	}
	
	public void commit() {
		em.getTransaction().commit();
	}
	
	public void clear() {
		em.clear();
	}
	
}
