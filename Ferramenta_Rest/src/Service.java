import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ejb.Ejb;
import models.Utente;

@Stateless
@Path("/utente")
public class Service {
	
	@EJB
	private Ejb ejb;
	
	@PUT
	@Path("/metti")
	public void inserisciUtente() {
		ejb.inserisciUtente();
	}
	
	@GET
	@Path("/ricevi")
	@Produces(MediaType.APPLICATION_JSON)
	public Utente riceviUtente() {
		return ejb.riceviUtente();
	}
	
	
}
