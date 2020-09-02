package view.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import enums.Pagine;
import models.Prodotto;
import models.Utente;
import utils.Risposta;
import view.clients.Client;

@Named
@SessionScoped
public class HomeBean implements Serializable{

	private static final long serialVersionUID = 851021323082808041L;
	
	@Inject
	private MainBean mainBean;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private CarrelloBean carrelloBean;
	
	@Inject
	private StoricoBean storicoBean;
	
	@Inject
	private AdminBean adminBean;
	
	private Client client;
	
	//Account
	private String oldPassword;
	private String newPassword;
	
	//Catalogo
	private LinkedHashSet<Prodotto> prodotti;
	private Prodotto selProdotto;
	
	@PostConstruct
	public void init() {
		client=new Client();
		oldPassword="";
		newPassword="";
		prodotti=new LinkedHashSet<>();
	}
	
	//Prodotti Admin
	public void getProdottiAdmin() {
		this.mainBean.setPagina(Pagine.PRODOTTI);
		this.adminBean.getListaProdotti();
	}
	
	//Utenti Admin
	public void getUtenti() {
		this.mainBean.setPagina(Pagine.UTENTI);
		this.adminBean.getListaUtenti();
	}
	
	//Storico
	public void getStorico() {
		this.mainBean.setPagina(Pagine.STORICO);
		this.storicoBean.richiediStorici();;
	}
	
	//Carrello
	public void getCarrello() {
		this.mainBean.setPagina(Pagine.CARRELLO);
	}
	
	//Catalogo
	public void addCarrello() {
		if(this.carrelloBean.getProdotti().contains(selProdotto)) {
			int oldConta=0;
			for(Prodotto e: this.carrelloBean.getProdotti()) {
				if(selProdotto.equals(e)) {
					oldConta=e.getConteggio();
				}
			}
			this.carrelloBean.setTotale(this.carrelloBean.getTotale() -(oldConta * selProdotto.getPrezzo()));
			selProdotto.setConteggio(selProdotto.getConteggio()+oldConta);
			this.carrelloBean.getProdotti().remove(selProdotto);
		}
		FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_INFO,"Successo","Hai aggiunto il prodotto al carrello"));
		Prodotto prodotto=new Prodotto();
		prodotto.clone(selProdotto);
		this.carrelloBean.getProdotti().add(prodotto);
		this.carrelloBean.setTotale(this.carrelloBean.getTotale()+(prodotto.getConteggio() * prodotto.getPrezzo()));
		selProdotto.setConteggio(1);
		PrimeFaces.current().ajax().update("formNav");
		
	}
	
	public void getCatalogo() {
		this.mainBean.setPagina(Pagine.CATALOGO);
		Risposta risposta=client.riceviProdotti();
		prodotti=risposta.getProdotti();
	}
	
	
	//Account
	public void getAccount() {
		this.mainBean.setPagina(Pagine.ACCOUNT);
	}
	
	public void modificaAccount() {
		Risposta risposta=client.modificaUtente(loginBean.getUtenteLogin());
		if(risposta.getStatus() != 200 && risposta.getStatus() != 204) {
			FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Errore",risposta.getMessaggio()));
		}else {
			FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_INFO,"Complimenti",risposta.getMessaggio()));
		}
	}
	
	public String modificaPassword() {
		//la password old deve essere uguale a quella del login
		if(!(this.getLoginBean().getUtenteLogin().hashPassword(oldPassword).equals(this.getLoginBean().getUtenteLogin().getPassword()))) {
			FacesContext.getCurrentInstance().addMessage("password", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Errore","La password non corrisponde alla password attuale"));
			return "home";
		}
		//la passsword nuova deve avere 8 caretteri minimo e required
		if(this.newPassword.length() == 0) {
			FacesContext.getCurrentInstance().addMessage("repassword", new FacesMessage(FacesMessage.SEVERITY_ERROR,"*","Campo della password richiesto"));
			return "home";
		}
		if(this.newPassword.length() >0 && this.newPassword.length()<8) {
			FacesContext.getCurrentInstance().addMessage("repassword", new FacesMessage(FacesMessage.SEVERITY_ERROR,"*","La password deve avere una lunghezza di almeno 8 caratteri"));
			return "home";
		}
		this.loginBean.getUtenteLogin().setPassword(this.loginBean.getUtenteLogin().hashPassword(newPassword));
		this.modificaAccount();
		return "home";
	}
	
	public String cancellaAccount() {
		Risposta risposta=client.eliminaUtente(loginBean.getUtenteLogin());
		if(risposta.getStatus() != 200 && risposta.getStatus() != 204) {
			FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Errore",risposta.getMessaggio()));
			return "home";
		}
		this.loginBean.init();
		return "accesso?faces-redirect=true";
	}
	
	//Home
	public void getHome() {
		this.mainBean.setPagina(Pagine.HOME);
	}
	
	public MainBean getMainBean() {return this.mainBean;}
	public LoginBean getLoginBean() {return this.loginBean;}
	public CarrelloBean getCarrelloBean() {return this.carrelloBean;}
	public String getOldPassword() {return this.oldPassword;}
	public String getNewPassword() {return this.newPassword;}
	public LinkedHashSet<Prodotto> getProdotti() {return this.prodotti;}
	public Prodotto getSelProdotto() {return this.selProdotto;}
	public StoricoBean getStoricoBean() {return this.storicoBean;}
	public AdminBean getAdminBean() {return this.adminBean;}
	
	public void setMainBean(MainBean mainBean) {this.mainBean=mainBean;}
	public void setLoginBean(LoginBean loginBean) {this.loginBean=loginBean;}
	public void setCarrelloBean(CarrelloBean carrelloBean) {this.carrelloBean=carrelloBean;}
	public void setOldPassword(String pass) {this.oldPassword=pass;}
	public void setNewPassword(String pass) {this.newPassword=pass;}
	public void setProdotti(LinkedHashSet<Prodotto> prodotti) {this.prodotti=prodotti;}
	public void setSelProdotto(Prodotto prodotto) {this.selProdotto=prodotto;}
	public void setStoricoBean(StoricoBean storicoBean) {this.storicoBean=storicoBean;}
	public void setAdminBean(AdminBean adminBean) {this.adminBean=adminBean;}
	
}
