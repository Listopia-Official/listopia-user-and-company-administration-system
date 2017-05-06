package florian_haas.lucas.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = NotBlankStringValidator.class)
@Documented
public @interface NotBlankString {
	String message() default "String is blank";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
