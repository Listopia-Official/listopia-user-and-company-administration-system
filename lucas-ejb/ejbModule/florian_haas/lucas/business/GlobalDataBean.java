package florian_haas.lucas.business;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.executable.*;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.*;
import florian_haas.lucas.util.*;

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
	@RequiresPermissions({
			"globalData:getSalaries" })
	public Map<EnumSalaryClass, BigDecimal> getSalaries() {
		return newInstance().getSalaries();
	}

	@Override
	@RequiresPermissions({
			"globalData:getMinTimePresent" })
	public Long getMinTimePresent() {
		return newInstance().getMinTimePresent();
	}

	@Override
	@RequiresPermissions({
			"globalData:getMinimumWage" })
	public BigDecimal getMinimumWage() {
		return newInstance().getMinimumWage();
	}

	@Override
	@RequiresPermissions({
			"globalData:setSalary" })
	public Boolean setSalary(EnumSalaryClass salaryClass, BigDecimal salary) {
		GlobalData newInstance = newInstance();
		if (Utils.isEqual(newInstance.getSalaries().get(salaryClass), salary)) return Boolean.FALSE;
		newInstance.setSalary(salaryClass, salary);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions({
			"globalData:setMinTimePresent" })
	public Boolean setMinTimePresent(Long minTimePresent) {
		GlobalData newInstance = newInstance();
		if (newInstance.getMinTimePresent().equals(minTimePresent)) return Boolean.FALSE;
		newInstance.setMinTimePresent(minTimePresent);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions({
			"globalData:setMinimumWage" })
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
	@RequiresPermissions({
			"globalData:getWarehouse" })
	public Company getWarehouse() {
		return newInstance().getWarehouse();
	}

	@Override
	@RequiresPermissions({
			"globalData:setWarehouse" })
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
	@RequiresPermissions({
			"globalData:getInstance" })
	public GlobalData getInstance() {
		return newInstance();
	}

	@Override
	@RequiresPermissions({
			"globalData:getTransactionLimit" })
	public BigDecimal getTransactionLimit() {
		return newInstance().getTransactionLimit();
	}

	@Override
	@RequiresPermissions({
			"globalData:setTransactionLimit" })
	public Boolean setTransactionLimit(BigDecimal transactionLimit) {
		GlobalData newInstance = newInstance();
		if (Utils.isEqual(newInstance.getTransactionLimit(), transactionLimit)) return Boolean.FALSE;
		newInstance.setTransactionLimit(transactionLimit);
		return Boolean.TRUE;
	}

}
