package florian_haas.lucas.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.*;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@NotNull
@Min(value = 0)
public @interface ValidValidTimeMissing {

	String message() default "Invalid valid time missing";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
