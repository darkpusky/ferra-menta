package daos;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import models.Utente;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class UtenteDao implements Serializable{

	private static final long serialVersionUID = 7505088539991239186L;
	
	public void inserisciUtente() {
		EntityManagerFactory emf;
		EntityManager em;
		emf=Persistence.createEntityManagerFactory("Prova");
		em=emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Utente utente=new Utente();
		utente.setNome("Valentin");
		utente.setCognome("Puscasu");
		
		em.persist(utente);
		
		em.getTransaction().commit();
		em.detach(utente);
		em.close();
		emf.close();
	}
	
	public Utente riceviUtente() {
		EntityManagerFactory emf;
		EntityManager em;
		emf=Persistence.createEntityManagerFactory("Prova");
		em=emf.createEntityManager();
		
		em.getTransaction().begin();
		Utente utente=new Utente();
		utente = em.find(Utente.class, 6);
		em.close();
		emf.close();
		return utente;
	}
	
}
