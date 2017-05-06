package florian_haas.lucas.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.*;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {
		MinimumWageValidator.class })
@Documented
@NotNull
@DecimalMin(value = "0", inclusive = false)
public @interface MinimumWage {
	String message() default "Is not minimum wage";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
