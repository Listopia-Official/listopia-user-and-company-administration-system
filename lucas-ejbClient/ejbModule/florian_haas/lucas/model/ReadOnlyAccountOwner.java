package florian_haas.lucas.model;

public interface ReadOnlyAccountOwner extends ReadOnlyEntity {

	public ReadOnlyAccount getAccount();

	public EnumAccountOwnerType getOwnerType();

}
