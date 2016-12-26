package florian_haas.lucas.model;

public abstract class AccountOwner extends EntityBase {

	private static final long serialVersionUID = -2651775692956853862L;

	private Account account;

	protected AccountOwner() {
		account = new Account(this);
	}

	public Account getAccount() {
		return account;
	}

	public abstract EnumAccountOwnerType getOwnerType();

}
