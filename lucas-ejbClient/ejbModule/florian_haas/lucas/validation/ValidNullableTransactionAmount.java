package florian_haas.lucas.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.DecimalMin;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@DecimalMin(value = "0.0", inclusive = false)
public @interface ValidNullableTransactionAmount {

	String message() default "Invalid transaction amount";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
