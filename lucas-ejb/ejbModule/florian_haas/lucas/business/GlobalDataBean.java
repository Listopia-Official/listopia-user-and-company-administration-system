package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.executable.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.*;
import florian_haas.lucas.util.Utils;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
public class GlobalDataBean implements GlobalDataBeanLocal {

	@JPADAO
	@Inject
	private GlobalDataDAO globalDataDao;

	@JPADAO
	@Inject
	private CompanyDAO companyDao;

	@Override
	public Map<EnumSalaryClass, BigDecimal> getSalaries() {
		return newInstance().getSalaries();
	}

	@Override
	public Long getMinTimePresent() {
		return newInstance().getMinTimePresent();
	}

	@Override
	public BigDecimal getMinimumWage() {
		return newInstance().getMinimumWage();
	}

	@Override
	public Boolean setSalary(EnumSalaryClass salaryClass, @MinimumWage BigDecimal salary) {
		GlobalData newInstance = newInstance();
		if (Utils.isEqual(newInstance.getSalaries().get(salaryClass), salary)) return Boolean.FALSE;
		newInstance.setSalary(salaryClass, salary);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setMinTimePresent(Long minTimePresent) {
		GlobalData newInstance = newInstance();
		if (newInstance.getMinTimePresent().equals(minTimePresent)) return Boolean.FALSE;
		newInstance.setMinTimePresent(minTimePresent);
		return Boolean.TRUE;
	}

	@Override
	public Boolean setMinimumWage(BigDecimal minimumWage) {
		GlobalData newInstance = newInstance();
		if (Utils.isEqual(newInstance.getMinimumWage(), minimumWage)) return Boolean.FALSE;
		newInstance.setMinimumWage(minimumWage);
		return Boolean.TRUE;
	}

	private GlobalData newInstance() {
		GlobalData ret = null;
		List<GlobalData> globalData = globalDataDao.findAll();
		if (globalData.isEmpty()) {
			ret = new GlobalData();
			globalDataDao.persist(ret);
		} else {
			ret = globalData.get(0);
		}
		return ret;
	}

	@Override
	public Company getWarehouse() {
		return newInstance().getWarehouse();
	}

	@Override
	public Boolean setWarehouse(@ValidEntityId(entityClass = Company.class) Long companyId) {
		Company existingCompany = newInstance().getWarehouse();
		Company company = companyDao.findById(companyId);
		if (company.equals(existingCompany)) {
			return Boolean.FALSE;
		} else {
			newInstance().setWarehouse(company);
			return Boolean.TRUE;
		}
	}

	@Override
	public GlobalData getInstance() {
		return newInstance();
	}

}
