import java.sql.Timestamp;
import java.util.LinkedHashSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ejb.EjbAdmin;
import ejb.EjbClient;
import enums.Ruolo;
import models.Utente;


@Stateless
@Path("/utenti")
public class ServiceUtente {
	
	@EJB
	private EjbAdmin admin;
	
	@EJB
	private EjbClient client;
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/put")
	public void inserisciUtenteRegistrazione(Utente utente) {
		utente.setRuolo(Ruolo.USER);
		this.client.insertUtente(utente);
	}
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Utente riceviUtenteLogin(@QueryParam("username") String username) {
		Utente utente = new Utente();
		utente=this.client.getUtenteUsername(username);
		return utente;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/post")
	public void modificaUtente(Utente utente) {
		this.client.updateUtente(utente);
	}
	
	@DELETE
	@Path("/delete")
	public void eliminaUtente(@QueryParam("username")String username) {
		this.client.removeUtente(username);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAll")
	public LinkedHashSet<Utente> riceviUtenti() {
		return admin.getUtenti();
	}
	
}
