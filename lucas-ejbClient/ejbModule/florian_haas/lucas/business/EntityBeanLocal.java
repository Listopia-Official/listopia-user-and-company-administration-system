package florian_haas.lucas.business;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.model.ReadOnlyEntity;

@Local
public interface EntityBeanLocal {
	public Boolean exists(@NotNull Long id, @NotNull Class<? extends ReadOnlyEntity> entityClass);
}
