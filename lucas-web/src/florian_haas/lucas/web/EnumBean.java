package florian_haas.lucas.web;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import florian_haas.lucas.database.EnumQueryComparator;
import florian_haas.lucas.model.*;

@Named
@RequestScoped
public class EnumBean implements Serializable {

	private static final long serialVersionUID = -3382424211648556728L;

	public EnumSchoolClass[] getEnumSchoolClassValues() {
		return EnumSchoolClass.values();
	}

	public Collection<Integer> getEnumSchoolClassGrades() {
		return EnumSchoolClass.getGrades();
	}

	public Collection<String> getEnumSchoolClassClasses() {
		return EnumSchoolClass.getClasses();
	}

	public EnumUserType[] getEnumUserTypeValues() {
		return EnumUserType.values();
	}

	public EnumQueryComparator[] getTextComparators() {
		return EnumQueryComparator.getTextComparators();
	}

	public EnumQueryComparator[] getNumericComparators() {
		return EnumQueryComparator.getNumericComparators();
	}

	public EnumQueryComparator[] getArrayComparators() {
		return EnumQueryComparator.getArrayComparators();
	}

	public EnumQueryComparator[] getLogicComparators() {
		return EnumQueryComparator.getLogicComparators();
	}

}
