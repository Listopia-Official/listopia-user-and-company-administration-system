package florian_haas.lucas.persistence;

import java.util.*;

import florian_haas.lucas.model.*;

public interface UserDAO extends DAO<User> {

	public byte[] getImageFromId(Long userId);

	public List<User> findUsers(Long userId, String forename, String surname, List<EnumSchoolClass> schoolClasses, EnumUserType userType,
			List<String> ranks, Boolean useUserId, Boolean useForename, Boolean useSurname, Boolean useSchoolClass, Boolean useUserType,
			Boolean useRanks, EnumQueryComparator userIdComparator, EnumQueryComparator forenameComparator, EnumQueryComparator surnameComparator,
			EnumQueryComparator searchUserTypeComparator, EnumQueryComparator ranksComparator);

	public List<User> getAllUsersWithNoEmployments(EnumSet<EnumUserType> permittedUserTypes);

	public void clearJobWishes(Long jobId);

}
