package view.beans;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.LinkedHashSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import enums.Pagine;
import enums.Stato;
import models.Prodotto;
import models.Storico;
import utils.Risposta;
import view.clients.Client;

@Named
@SessionScoped
public class CarrelloBean implements Serializable{

	private static final long serialVersionUID = 1640040481148442052L;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private MainBean mainBean;
	
	private LinkedHashSet<Prodotto> prodotti;
	private double totale;
	private Client client;
	
	@PostConstruct
	public void init() {
		client=new Client();
		prodotti=new LinkedHashSet<>();
		totale=0;
	}
	
	public String compra() {
		LinkedHashSet<Storico> lista=new LinkedHashSet<>();
		for(Prodotto e: prodotti) {
			Storico storico= new Storico();
			storico.setUtente(loginBean.getUtenteLogin());
			storico.setProdotti(e);
			storico.setStato(Stato.IN_CORSO);
			storico.setQuantita(e.getConteggio());
			storico.setPrezzo(e.getPrezzo() * e.getConteggio());
			lista.add(storico);
		}
		Risposta risposta=client.compra(lista);
		if(risposta.getStatus() != 200 && risposta.getStatus() != 204) {
			FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Errore",risposta.getMessaggio()));
			return "home";
		}else {
			FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_INFO,"Successo",risposta.getMessaggio()));
			this.prodotti=new LinkedHashSet<>();
			this.totale=0;
		}
		mainBean.setPagina(Pagine.CATALOGO);
		return "home?faces-redirect=true";
	}
	
	public void rimuoviCarrello(Prodotto prodotto) {
		prodotti.remove(prodotto);
		this.setTotale(this.getTotale() - (prodotto.getConteggio() * prodotto.getPrezzo()));
		PrimeFaces.current().ajax().update("form");
	}
	
	public String getTotal() {
		DecimalFormat df= new DecimalFormat("#.##");
		return df.format(this.totale);
	}
	
	public LinkedHashSet<Prodotto> getProdotti(){return this.prodotti;}
	public double getTotale() {return this.totale;}
	public LoginBean getLoginBean() {return this.loginBean;}
	public MainBean getMainBean() {return this.mainBean;}
	
	public void setProdotti(LinkedHashSet<Prodotto> prodotti) {this.prodotti=prodotti;}
	public void setTotale(double totale) {this.totale=totale;}
	public void setLoginBean(LoginBean loginBean) {this.loginBean=loginBean;}
	public void setMainBean(MainBean mainBean) {this.mainBean=mainBean;}
	
}
