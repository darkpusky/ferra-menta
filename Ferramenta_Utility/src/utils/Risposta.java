package utils;

import java.util.LinkedHashSet;

import models.Prodotto;
import models.Storico;
import models.Utente;

public class Risposta {
	
	private Utente utente;
	private Prodotto prodotto;
	private Storico storico;
	private String messaggio;
	private int status;
	
	LinkedHashSet<Prodotto> prodotti;
	LinkedHashSet<Utente> utenti;
	LinkedHashSet<Storico> storici;
	
	public Risposta(int status) {
		this.status=status;
		this.messaggio="";
		this.utente=null;
		this.prodotto=null;
		this.storico=null;
		this.prodotti=new LinkedHashSet<>();
		this.utenti=new LinkedHashSet<>();
		this.storici=new LinkedHashSet<>();
	}
	
	public Risposta(int status,String messaggio) {
		this.status=status;
		this.messaggio=messaggio;
		this.utente=null;
		this.prodotto=null;
		this.storico=null;
		this.prodotti=new LinkedHashSet<>();
		this.utenti=new LinkedHashSet<>();
		this.storici=new LinkedHashSet<>();
	}
	
	public Risposta(int status,Utente utente) {
		this.status=status;
		this.messaggio="";
		this.utente=utente;
		this.prodotto=null;
		this.storico=null;
	}
	
	public Risposta(int status,Utente utente,String messaggio) {
		this.status=status;
		this.utente=utente;
		this.messaggio=messaggio;
		this.prodotto=null;
		this.storico=null;
	}
	
	public Risposta(int status,Prodotto prodotto) {
		this.status=status;
		this.prodotto=prodotto;
		this.messaggio="";
		this.utente=null;
		this.storico=null;
	}
	
	public Risposta(int status,Prodotto prodotto,String messaggio) {
		this.status=status;
		this.messaggio=messaggio;
		this.prodotto=prodotto;
		this.utente=null;
		this.storico=null;
	}
	
	public Risposta(int status,Storico storico) {
		this.status=status;
		this.storico=storico;
		this.messaggio="";
		this.utente=null;
		this.prodotto=null;
	}
	
	public Risposta(int status,Storico storico,String messaggio) {
		this.status=status;
		this.storico=storico;
		this.messaggio=messaggio;
		this.utente=null;
		this.prodotto=null;
	}
	
	public int getStatus() {return this.status;}
	
	public String getMessaggio() {return this.messaggio;}
	
	public Object getObject() {
		if(this.utente!=null) return this.utente;
		if(this.prodotto!=null) return this.prodotto;
		if(this.storico!=null) return this.storico;
		else return null;
	}
	
	public LinkedHashSet<Prodotto> getProdotti(){return this.prodotti;}
	public LinkedHashSet<Utente> getUtenti(){return this.utenti;}
	public LinkedHashSet<Storico> getStorico(){return this.storici;}
	
	public void setProdotti(LinkedHashSet<Prodotto> prodotti) {this.prodotti=prodotti;}
	public void setUtenti(LinkedHashSet<Utente> utenti) {this.utenti=utenti;}
	public void setStorico(LinkedHashSet<Storico> storico) {this.storici=storico;}
}
