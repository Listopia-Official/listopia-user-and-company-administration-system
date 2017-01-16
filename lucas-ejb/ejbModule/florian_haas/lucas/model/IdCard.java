package florian_haas.lucas.model;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class IdCard extends EntityBase {

	private static final long serialVersionUID = 8810801313974710266L;

	@Column(nullable = false)
	@Basic(optional = false)
	@NotNull
	private Boolean blocked = Boolean.FALSE;

	IdCard() {}

	private LocalDate validDay;

	public Boolean getBlocked() {
		return this.blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public LocalDate getValidDay() {
		return validDay;
	}

	public void setValidDay(LocalDate validDay) {
		this.validDay = validDay;
	}

}
