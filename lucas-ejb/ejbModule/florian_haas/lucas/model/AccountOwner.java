package florian_haas.lucas.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "disc")
public abstract class AccountOwner extends EntityBase implements ReadOnlyAccountOwner {

	private static final long serialVersionUID = -2651775692956853862L;

	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	@JoinColumn(nullable = false)
	@NotNull
	private Account account;

	protected AccountOwner() {
		account = new Account(this);
	}

	@Override
	public Account getAccount() {
		return account;
	}

	public abstract EnumAccountOwnerType getOwnerType();

}
