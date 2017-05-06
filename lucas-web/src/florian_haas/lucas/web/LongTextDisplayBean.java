package florian_haas.lucas.web;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class LongTextDisplayBean implements Serializable {

	private static final long serialVersionUID = 6205923333054223899L;

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
