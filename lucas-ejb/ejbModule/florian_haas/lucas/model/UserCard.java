package florian_haas.lucas.model;

public class UserCard extends EntityBase {

	private static final long serialVersionUID = -4981744051982144780L;

	private User user;
	private Boolean blocked = Boolean.FALSE;

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
