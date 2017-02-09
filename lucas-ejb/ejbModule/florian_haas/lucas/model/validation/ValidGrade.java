package florian_haas.lucas.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.NotNull;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidGradeValidator.class)
@Documented
@NotNull
public @interface ValidGrade {

	String message() default "Grade isn't valid!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
