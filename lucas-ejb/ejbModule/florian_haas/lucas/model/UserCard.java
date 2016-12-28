package florian_haas.lucas.model;

import javax.persistence.*;

@Entity
public class UserCard extends EntityBase {

	private static final long serialVersionUID = -4981744051982144780L;

	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;

	@Column(nullable = false)
	@Basic(optional = false)
	private Boolean blocked = Boolean.FALSE;

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

}
