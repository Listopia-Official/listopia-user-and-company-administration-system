package florian_haas.lucas.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.NotNull;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = NotBlankStringValidator.class)
@Documented
@NotNull
public @interface TypeNotNull {
	String message() default "is null";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
