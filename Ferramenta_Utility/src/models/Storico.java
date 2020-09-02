package models;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import enums.Stato;

@Entity
@Table(name="storico")
public class Storico implements Serializable{

	private static final long serialVersionUID = -2796630172041169342L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="data_acquisto",nullable=false)
	private String data;
	
	@Column(name="quantita",nullable=false)
	private int quantita;
	
	@Column(name="prezzo_tot",nullable=false)
	private double prezzo;
	
	@Column(name="stato",nullable=false)
	@Enumerated(EnumType.STRING)
	private Stato stato;
	
	@ManyToOne
	@JoinColumn(name="id_utente",nullable=false)
	private Utente utente;
	
	@ManyToOne
	@JoinColumn(name="id_prodotto",nullable=false)
	private Prodotto prodotto;
	
	public int getId() {return this.id;}
	public String getData() {return this.data;}
	public int getQuantita() {return this.quantita;}
	public double getPrezzo() {return this.prezzo;}
	public Stato getStato() {return this.stato;}
	public Utente getUtente() {return this.utente;}
	public Prodotto getProdotto(){return this.prodotto;}
	
	public void setId(int id) {this.id=id;}
	public void setData(String data) {this.data=data;}
	public void setQuantita(int quantita) {this.quantita=quantita;}
	public void setPrezzo(double prezzo) {this.prezzo=prezzo;}
	public void setStato(Stato stato) {this.stato=stato;}
	public void setUtente(Utente utente) {this.utente=utente;}
	public void setProdotti(Prodotto prodotto) {this.prodotto=prodotto;}
	
	public boolean equals(Object o) {
		if(o == null) return false;
		if(!(o instanceof Storico)) return false;
		Storico temp=(Storico)o;
		return 	this.id==temp.id &&
				(this.data != null ? this.data.equals(temp.data) : true) &&
				this.quantita==temp.quantita &&
				this.prezzo==temp.prezzo &&
				this.stato.toString().equals(temp.stato.toString()) &&
				this.utente.equals(temp.utente);
				//&& 
				//this.prodotti.containsAll(temp.prodotti);
	}
	
	public int hashCode() {
		int hash=0;
		hash+=this.id;
		hash+=this.quantita;
		hash+=(int)this.prezzo;
		hash+=this.stato.toString().length();
		hash+=this.utente.hashCode();
		return hash;
	}
	
	public String toString() {
		return 	"Storico" +
				this.id + " " +
				this.data.toString() + " " +
				this.quantita + " " +
				this.prezzo + " " +
				this.stato.toString() + " " +
				this.utente.toString();
	}
	
	public String dataConverter() {
		if(this.data.length()>0) {
			String anno=this.data.substring(0,4);
			String mese=this.data.substring(5,7);
			String giorno=this.data.substring(8, 10);
			return giorno+"/"+mese+"/"+anno;
		}
		return "";
	}
	
	public String dataSqlConverter() {
		if(this.data.length()>0) {
			String anno=this.data.substring(0,4);
			String mese=this.data.substring(5,7);
			String giorno=this.data.substring(8, 10);
			return anno+"/"+mese+"/"+giorno;
		}
		return "";
	}
	
	/*public String dataConverter() {
		Date data=new Date(this.data.getTime());
		data.
		DateFormat date=new SimpleDateFormat("dd-MM-yyyy");
		return date.format(data);
	}*/
	
}
