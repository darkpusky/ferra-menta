package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="prodotti")
public class Prodotto implements Serializable{

	private static final long serialVersionUID = 4087663214312039745L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="codice",length=5,unique=true,nullable=false)
	private String codice;
	
	@Column(name="prezzo",nullable=false)
	private double prezzo;
	
	@Column(name="quantita",nullable=false)
	private int quantita;
	
	@Column(name="nome",unique=true,length=50,nullable=false)
	private String nome;
	
	@Column(name="descrizione",length=500,nullable=true)
	private String descrizione;
	
	@Column(name="marca",length=50,nullable=false)
	private String marca;
	
	@Column(name="attivo",nullable=false)
	private boolean attivo;
	
	@Column(name="path_img",nullable=false)
	private String immagine="";
	
	private transient int conteggio=1;
	
	public int getId() {return this.id;}
	public String getCodice() {return this.codice;}
	public double getPrezzo() {return this.prezzo;}
	public int getQuantita() {return this.quantita;}
	public String getNome() {return this.nome;}
	public String getDescrizione() {return this.descrizione;}
	public String getMarca() {return this.marca;}
	public boolean isAttivo() {return this.attivo;}
	public String getImmagine() {return this.immagine;}
	public int getConteggio() {return this.conteggio;}
	
	public void setId(int id) {this.id=id;}
	public void setCodice(String codice) {this.codice=codice;}
	public void setPrezzo(double prezzo) {this.prezzo=prezzo;}
	public void setQuantita(int quantita) {this.quantita=quantita;}
	public void setNome(String nome) {this.nome=nome;}
	public void setDescrizione(String descrizione) {this.descrizione=descrizione;}
	public void setMarca(String marca) {this.marca=marca;}
	public void setAttivo(boolean attivo) {this.attivo=attivo;}
	public void setImmagine(String immagine) {this.immagine=immagine;}
	public void setConteggio(int conteggio) {this.conteggio=conteggio;}
	
	public boolean equals(Object o) {
		if(o == null) return false;
		if(!(o instanceof Prodotto)) return false;
		Prodotto temp=(Prodotto)o;
		return 	this.id==temp.id &&
				this.codice.equals(temp.codice) && 
				this.prezzo==temp.prezzo &&
				this.nome.equals(temp.nome) &&
				this.descrizione.equals(temp.descrizione) &&
				this.marca.equals(temp.marca) && 
				this.attivo == temp.attivo;
	}
	
	public int hashCode() {
		int hash=0;
		hash+=id;
		hash+=codice.length();
		hash+=(int)prezzo;
		hash+=quantita;
		hash+=nome.length();
		hash+=descrizione.length();
		hash+=marca.length();
		return hash;
	}
	
	public String toString() {
		return 	"Prodotto " +
				this.id + " " +
				this.codice + " " +
				this.prezzo + " " +
				this.quantita + " " +
				this.nome + " " +
				this.marca + " " +
				(this.attivo ? "attivo" : "inattivo") + " " +
				this.immagine;
	}
	
	public void clone(Prodotto prodotto) {
		this.id=prodotto.getId();
		this.codice=prodotto.getCodice();
		this.prezzo=prodotto.getPrezzo();
		this.quantita=prodotto.getQuantita();
		this.nome=prodotto.getNome();
		this.descrizione=prodotto.getDescrizione();
		this.marca=prodotto.getMarca();
		this.attivo=prodotto.isAttivo();
		this.immagine=prodotto.getImmagine();
		this.conteggio=prodotto.getConteggio();
	}
	
	public void increment() {
		this.conteggio++;
	}
	
	public void decrement() {
		this.conteggio--;
	}
}
