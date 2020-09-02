package daos;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.TypedQuery;

import models.Prodotto;
import models.Storico;
import models.Utente;
import utils.Costants;
import utils.Manager;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class StoricoDao implements Serializable{

	private static final long serialVersionUID = -6337791046729219506L;
	
	Manager manager = new Manager();
	
	//Insert
	public void inserisciStorico(LinkedHashSet<Storico> storico) {
		manager.open();
		for(Storico e:storico) manager.getManager().persist(e);
		manager.commit();
		manager.clear();
		manager.close();
	}
	
	//Select find by id
	public Storico findByIdStorico(int id) {
		manager.open();
		Storico storico=new Storico();
		storico = manager.getManager().find(Storico.class,id);
		manager.close();
		return storico;
	}
	
	//Select find by id utente
	public LinkedHashSet<Storico> findByIdUtente(int id){
		manager.open();
		TypedQuery<Storico> query= manager.getManager().createQuery(Costants.QueryStorico.SELECT_IDUTENTE+id, Storico.class);
		List<Storico> storico = query.getResultList();
		LinkedHashSet<Storico> storic=new LinkedHashSet<>();
		for(Storico e:storico) storic.add(e);
		manager.close();
		return storic;
	}
	
	//Update
	public void modificaStorico(Storico storico) {
		Storico storicoTemp=new Storico();
		storicoTemp = this.findByIdStorico(storico.getId());
		storicoTemp=storico;
		storicoTemp.setData(storico.getData());
		manager.open();
		manager.getManager().merge(storicoTemp);
		manager.commit();
		manager.clear();
		manager.close();
	}
	
	//Remove
	public void rimuoviStorico(Storico storico) {
		manager.open();
		storico=manager.getManager().find(Storico.class,storico.getId());
		manager.getManager().remove(storico);
		manager.commit();
		manager.clear();
		manager.close();
	}
	
	//Remove con id utente
	public void rimuoviStorico(int id) {
		manager.open();
		TypedQuery<Storico> query= manager.getManager().createQuery(Costants.QueryStorico.SELECT_IDUTENTE+id, Storico.class);
		List<Storico> storico = query.getResultList();
		//try {
			if(storico.size()>0) {
				for(Storico e:storico) manager.getManager().remove(e);
				manager.commit();
			}
		//}catch(Exception e) {}
		manager.clear();
		manager.close();
	}
	
	public LinkedHashSet<Storico> getStoricoDate(int id) {
		manager.open();
		TypedQuery<Storico> query= manager.getManager().createQuery(Costants.QueryStorico.SELECT_IDUTENTE+id+Costants.QueryStorico.SELECT_ALLBYDATA, Storico.class);
		List<Storico> storico = query.getResultList();
		LinkedHashSet<Storico> storic=new LinkedHashSet<>();
		for(Storico e:storico) storic.add(e);
		manager.close();
		return storic;
	}
	
	public LinkedHashSet<Storico> getStoriciByData(int id,String data){
		manager.open();
		TypedQuery<Storico> query= manager.getManager().createQuery(Costants.QueryStorico.SELECT_IDUTENTE+id+Costants.QueryStorico.SELECT_BYDATA+"'"+data+"'", Storico.class);
		List<Storico> storico = query.getResultList();
		LinkedHashSet<Storico> storic=new LinkedHashSet<>();
		for(Storico e:storico) storic.add(e);
		manager.close();
		return storic;
	}
}
