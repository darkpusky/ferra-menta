package view.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import enums.Pagine;

@Named
@SessionScoped
public class MainBean implements Serializable{

	private static final long serialVersionUID = -6456432649638532944L;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private HomeBean homeBean;
	
	private Pagine pagina;
	
	@PostConstruct
	public void init() {
		this.setPagina(Pagine.ACCESSO);
	}
	
	public void logout(){
		this.loginBean.getUtenteLogin().setToken(null);
		this.homeBean.modificaAccount();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
		
	}
	
	public Pagine getPagina() {return this.pagina;}
	public LoginBean getLoginBean() {return this.loginBean;}
	public HomeBean getHomeBean() {return this.homeBean;}
	
	public void setPagina(Pagine pagina) {this.pagina=pagina;}
	public void setLoginBean(LoginBean loginBean) {this.loginBean=loginBean;}
	public void setHomeBean(HomeBean homeBean) {this.homeBean=homeBean;}
	
	
}
