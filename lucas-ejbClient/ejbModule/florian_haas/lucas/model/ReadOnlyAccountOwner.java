package florian_haas.lucas.model;

import java.util.Set;

public interface ReadOnlyAccountOwner extends ReadOnlyEntity {

	public ReadOnlyAccount getAccount();

	public EnumAccountOwnerType getOwnerType();

	public Set<? extends ReadOnlyIdCard> getIdCards();

}
