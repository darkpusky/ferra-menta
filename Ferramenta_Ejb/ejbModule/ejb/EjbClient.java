package ejb;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import daos.ProdottoDao;
import daos.StoricoDao;
import daos.UtenteDao;
import models.Prodotto;
import models.Storico;
import models.Utente;


@Stateless
public class EjbClient implements Serializable{
	
	private static final long serialVersionUID = -7248988308168021315L;

	@EJB
	private UtenteDao utenteDao;
	
	@EJB
	private ProdottoDao prodottoDao;
	
	@EJB
	private StoricoDao storicoDao;
	
	//riceve il prodotto tramite il suo id
	public Prodotto getProdottoId(int id) {
		return prodottoDao.findByIdProdotto(id);
	}

	//riceve l'utente,il cliente può ricevere solo il suo utente al momento del login
	//todo riceve l'utente tramite username
	public Utente getUtenteUsername(String username) {
		try{
			return utenteDao.findByUsernameUtente(username);
		}catch(Exception e){
			return null; 
		}
	}
	
	//inserisce l'utente quando il cliente si registra
	//l'admin non può inserire utenti,solo modificarli o cancellarli
	public void insertUtente(Utente utente) {
		utenteDao.inserisciUtente(utente);
	}
	
	//l'utente inserisce lo storico al momento dell'acquisto
	public void insertStorico(LinkedHashSet<Storico> storico) {
		storicoDao.inserisciStorico(storico);
	}
	
	//l'utente riceve il suo storico
	//forse non serve storico singolo
	public Storico getStoricoId(int id) {
		return storicoDao.findByIdStorico(id);
	}
	
	//modificare l'utenza dell'utente
	public void updateUtente(Utente utente) {
		utenteDao.modificaUtente(utente);
	}
	
	//eliminare l'account
	public void removeUtente(String username) {
		Utente utente=this.getUtenteUsername(username);
		storicoDao.rimuoviStorico(utente.getId());
		utenteDao.rimuoviUtente(utente);
	}
	
	//modifica dello storico solo se lo storico è in corso di approvazione
	public void updateStorico(Storico storico) {
		storicoDao.modificaStorico(storico);
	}
	
	//rimuovi lo storico solo se lo storico è in corso di approvazione
	public void removeStorico(Storico storico) {
		storicoDao.rimuoviStorico(storico);
	}
	
	public LinkedHashSet<Prodotto> getProdotti() {
		return prodottoDao.findAll();
	}
	
	public LinkedHashSet<Storico> getStorico(int id){
		return storicoDao.findByIdUtente(id);
	}
	
	public LinkedHashSet<Storico> getStoriciData(int id){
		return storicoDao.getStoricoDate(id);
	}
	
	public LinkedHashSet<Storico> getStoriciByData(int id,String data){
		return storicoDao.getStoriciByData(id, data);
	}
	
}
