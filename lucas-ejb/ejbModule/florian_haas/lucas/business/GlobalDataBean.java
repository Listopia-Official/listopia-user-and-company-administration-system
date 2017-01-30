package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.executable.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.security.*;
import florian_haas.lucas.util.Utils;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class GlobalDataBean implements GlobalDataBeanLocal {

	@JPADAO
	@Inject
	private GlobalDataDAO globalDataDao;

	@EJB
	private CompanyBeanLocal companyBean;

	@Override
	@RequiresPermissions(GLOBAL_DATA_GET_SALARIES)
	public Map<EnumSalaryClass, BigDecimal> getSalaries() {
		return newInstance().getSalaries();
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_GET_MIN_TIME_PRESENT)
	public Long getMinTimePresent() {
		return newInstance().getMinTimePresent();
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_GET_MINIMUM_WAGE)
	public BigDecimal getMinimumWage() {
		return newInstance().getMinimumWage();
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_SET_SALARY)
	public Boolean setSalary(EnumSalaryClass salaryClass, BigDecimal salary) {
		GlobalData newInstance = newInstance();
		if (Utils.isEqual(newInstance.getSalaries().get(salaryClass), salary)) return Boolean.FALSE;
		newInstance.setSalary(salaryClass, salary);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_SET_MIN_TIME_PRESENT)
	public Boolean setMinTimePresent(Long minTimePresent) {
		GlobalData newInstance = newInstance();
		if (newInstance.getMinTimePresent().equals(minTimePresent)) return Boolean.FALSE;
		newInstance.setMinTimePresent(minTimePresent);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_SET_MINIMUM_WAGE)
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
	@RequiresPermissions(GLOBAL_DATA_GET_WAREHOUSE)
	public Company getWarehouse() {
		return newInstance().getWarehouse();
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_SET_WAREHOUSE)
	public Boolean setWarehouse(Long companyId) {
		Company existingCompany = newInstance().getWarehouse();
		Company company = companyBean.findById(companyId);
		if (company.equals(existingCompany)) {
			return Boolean.FALSE;
		} else {
			newInstance().setWarehouse(company);
			return Boolean.TRUE;
		}
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_GET_INSTANCE)
	public GlobalData getInstance() {
		return newInstance();
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_GET_TRANSACTION_LIMIT)
	public BigDecimal getTransactionLimit() {
		return newInstance().getTransactionLimit();
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_SET_TRANSACTION_LIMIT)
	public Boolean setTransactionLimit(BigDecimal transactionLimit) {
		GlobalData newInstance = newInstance();
		if (Utils.isEqual(newInstance.getTransactionLimit(), transactionLimit)) return Boolean.FALSE;
		newInstance.setTransactionLimit(transactionLimit);
		return Boolean.TRUE;
	}

}
