package florian_haas.lucas.business;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.model.EntityBase;

@Local
public interface EntityBeanLocal {
	public Boolean exists(@NotNull Long id, @NotNull Class<? extends EntityBase> entityClass);

	public <T> Boolean isUnique(String columnName, T value, @NotNull Class<? extends EntityBase> entityClass);
}
