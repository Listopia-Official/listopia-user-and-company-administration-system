package florian_haas.lucas.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR })
@Retention(RUNTIME)
@Constraint(validatedBy = {
		ValidSchoolGradeValidator.class })
@Documented
public @interface ValidSchoolGrade {
	String message() default "Invalid school grade";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
