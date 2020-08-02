package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="utenti")
public class Utente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="nome",nullable=false)
	private String nome;
	
	@Column(name="cognome",nullable=false)
	private String cognome;
	
	public int getId() {return this.id;}
	public String getNome() {return this.nome;}
	public String getCognome() {return this.cognome;}
	
	public void setId(int id) {this.id=id;}
	public void setNome(String nome) {this.nome=nome;}
	public void setCognome(String cognome) {this.cognome=cognome;}
	
	
}
