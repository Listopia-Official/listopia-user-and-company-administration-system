package florian_haas.lucas.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class UserCard extends IdCard implements ReadOnlyUserCard {

	private static final long serialVersionUID = -4981744051982144780L;

	@ManyToOne
	@JoinColumn(nullable = false)
	@NotNull
	private User user;

	UserCard() {}

	public UserCard(User user) {
		this.user = user;
	}

	@Override
	public User getUser() {
		return this.user;
	}
}
