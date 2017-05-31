package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.util.*;

public interface ReadOnlyGlobalData extends ReadOnlyEntity {

	public Map<EnumSalaryClass, BigDecimal> getSalaries();

	public Long getMinTimePresent();

	public BigDecimal getMinimumWage();

	public ReadOnlyCompany getWarehouse();

	public BigDecimal getTransactionLimit();

	public String getCurrencySymbol();

	public Long getMaxIdleTime();

	public Integer getMaxUserImageWidth();

	public Integer getMaxUserImageHeight();

	public String getDefaultUITheme();

	public List<String> getUiThemes();

	public Long getMaxUserImageUploadSizeByte();

	public Integer getCivilCompanyOptimalManagerCount();

	public Integer getMinCivilManagerSchoolGrade();

	public BigDecimal getRealMoneyCount();

	public BigDecimal getMoneyInCirculation();

	public BigDecimal getRateOfExchange();

	public BigDecimal getRateOfBackExchange();

	public String getRealCurrencySymbol();

}
