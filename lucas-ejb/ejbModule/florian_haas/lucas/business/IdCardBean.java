package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.time.LocalDate;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.executable.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.*;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class IdCardBean implements IdCardBeanLocal {

	@Inject
	@JPADAO
	private AccountOwnerDAO accountOwnerDao;

	@Inject
	@JPADAO
	private IdCardDAO idCardDao;

	@Override
	@RequiresPermissions(ID_CARD_GET_FROM_OWNER)
	public Set<? extends ReadOnlyIdCard> getIdCards(Long accountOwnerId) {
		return accountOwnerDao.findById(accountOwnerId).getIdCards();
	}

	@Override
	@RequiresPermissions(ID_CARD_ADD)
	public Long addIdCard(Long accountOwnerId) {
		AccountOwner owner = accountOwnerDao.findById(accountOwnerId);
		IdCard idCard = new IdCard(owner);
		idCardDao.persist(idCard);
		owner.addIdCard(idCard);
		return idCard.getId();
	}

	@Override
	@RequiresPermissions(ID_CARD_REMOVE)
	public Boolean removeIdCard(Long idCardId) {
		IdCard idCard = idCardDao.findById(idCardId);
		Boolean removed = idCard.getOwner().removeIdCard(idCard);
		if (removed) {
			idCardDao.delete(idCard);
		}
		return removed;
	}

	@Override
	@RequiresPermissions(ID_CARD_BLOCK)
	public Boolean blockIdCard(Long idCardId) {
		IdCard idCard = idCardDao.findById(idCardId);
		if (idCard.getBlocked()) return Boolean.FALSE;
		idCard.setBlocked(Boolean.TRUE);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ID_CARD_UNBLOCK)
	public Boolean unblockIdCard(Long idCardId) {
		IdCard idCard = idCardDao.findById(idCardId);
		if (!idCard.getBlocked()) return Boolean.FALSE;
		idCard.setBlocked(Boolean.FALSE);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ID_CARD_SET_VALID_DATE)
	public Boolean setValidDate(Long idCardId, LocalDate validDate) {
		IdCard idCard = idCardDao.findById(idCardId);
		if (idCard.getValidDay() != null && idCard.getValidDay().equals(validDate) || idCard.getValidDay() == null && validDate == null)
			return Boolean.FALSE;
		idCard.setValidDay(validDate);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(ID_CARD_FIND_BY_ID)
	public ReadOnlyIdCard findIdCardById(Long idCardId) {
		return idCardDao.findById(idCardId);
	}

}
