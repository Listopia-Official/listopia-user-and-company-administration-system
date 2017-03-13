package florian_haas.lucas.util;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import florian_haas.lucas.business.EntityBeanLocal;
import florian_haas.lucas.model.EntityBase;

@Named
@RequestScoped
public class EntityBean {

	@EJB
	private EntityBeanLocal entityBean;

	public <T extends EntityBase> Boolean exists(Long id, Class<T> entityClass) {
		return entityBean.exists(id, entityClass);
	}

}
