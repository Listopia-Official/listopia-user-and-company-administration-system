package florian_haas.lucas.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

@Target({
		TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidItemValidator.class)
@Documented
public @interface ValidItem {
	String message() default "Invalid item";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
