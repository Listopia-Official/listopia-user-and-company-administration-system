package florian_haas.lucas.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ReadOnlyTaxdata extends ReadOnlyEntity {

	public ReadOnlyCompany getCompany();

	public LocalDate getDate();

	public BigDecimal getIncomings();

	public BigDecimal getOutgoings();

	public BigDecimal getBalance();

}
