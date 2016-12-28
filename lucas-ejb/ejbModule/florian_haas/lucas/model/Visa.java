package florian_haas.lucas.model;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class Visa extends EntityBase {

	private static final long serialVersionUID = 6698272406574057040L;

	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	@Column(nullable = false)
	@Basic(optional = false)
	private Boolean activated = Boolean.FALSE;

	private LocalDate validDay;

	Visa() {}

	public Visa(User user) {
		this.user = user;
	}

	public Boolean getActivated() {
		return this.activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public LocalDate getValidDay() {
		return this.validDay;
	}

	public void setValidDay(LocalDate validDay) {
		this.validDay = validDay;
	}

	public User getUser() {
		return this.user;
	}
}
