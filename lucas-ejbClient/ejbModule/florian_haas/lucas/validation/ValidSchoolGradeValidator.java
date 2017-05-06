package florian_haas.lucas.validation;

import javax.validation.*;

import florian_haas.lucas.model.EnumSchoolClass;

public class ValidSchoolGradeValidator implements ConstraintValidator<ValidSchoolGrade, Integer> {

	@Override
	public void initialize(ValidSchoolGrade annotation) {}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return value == null ? true : EnumSchoolClass.GRADES.contains(value);
	}

}
