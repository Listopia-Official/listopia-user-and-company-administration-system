package florian_haas.lucas.model.validation;

import javax.ejb.EJB;
import javax.validation.*;

import florian_haas.lucas.model.GlobalData;

public class ValidGradeValidator implements ConstraintValidator<ValidGrade, Integer> {

	@EJB
	private GlobalData globalData;

	@Override
	public void initialize(ValidGrade annotation) {}

	@Override
	public boolean isValid(Integer grade, ConstraintValidatorContext context) {
		if (grade == null) return true;
		return grade.compareTo(globalData.getMinGrade()) >= 0 && grade.compareTo(globalData.getMaxGrade()) <= 0;

	}

}
