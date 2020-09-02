package models;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import enums.Ruolo;

@Entity
@Table(name="utenti")
public class Utente implements Serializable{
	
	private static final long serialVersionUID = -7972596075974693456L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="nome",nullable=false,length=50)
	private String nome;
	
	@Column(name="cognome",nullable=false,length=50)
	private String cognome;
	
	@Column(name="username",nullable=false,length=50,unique=true)
	private String username;
	
	@Column(name="password",nullable=false,length=100)
	private String password;
	
	@Column(name="email",nullable=false,length=255,unique=true)
	private String email;
	
	@Column(name="ruolo",nullable=false)
	@Enumerated(EnumType.STRING)
	private Ruolo ruolo;
	
	@Column(name="data_registrazione", updatable=false)
	private String data;
	
	@Column(name="token",nullable=true,length=32)
	private String token;
	
	public int getId() {return this.id;}
	public String getNome() {return this.nome;}
	public String getCognome() {return this.cognome;}
	public String getUsername() {return this.username;}
	public String getPassword() {return this.password;}
	public String getEmail() {return this.email;}
	public Ruolo getRuolo() {return this.ruolo;}
	public String getData() {return this.data;}
	public String getToken() {return this.token;}
	
	public void setId(int id) {this.id=id;}
	public void setNome(String nome) {this.nome=nome;}
	public void setCognome(String cognome) {this.cognome=cognome;}
	public void setUsername(String username) {this.username=username;}
	public void setPassword(String password) {this.password=password;}
	public void setEmail(String email) {this.email=email;}
	public void setRuolo(Ruolo ruolo) {this.ruolo=ruolo;}
	public void setData(String data) {this.data=data;}
	public void setToken(String token) {this.token=token;}
	
	public boolean equals(Object o) {
		if(o == null) return false;
		if(!(o instanceof Utente)) return false;
		Utente temp=(Utente)o;
		return 	this.id==temp.id &&
				this.nome.equals(temp.nome) &&
				this.cognome.equals(temp.cognome) && 
				this.username.equals(temp.username) &&
				this.password.equals(temp.password) &&
				this.email.equals(temp.email) && 
				this.ruolo.toString().equals(temp.ruolo.toString()) &&
				this.data.equals(temp.data) &&
				this.token.equals(temp.token);
	}
	
	public int hashCode() {
		int hash=0;
		hash+=id;
		hash+=this.nome.length();
		hash+=this.cognome.length();
		hash+=this.username.length();
		hash+=this.password.length();
		hash+=this.email.length();
		return hash;
	}
	
	public String toString() {
		return 	"Utente: " +
				this.id + " "+
				this.nome + " " +
				this.cognome + " " +
				this.username + " " +
				this.password + " " +
				this.email + " " +
				this.ruolo.toString() + " " +
				this.data.toString() + " " +
				this.token;
	}
	
	public String generateToken() {
		SecureRandom secure=new SecureRandom();
		Encoder encoder = Base64.getUrlEncoder();
		byte[] random = new byte[24];
		secure.nextBytes(random);
		return encoder.encodeToString(random);
	}
	
	public String hashPassword(String password) {
		StringBuilder hexString=null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			BigInteger number = new BigInteger(1,md.digest(password.getBytes(StandardCharsets.UTF_8)));
			hexString = new StringBuilder(number.toString(16));
			while(hexString.length() <32 ) {
				hexString.insert(0,'0');
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hexString.toString();
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
	
	public Utente clone() {
		Utente temp=new Utente();
		temp.setNome(this.nome);
		temp.setId(this.id);
		temp.setCognome(this.cognome);
		temp.setUsername(this.username);
		temp.setPassword(this.password);
		temp.setData(this.data);
		temp.setEmail(this.email);
		temp.setRuolo(this.ruolo);
		temp.setToken(this.token);
		return temp;
	}
}
