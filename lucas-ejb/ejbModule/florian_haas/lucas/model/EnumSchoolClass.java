package florian_haas.lucas.model;

import java.util.*;

public enum EnumSchoolClass {

	A5,
	B5,
	C5,
	D5,
	E5,
	A6,
	B6,
	C6,
	D6,
	E6,
	A7,
	B7,
	C7,
	D7,
	E7,
	A8,
	B8,
	C8,
	D8,
	E8,
	A9,
	B9,
	C9,
	D9,
	E9,
	A10,
	B10,
	C10,
	D10,
	E10,
	A11,
	B11,
	C11,
	D11,
	E11,
	A12,
	B12,
	C12,
	D12,
	E12;

	private Integer grade;
	private String schoolClass;

	private EnumSchoolClass() {
		this.grade = Integer.parseInt(this.name().substring(1, this.name().length()));
		this.schoolClass = Character.toString(this.name().charAt(0));
	}

	public Integer getGrade() {
		return this.grade;
	}

	public String getSchoolClass() {
		return this.schoolClass;
	}

	public static Set<EnumSchoolClass> getMatchingClasses(Integer schoolGrade, String schoolClass) {
		Set<EnumSchoolClass> ret = new HashSet<>();
		for (EnumSchoolClass value : EnumSchoolClass.values()) {
			if (schoolGrade != null && schoolClass != null) {
				if ((schoolGrade != null && schoolClass != null && value.getGrade().equals(schoolClass) && value.getSchoolClass().equals(schoolClass))
						|| (schoolGrade != null && schoolClass == null && value.getGrade().equals(schoolGrade))
						|| (schoolGrade == null && schoolClass != null && value.getSchoolClass().equals(schoolClass))) {
					ret.add(value);
				}
			}
		}
		return ret;
	}

}
