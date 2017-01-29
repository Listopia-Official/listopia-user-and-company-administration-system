package florian_haas.lucas.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.*;

import florian_haas.lucas.util.validation.NotBlankCharArray;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@Size(min = 6, max = 16)
@NotBlankCharArray
@NotNull
public @interface ValidUnhashedPassword {
	String message() default "Password isn't valid";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
