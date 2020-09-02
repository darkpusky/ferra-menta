package daos;

import java.io.Serializable;
import java.util.LinkedHashSet;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;

import models.Prodotto;
import utils.Costants;
import utils.Manager;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class ProdottoDao implements Serializable{

	private static final long serialVersionUID = -588035965960098775L;
	
	private Manager manager=new Manager();
	
	//Insert
	public void inserisciProdotto(Prodotto prodotto) {
		manager.open();
		manager.getManager().persist(prodotto);
		manager.commit();
		manager.clear();
		manager.close();
	}
	
	//Select find by id
	public Prodotto findByIdProdotto(int id) {
		manager.open();
		Prodotto prodotto=new Prodotto();
		prodotto=manager.getManager().find(Prodotto.class, id);
		manager.clear();
		manager.close();
		return prodotto;
	}
	
	public LinkedHashSet<Prodotto> findAll() {
		manager.open();
		LinkedHashSet<Prodotto> prodotti=new LinkedHashSet<>();
		TypedQuery<Prodotto> query=manager.getManager().createQuery(Costants.QueryProdotto.SELECT_ALL,Prodotto.class);
		for(Prodotto e : query.getResultList()) prodotti.add(e);
		manager.commit();
		manager.close();
		return prodotti;
	}
	
	//Update
	public void modificaProdotto(Prodotto prodotto) {
		Prodotto prodottoTemp = new Prodotto();
		prodottoTemp = this.findByIdProdotto(prodotto.getId());
		prodottoTemp=prodotto;
		manager.open();
		manager.getManager().merge(prodottoTemp);
		manager.commit();
		manager.clear();
		manager.close();
	}
	
	//Remove solo se nessun storico ha il prodotto
	public void rimuoviProdotto(Prodotto prodotto) {
		manager.open();
		prodotto=manager.getManager().find(Prodotto.class, prodotto.getId());
		manager.getManager().remove(prodotto);
		manager.commit();
		manager.clear();
		manager.close();
	}

}
