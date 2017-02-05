package florian_haas.lucas.database;

import java.util.List;

import florian_haas.lucas.model.*;

public interface UserDAO extends DAO<User> {

	public byte[] getImageFromId(Long userId);

	public List<User> findUsers(Long userId, String forename, String surname, Integer schoolGrade, String schoolClass, EnumUserType userType,
			List<String> ranks, Boolean useUserId, Boolean useForename, Boolean useSurname, Boolean useSchoolGrade, Boolean useSchoolClass,
			Boolean useUserType, Boolean useRanks, EnumQueryComparator userIdComparator, EnumQueryComparator forenameComparator,
			EnumQueryComparator surnameComparator, EnumQueryComparator schoolGradeComparator, EnumQueryComparator schoolClassComparator,
			EnumQueryComparator ranksComparator);

}
