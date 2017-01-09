package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.executable.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.MinimumWage;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
public class GlobalDataBean implements GlobalDataBeanLocal {

	@JPADAO
	@Inject
	private GlobalDataDAO globalDataDao;

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
	public void setSalary(EnumSalaryClass salaryClass, @MinimumWage BigDecimal salary) {
		newInstance().setSalary(salaryClass, salary);
	}

	@Override
	public void setMinTimePresent(Long minTimePresent) {
		newInstance().setMinTimePresent(minTimePresent);
	}

	@Override
	public void setMinimumWage(BigDecimal minimumWage) {
		newInstance().setMinimumWage(minimumWage);
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

}
