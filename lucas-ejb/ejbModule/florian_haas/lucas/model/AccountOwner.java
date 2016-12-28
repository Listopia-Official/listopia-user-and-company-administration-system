package florian_haas.lucas.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "disc")
public abstract class AccountOwner extends EntityBase {

	private static final long serialVersionUID = -2651775692956853862L;

	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	@JoinColumn(nullable = false)
	private Account account;

	protected AccountOwner() {
		account = new Account(this);
	}

	public Account getAccount() {
		return account;
	}

	public abstract EnumAccountOwnerType getOwnerType();

}
