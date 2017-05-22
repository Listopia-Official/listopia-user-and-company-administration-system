package florian_haas.lucas.model;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "idcard")
public class IdCard extends EntityBase implements ReadOnlyIdCard {

	private static final long serialVersionUID = 8810801313974710266L;

	@ManyToOne
	@JoinColumn(nullable = false)
	@NotNull
	private AccountOwner owner;

	@Column(nullable = false)
	@Basic(optional = false)
	@NotNull
	private Boolean blocked = Boolean.FALSE;

	IdCard() {}

	public IdCard(AccountOwner owner) {
		this.owner = owner;
	}

	private LocalDate validDay;

	@Override
	public AccountOwner getOwner() {
		return owner;
	}

	@Override
	public Boolean getBlocked() {
		return this.blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	@Override
	public LocalDate getValidDay() {
		return this.validDay;
	}

	public void setValidDay(LocalDate validDay) {
		this.validDay = validDay;
	}

}
