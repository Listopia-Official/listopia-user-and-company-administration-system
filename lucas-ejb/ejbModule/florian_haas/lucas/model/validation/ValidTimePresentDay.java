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
public @interface ValidTimePresentDay {

	String message() default "Invalid time present day";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
