package florian_haas.lucas.model;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class UserCard extends EntityBase {

	private static final long serialVersionUID = -4981744051982144780L;

	@ManyToOne
	@JoinColumn(nullable = false)
	@NotNull
	private User user;

	@Column(nullable = false)
	@Basic(optional = false)
	@NotNull
	private Boolean blocked = Boolean.FALSE;

	private LocalDate validDay;

	UserCard() {}

	public UserCard(User user) {
		this.user = user;
	}

	public Boolean getBlocked() {
		return this.blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public User getUser() {
		return this.user;
	}

	public LocalDate getValidDay() {
		return validDay;
	}

	public void setValidDay(LocalDate validDay) {
		this.validDay = validDay;
	}

}
