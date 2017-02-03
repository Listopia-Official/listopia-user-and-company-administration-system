package florian_haas.lucas.web;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import florian_haas.lucas.business.*;

@Named
@ViewScoped
public class TestBean implements Serializable {

	private static final long serialVersionUID = -8272863330457069304L;

	private int count = 0;

	@EJB
	private UserBeanLocal userBean;

	@EJB
	private LoginBeanLocal loginBean;

	public void newUser() {
		// for (int i = 0; i < 800; i++) {
		// userBean.createPupil(Double.toString(Math.random()), Double.toString(Math.random()),
		// (int) Math.random() + 1,
		// Double.toString(Math.random()), null);
		// }
		// for (int i = 0; i < 100; i++) {
		// userBean.createTeacher(Double.toString(Math.random()), Double.toString(Math.random()),
		// null);
		//
		// }
		// for (int i = 0; i < 250; i++) {
		// userBean.createGuest();
		// }
	}

	public String logout() {
		loginBean.logout();
		return "login.xhtml";
	}

	public int getCount() {
		count = userBean.findAll().size();
		return count;
	}

}
