package florian_haas.lucas.web;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import florian_haas.lucas.database.EnumQueryComparator;
import florian_haas.lucas.model.EnumSchoolClass;

@Named
@RequestScoped
public class EnumBean implements Serializable {

	private static final long serialVersionUID = -3382424211648556728L;

	public EnumSchoolClass[] getEnumSchoolClassValues() {
		return EnumSchoolClass.values();
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
