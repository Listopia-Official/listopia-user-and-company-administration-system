package florian_haas.lucas.model;

import java.util.*;

public interface ReadOnlyUser extends ReadOnlyEntity {

	public String getForename();

	public String getSurname();

	public EnumUserType getUserType();

	public EnumSchoolClass getSchoolClass();

	public List<String> getRanks();

	public ReadOnlyAttendancedata getAttendancedata();

	public Set<? extends ReadOnlyUserCard> getUserCards();

	public Set<? extends ReadOnlyEmployment> getEmployments();

	public byte[] getImage();

	public ReadOnlyJob getFirstJobRequest();

	public ReadOnlyJob getSecondJobRequest();

	public ReadOnlyJob getThirdJobRequest();

}
