package daos;

import java.io.Serializable;
import java.util.LinkedHashSet;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import models.Prodotto;
import models.Utente;
import utils.Costants;
import utils.Manager;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class UtenteDao implements Serializable{

	private static final long serialVersionUID = 7505088539991239186L;
	
	private Manager manager=new Manager();
	
	//Insert
	public void inserisciUtente(Utente utente) {
		manager.open();
		manager.getManager().persist(utente);
		manager.commit();
		manager.clear();
		manager.close();
	}
	
	//Select find by id 
	public Utente findByIdUtente(int id) {
		Utente utente=new Utente();
		manager.open();
		utente= manager.getManager().find(Utente.class, id);
		manager.close();
		return utente;
	}
	
	//Select find by username
	public Utente findByUsernameUtente(String username) {
		Utente utente=null;
		manager.open();
		try{
			Query query = manager.getManager().createQuery(Costants.QueryUtente.SELECT_USERNAME + "'" +username +"'",Utente.class);
			utente =(Utente) query.getSingleResult();
		}catch(NoResultException e) {
		}
		manager.clear();
		return utente;
	}
	
	//Update
	public void modificaUtente(Utente utente) {
		Utente utenteTemp = new Utente();
		utenteTemp = this.findByIdUtente(utente.getId());
		utenteTemp =utente;
		manager.open();
		manager.getManager().merge(utenteTemp);
		manager.commit();
		manager.clear();
		manager.close();
	}
	
	//Remove
	public void rimuoviUtente(Utente utente) {
		manager.open();
		utente=manager.getManager().find(Utente.class, utente.getId());
		manager.getManager().remove(utente);
		manager.commit();
		manager.clear();
		manager.close();
	}
	
	//Get tutti gli utenti
	public LinkedHashSet<Utente> findAllUtenti(){
		manager.open();
		LinkedHashSet<Utente> utenti=new LinkedHashSet<>();
		TypedQuery<Utente> query=manager.getManager().createQuery(Costants.QueryUtente.SELECT_ALL,Utente.class);
		for(Utente e : query.getResultList()) utenti.add(e);
		manager.close();
		return utenti;
	}
}
