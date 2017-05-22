package florian_haas.lucas.persistence;

import java.util.List;

import florian_haas.lucas.model.AccountOwner;

public interface AccountOwnerDAO extends ReadOnlyDAO<AccountOwner> {

	public List<AccountOwner> getAccountOwnersFromData(String data, Integer resultsCount);

}
