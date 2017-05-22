package florian_haas.lucas.model;

import java.time.LocalDate;

public interface ReadOnlyIdCard extends ReadOnlyEntity {

	public ReadOnlyAccountOwner getOwner();

	public Boolean getBlocked();

	public LocalDate getValidDay();

}
