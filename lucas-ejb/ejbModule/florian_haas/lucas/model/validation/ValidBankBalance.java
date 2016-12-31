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
@DecimalMin(value = "0.0", inclusive = true)
public @interface ValidBankBalance {
	String message() default "Invalid bank balance";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
