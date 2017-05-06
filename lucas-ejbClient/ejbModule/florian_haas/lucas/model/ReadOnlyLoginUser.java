package florian_haas.lucas.model;

import java.util.Set;

public interface ReadOnlyLoginUser extends ReadOnlyEntity {

	public String getUsername();

	public ReadOnlyUser getUser();

	public Set<ReadOnlyLoginUserRole> getRoles();

	public String getUiTheme();
}
