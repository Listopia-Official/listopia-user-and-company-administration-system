package florian_haas.lucas.model.validation;

import javax.ejb.EJB;
import javax.validation.*;

import florian_haas.lucas.business.GlobalDataBeanLocal;

public class ValidGradeValidator implements ConstraintValidator<ValidGrade, Integer> {

	@EJB
	private GlobalDataBeanLocal globalData;

	@Override
	public void initialize(ValidGrade annotation) {}

	@Override
	public boolean isValid(Integer grade, ConstraintValidatorContext context) {
		if (grade == null) return true;
		return grade.compareTo(globalData.getMinGrade()) >= 0 && grade.compareTo(globalData.getMaxGrade()) <= 0;
	}

}
