package florian_haas.lucas.util.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR })
@Retention(RUNTIME)
@Constraint(validatedBy = NotBlankStringValidator.class)
@Documented
public @interface NotBlankString {
	String message() default "String is blank";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
