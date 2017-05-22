package florian_haas.lucas.business;

import java.time.LocalDate;
import java.util.Set;

import javax.ejb.Local;

import florian_haas.lucas.model.*;
import florian_haas.lucas.validation.ValidEntityId;

@Local
public interface IdCardBeanLocal {

	public ReadOnlyIdCard findIdCardById(@ValidEntityId(entityClass = ReadOnlyIdCard.class) Long idCardId);

	public Set<? extends ReadOnlyIdCard> getIdCards(@ValidEntityId(entityClass = ReadOnlyAccountOwner.class) Long accountOwnerId);

	public Long addIdCard(@ValidEntityId(entityClass = ReadOnlyAccountOwner.class) Long accountOwnerId);

	public Boolean removeIdCard(@ValidEntityId(entityClass = ReadOnlyIdCard.class) Long idCardId);

	public Boolean blockIdCard(@ValidEntityId(entityClass = ReadOnlyIdCard.class) Long idCardId);

	public Boolean unblockIdCard(@ValidEntityId(entityClass = ReadOnlyIdCard.class) Long idCardId);

	public Boolean setValidDate(@ValidEntityId(entityClass = ReadOnlyIdCard.class) Long idCardId, LocalDate validDate);

}
