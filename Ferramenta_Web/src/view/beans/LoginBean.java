package view.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import enums.Pagine;
import models.Utente;
import utils.Costants;
import utils.Risposta;
import view.clients.Client;

import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = -2433391625495430076L;
	
	private Client client;
	private Utente utenteLogin;
	private Utente utenteRegistrazione;
	private String pass;
	
	@Inject
	private MainBean mainBean;
	
	@PostConstruct
	public void init() {
		client=new Client();
		utenteLogin=new Utente();
		utenteRegistrazione=new Utente();
		pass="";
	}
	
	//Login
	public String login() {
		Risposta risposta=client.login(utenteLogin.getUsername());
		Utente utente= new Utente();
		
		try {
			utente=(Utente)risposta.getObject();
			if(!(utente.getPassword().equals(utenteLogin.hashPassword(utenteLogin.getPassword())))) {
				FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Errore","Password inserita errata"));
				return "/accesso";
			}
			utenteLogin=utente;
		}catch(NullPointerException e) {
			FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Errore","Utente non trovato"));
			return "/accesso";
		}
		
		//tutto corretto allora inserisce il token
		utenteLogin.setToken(utenteLogin.generateToken());
		risposta=client.modificaUtente(utenteLogin);
		
		//errore nll'inserimento del token
		if(risposta.getStatus() != 200 && risposta.getStatus()!=204) {
			FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Errore",risposta.getMessaggio()));
			return "/accesso";
		}
		
		//riprende il token
		risposta=client.login(utenteLogin.getUsername());
		utenteLogin = (Utente)risposta.getObject();
		
		//accedi alla home
		mainBean.setPagina(Pagine.HOME);
		return "/home.xhtml?faces-redirect=true";
	}
	
	//Registrazione
	public void registrazione() {
		if(validazioneRegistrazione()) {
			utenteRegistrazione.setPassword(utenteRegistrazione.hashPassword(utenteRegistrazione.getPassword()));
			Risposta risposta= client.registrazione(utenteRegistrazione);
			if(risposta.getStatus() == 200 || risposta.getStatus() == 204) {
				FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_INFO,"",risposta.getMessaggio()));
				utenteRegistrazione = new Utente();
			}
			else FacesContext.getCurrentInstance().addMessage("pagina", new FacesMessage(FacesMessage.SEVERITY_ERROR,"",risposta.getMessaggio()));
		}
	}
	
	//Validazione
	public boolean validazioneRegistrazione() {
		boolean flag=true;
		//if(context.getMessages().hasNext()) context.getMessages().remove();
		//Nome Required 
		if(this.utenteRegistrazione.getNome().equals("")) {
			FacesContext.getCurrentInstance().addMessage("nome", new FacesMessage(FacesMessage.SEVERITY_ERROR,"*","Campo nome richiesto"));
			flag=false;
		}
		
		//Cognome required
		if(this.utenteRegistrazione.getCognome().equals("")) {
			FacesContext.getCurrentInstance().addMessage("cognome", new FacesMessage(FacesMessage.SEVERITY_ERROR,"*","Campo cognome richiesto"));
			flag=false;
		}
		
		//Username required
		if(this.utenteRegistrazione.getUsername().equals("")) {
			FacesContext.getCurrentInstance().addMessage("username", new FacesMessage(FacesMessage.SEVERITY_ERROR,"*","Campo username richiesto"));
			flag=false;
		}
		
		//Email required and regex
		if(this.utenteRegistrazione.getEmail().equals("")) {
			FacesContext.getCurrentInstance().addMessage("email", new FacesMessage(FacesMessage.SEVERITY_ERROR,"*","Campo email richiesto"));
			flag=false;
		}else if(!(this.utenteRegistrazione.getEmail().matches(Costants.Other.REGEX_EMAIL))) {
			FacesContext.getCurrentInstance().addMessage("email", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Attenzione","Email non valida"));
			flag=false;
		}
		
		//Password required and min-length 8
 		if(this.utenteRegistrazione.getPassword().equals("")) {
			FacesContext.getCurrentInstance().addMessage("password", new FacesMessage(FacesMessage.SEVERITY_ERROR,"*","Campo password richiesto"));
			flag=false;
		}else if(this.utenteRegistrazione.getPassword().length() < 8 && this.utenteRegistrazione.getPassword().length() > 0) {
			FacesContext.getCurrentInstance().addMessage("password", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Attenzione","La password deve avere almeno 8 caratteri"));
			flag=false;
		}
 		
 		//Repassword required and equal to password
 		if(this.pass.equals("")) {
 			FacesContext.getCurrentInstance().addMessage("repassword", new FacesMessage(FacesMessage.SEVERITY_ERROR,"*","Campo re-password richiesto"));
 			flag=false;
 		}else if(!(this.pass.equals(this.utenteRegistrazione.getPassword()))) {
 			FacesContext.getCurrentInstance().addMessage("repassword", new FacesMessage(FacesMessage.SEVERITY_ERROR,"Attenzione","Le password non coincidono"));
 			flag=false;
 		}
		
		return flag;
	}
	
	public MainBean getMainBean() {return this.mainBean;}
	public Utente getUtenteLogin() {return this.utenteLogin;}
	public Utente getUtenteRegistrazione() {return this.utenteRegistrazione;}
	public String getPass() {return this.pass;}
	
	public void setMainBean(MainBean mainBean) {this.mainBean=mainBean;}
	public void setUtenteLogin(Utente utente) {this.utenteLogin=utente;}
	public void setUtenteRegistrazione(Utente utente) {this.utenteRegistrazione=utente;}
	public void setPass(String pass) {this.pass=pass;}
	
}
