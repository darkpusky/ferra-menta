package view.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedHashSet;

import models.Prodotto;
import models.Utente;
import utils.Risposta;
import view.clients.AdminClient;
import view.clients.Client;

@Named
@SessionScoped
public class AdminBean implements Serializable {

	private static final long serialVersionUID = 101226539316634349L;
	
	@Inject
	private MainBean mainBean;
	
	private AdminClient admin;
	private Client client;
	
	//Lista utenti
	private LinkedHashSet<Utente> utenti;
	private Utente utenteSel;
	private LinkedHashSet<Prodotto> prodotti;
	private Prodotto prodottoSel;
	
	@PostConstruct
	public void init() {
		admin=new AdminClient();
		utenti=new LinkedHashSet<>();
		utenteSel=new Utente();
		client=new Client();
		prodotti=new LinkedHashSet<>();
		prodottoSel=new Prodotto();
	}
	
	//Lista prodotti
	public void modificaProdotto() {
		Risposta risposta= admin.modificaProdotto(prodottoSel);
		if(risposta.getStatus() != 200 && risposta.getStatus() != 204) {
			FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Errore",risposta.getMessaggio()));
		}else {
			FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_INFO,"Complimenti",risposta.getMessaggio()));
		}
	}
	
	public void getListaProdotti() {
		Risposta risposta=client.riceviProdotti();
		if(risposta.getStatus() != 200 && risposta.getStatus() != 204) {
			FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Errore",risposta.getMessaggio()));
		}else {
			this.setProdotti(risposta.getProdotti());
		}
	}
	
	//Lista utenti
	public void modificaRuoloUtente() {
		Risposta risposta=client.modificaUtente(utenteSel);
		if(risposta.getStatus() != 200 && risposta.getStatus() != 204) {
			FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Errore",risposta.getMessaggio()));
		}else {
			FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_INFO,"Complimenti",risposta.getMessaggio()));
		}
	}
	
	public void getListaUtenti() {
		Risposta risposta= admin.riceviListaUtenti();
		if(risposta.getStatus() != 200 && risposta.getStatus() != 204) {
			FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Errore",risposta.getMessaggio()));
		}else {
			this.setUtenti(risposta.getUtenti());
		}
	}
	
	public MainBean getMainBean() {return this.mainBean;}
	public LinkedHashSet<Utente> getUtenti(){return this.utenti;}
	public Utente getUtenteSel() {return this.utenteSel;}
	public LinkedHashSet<Prodotto> getProdotti(){return this.prodotti;}
	public Prodotto getProdottoSel() {return this.prodottoSel;}
	
	public void setMainBean(MainBean mainBean) {this.mainBean=mainBean;}
	public void setUtenti(LinkedHashSet<Utente> utenti) {this.utenti=utenti;}
	public void setUtenteSel(Utente utente) {this.utenteSel=utente;}
	public void setProdotti(LinkedHashSet<Prodotto> prodotti) {this.prodotti=prodotti;}
	public void setProdottoSel(Prodotto prodotto) {this.prodottoSel=prodotto;}
}
