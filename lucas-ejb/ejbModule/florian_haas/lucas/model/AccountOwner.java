package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.validation.TypeNotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "disc")
public abstract class AccountOwner extends EntityBase implements ReadOnlyAccountOwner {

	private static final long serialVersionUID = -2651775692956853862L;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "owner")
	@Valid
	@NotNull
	private Set<@TypeNotNull IdCard> idCards = new HashSet<>();

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

	public Set<IdCard> getIdCards() {
		return Collections.unmodifiableSet(this.idCards);
	}

	public Boolean addIdCard(IdCard idCard) {
		return this.idCards.add(idCard);
	}

	public Boolean removeIdCard(IdCard idCard) {
		return this.idCards.remove(idCard);
	}

	public abstract EnumAccountOwnerType getOwnerType();

}
