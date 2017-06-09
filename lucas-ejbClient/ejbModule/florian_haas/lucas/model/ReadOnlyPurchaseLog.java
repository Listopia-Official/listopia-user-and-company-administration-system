package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ReadOnlyPurchaseLog extends ReadOnlyEntity {

	public ReadOnlyCompany getCompany();

	public LocalDateTime getDateTime();

	public ReadOnlyItem getItem();

	public EnumPayType getPayType();

	public Integer getCount();

	public BigDecimal getCurrentFictionalPrice();

	public BigDecimal getCurrentRealPrice();

	public BigDecimal getTotalFictionalPrice();

	public BigDecimal getTotalRealPrice();

}
