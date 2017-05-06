package florian_haas.lucas.model;

import java.time.LocalDate;

public interface ReadOnlyIdCard extends ReadOnlyEntity {

	public Boolean getBlocked();

	public LocalDate getValidDay();

}
