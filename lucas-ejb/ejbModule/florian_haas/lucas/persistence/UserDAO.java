package florian_haas.lucas.persistence;

import java.util.*;

import florian_haas.lucas.model.*;

public interface UserDAO extends DAO<User> {

	public byte[] getImageFromId(Long userId);

	public List<User> findUsers(Long userId, String forename, String surname, List<EnumSchoolClass> schoolClasses, EnumUserType userType,
			List<String> ranks, Integer employmentsCount, Boolean useUserId, Boolean useForename, Boolean useSurname, Boolean useSchoolClass,
			Boolean useUserType, Boolean useRanks, Boolean useEmploymentsCount, EnumQueryComparator userIdComparator,
			EnumQueryComparator forenameComparator, EnumQueryComparator surnameComparator, EnumQueryComparator searchUserTypeComparator,
			EnumQueryComparator ranksComparator, EnumQueryComparator employmentsCountComparator);

	public List<User> getAllUsersWithNoEmployments(EnumSet<EnumUserType> permittedUserTypes);

	public void clearJobWishes(Long jobId);

	public List<User> getUsersFromData(String data, Integer resultsCount);

}
