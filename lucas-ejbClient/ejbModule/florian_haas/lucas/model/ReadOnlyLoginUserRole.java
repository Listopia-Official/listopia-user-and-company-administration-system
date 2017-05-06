package florian_haas.lucas.model;

import java.util.Set;

public interface ReadOnlyLoginUserRole extends ReadOnlyEntity {

	public String getName();

	public Set<String> getPermissions();

}
