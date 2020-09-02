package ejb;

import java.io.Serializable;
import java.util.LinkedHashSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import daos.ProdottoDao;
import daos.StoricoDao;
import daos.UtenteDao;
import models.Prodotto;
import models.Storico;
import models.Utente;


@Stateless
public class EjbAdmin implements Serializable{
	
	private static final long serialVersionUID = 6955101728456827695L;

	@EJB
	private UtenteDao utenteDao;
	
	@EJB
	private ProdottoDao prodottoDao;
	
	@EJB
	private StoricoDao storicoDao;
	
	//inserire un prodotto,solo l'admin può inserire prodotti
	public void insertProdotto(Prodotto prodotto) {
		prodottoDao.inserisciProdotto(prodotto);
	}
	
	//l'admin inserisce lo storico al momento dell'acquisto per il magazzino
	public void insertStorico(LinkedHashSet<Storico> storico) {
		storicoDao.inserisciStorico(storico);
	}
	
	//modifica l'utenza dell'admin
	public void updateUtente(Utente utente) {
		utenteDao.modificaUtente(utente);
	}
	
	//modifica prodotto
	public void updateProdotto(Prodotto prodotto) {
		prodottoDao.modificaProdotto(prodotto);
	}
	
	//elimina prodotto
	public void removeProdotto(Prodotto prodotto) {
		prodottoDao.rimuoviProdotto(prodotto);
	}
	
	//riceve tutti gli utenti per l'admin
	public LinkedHashSet<Utente> getUtenti() {
		return utenteDao.findAllUtenti();
	}
}
