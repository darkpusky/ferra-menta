package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import daos.UtenteDao;
import models.Utente;


@Stateless
public class Ejb {
	
	@EJB
	private UtenteDao utenteDao;
	
	public void inserisciUtente() {
		this.utenteDao.inserisciUtente();
	}
	
	public Utente riceviUtente() {
		return this.utenteDao.riceviUtente();
	}
}
