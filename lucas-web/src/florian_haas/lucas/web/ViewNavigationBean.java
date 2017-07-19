package florian_haas.lucas.web;

import java.io.Serializable;
import java.util.*;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class ViewNavigationBean implements Serializable {

	private static final long serialVersionUID = 8755803478762699531L;

	private Map<String, List<Long>> ids = new HashMap<>();

	public void setIds(String viewName, List<Long> ids) {
		this.ids.put(viewName, ids);
	}

	public List<Long> getIds(String viewName) {
		return this.ids.remove(viewName);
	}

}
