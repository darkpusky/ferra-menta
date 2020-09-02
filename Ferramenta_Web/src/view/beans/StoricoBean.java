package view.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import models.Storico;
import utils.Risposta;
import view.clients.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Named
@SessionScoped
public class StoricoBean implements Serializable{

	private static final long serialVersionUID = 5795777579757044878L;
	
	@Inject
	private LoginBean loginBean;
	
	private List<Map.Entry<String,LinkedHashSet<Storico>>> storici;
	private Client client;
	
	@PostConstruct
	public void init() {
		client=new Client();
		storici=new ArrayList<>();
	}
	
	public void richiediStorici() {
		Map<String,LinkedHashSet<Storico>> map=new HashMap<String,LinkedHashSet<Storico>>();
		Risposta risposta=client.richiediStoricoData(loginBean.getUtenteLogin());
		for(Storico e: risposta.getStorico()) {
			Risposta newRisposta=client.richiediStoriciData(loginBean.getUtenteLogin(), e.dataSqlConverter());
			map.put(e.dataConverter(), newRisposta.getStorico());
		}
		Set<Map.Entry<String,LinkedHashSet<Storico>>> mapSet= map.entrySet();
		this.storici = new ArrayList<Map.Entry<String, LinkedHashSet<Storico>>>(mapSet);
	}
	
	public List<Map.Entry<String,LinkedHashSet<Storico>>> getStorici(){return storici;}
	public LoginBean getLoginBean() {return this.loginBean;}
	
	public void setStorici(List<Map.Entry<String,LinkedHashSet<Storico>>> storici) {this.storici=storici;}
	public void setLoginBean(LoginBean loginBean) {this.loginBean=loginBean;}
}
