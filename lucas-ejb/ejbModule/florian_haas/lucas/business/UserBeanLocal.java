package florian_haas.lucas.business;

import java.util.List;

import javax.ejb.Local;

import florian_haas.lucas.database.EnumQueryComparator;
import florian_haas.lucas.model.*;

@Local
public interface UserBeanLocal {

	public User createPupil(String forename, String surname, Integer schoolGrade, String schoolClass, String... ranks);

	public User createTeacher(String forename, String surname, String... ranks);

	public User createGuest();

	public List<User> findAll();

	public User findById(Long userId);

	public List<User> findUsers(Long userId, String forename, String surname, Integer schoolGrade, String schoolClass, EnumUserType userType,
			String[] ranks, Boolean useUserId, Boolean useForename, Boolean useSurname, Boolean useSchoolGrade, Boolean useSchoolClass,
			Boolean useUserType, Boolean useRanks, EnumQueryComparator userIdComparator, EnumQueryComparator forenameComparator,
			EnumQueryComparator surnameComparator, EnumQueryComparator schoolGradeComparator, EnumQueryComparator schoolClassComparator,
			EnumQueryComparator ranksComparator);

	public User setForename(Long userId, String forename);

	public User setSurname(Long userId, String surname);

	public User setSchoolGrade(Long userId, Integer schoolGrade);

	public User setSchoolClass(Long userId, String schoolClass);

	public User addRank(Long userId, String rank);

	public User removeRank(Long userId, String rank);

	public User addUserCard(Long userId);

	public Boolean blockUserCard(Long userId);

	public Boolean unblockUserCard(Long userId);

	public User addVisa(Long userId);

	public Boolean activateVisa(Long visaId);

	public Boolean deactivateVisa(Long visaId);

}
