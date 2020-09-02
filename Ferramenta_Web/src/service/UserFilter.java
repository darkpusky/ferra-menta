package service;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omnifaces.filter.HttpFilter;
import org.omnifaces.util.Servlets;

import enums.Ruolo;
import view.beans.LoginBean;

@WebFilter("/home.xhtml")
public class UserFilter extends HttpFilter{

	@Inject 
	LoginBean loginBean;
	
	public void doFilter(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			FilterChain chain) throws ServletException, IOException {

		if (session != null && this.loginBean.getUtenteLogin() !=null  && this.loginBean.getUtenteLogin().getToken() != null &&  this.loginBean.getUtenteLogin().getRuolo() != Ruolo.BANNED ) {
			chain.doFilter(request, response);
		} else {
			Servlets.facesRedirect(request, response, "accesso.xhtml");
			//TODO cosa succede se l'utente è bannato
		}

	}
	
}
