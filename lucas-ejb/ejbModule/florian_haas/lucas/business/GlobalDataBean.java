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

	@Override
	@RequiresPermissions(GLOBAL_DATA_GET_CURRENCY_SYMBOL)
	public String getCurrencySymbol() {
		return newInstance().getCurrencySymbol();
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_SET_CURRENCY_SYMBOL)
	public Boolean setCurrencySymbol(String currencySymbol) {
		GlobalData newInstance = newInstance();
		if (newInstance.getCurrencySymbol().equals(currencySymbol)) return Boolean.FALSE;
		newInstance.setCurrencySymbol(currencySymbol);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_GET_MAX_IDLE_TIME)
	public Long getMaxIdleTime() {
		return newInstance().getMaxIdleTime();
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_SET_MAX_IDLE_TIME)
	public Boolean setMaxIdleTime(Long maxIdleTime) {
		GlobalData newInstance = newInstance();
		if (newInstance.getMaxIdleTime().equals(maxIdleTime)) return Boolean.FALSE;
		newInstance.setMaxIdleTime(maxIdleTime);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_SET_MAX_USER_IMAGE_WIDTH)
	public Boolean setMaxUserImageWidth(Integer maxUserImageWidth) {
		GlobalData newInstance = newInstance();
		if (newInstance.getMaxUserImageWidth().equals(maxUserImageWidth)) return Boolean.FALSE;
		newInstance.setMaxUserImageWidth(maxUserImageWidth);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_SET_MAX_USER_IMAGE_HEIGHT)
	public Boolean setMaxUserImageHeight(Integer maxUserImageHeight) {
		GlobalData newInstance = newInstance();
		if (newInstance.getMaxUserImageHeight().equals(maxUserImageHeight)) return Boolean.FALSE;
		newInstance.setMaxUserImageHeight(maxUserImageHeight);
		return Boolean.TRUE;
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_GET_MAX_USER_IMAGE_WIDTH)
	public Integer getMaxUserImageWidth() {
		return newInstance().getMaxUserImageWidth();
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_GET_MAX_USER_IMAGE_HEIGHT)
	public Integer getMaxUserImageHeight() {
		return newInstance().getMaxUserImageHeight();
	}

	@Override
	public String getDefaultUITheme() {
		return newInstance().getDefaultUITheme();
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_GET_UI_THEMES)
	public List<String> getUIThemes() {
		return newInstance().getUiThemes();
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_SET_DEFAULT_UI_THEME)
	public Boolean setDefaultUITheme(String uiTheme) {
		GlobalData newInstance = newInstance();
		if (!newInstance.getDefaultUITheme().equals(uiTheme)) {
			newInstance.setDefaultUITheme(uiTheme);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_ADD_UI_THEME)
	public Boolean addUITheme(String uiTheme) {
		return newInstance().addUITheme(uiTheme);
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_REMOVE_UI_THEME)
	public Boolean removeUITheme(String uiTheme) {
		return newInstance().removeUITheme(uiTheme);
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_GET_MAX_USER_IMAGE_UPLOAD_SIZE_BYTES)
	public Long getMaxUserImageUploadSizeBytes() {
		return newInstance().getMaxUserImageUploadSizeByte();
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_SET_MAX_USER_IMAGE_UPLOAD_SIZE_BYTES)
	public Boolean setMaxUserImageUploadSizeBytes(Long maxUserImageUploadSizeBytes) {
		GlobalData newInstance = newInstance();
		if (!newInstance.getMaxUserImageUploadSizeByte().equals(maxUserImageUploadSizeBytes)) {
			newInstance.setMaxUserImageUploadSizeByte(maxUserImageUploadSizeBytes);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_GET_OPTIMAL_CIVIL_MANAGER_COUNT)
	public Integer getOptimalCivilManagerCount() {
		return newInstance().getCivilCompanyOptimalManagerCount();
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_GET_MIN_CIVIL_MANAGER_SCHOOL_GRADE)
	public Integer getMinCivilManagerSchoolGrade() {
		return newInstance().getMinCivilManagerSchoolGrade();
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_SET_OPTIMAL_CIVIL_MANAGER_COUNT)
	public Boolean setOptimalCivilManagerCount(Integer optimalCivilManagerCount) {
		GlobalData newInstance = newInstance();
		if (!newInstance.getCivilCompanyOptimalManagerCount().equals(optimalCivilManagerCount)) {
			newInstance.setCivilCompanyOptimalManagerCount(optimalCivilManagerCount);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	@RequiresPermissions(GLOBAL_DATA_SET_MIN_CIVIL_MANAGER_SCHOOL_GRADE)
	public Boolean setMinCivilManagerSchoolGrade(Integer minCivilManagerSchoolGrade) {
		GlobalData newInstance = newInstance();
		if (!newInstance.getMinCivilManagerSchoolGrade().equals(minCivilManagerSchoolGrade)) {
			newInstance.setMinCivilManagerSchoolGrade(minCivilManagerSchoolGrade);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
