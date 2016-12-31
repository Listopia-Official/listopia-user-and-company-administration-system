package florian_haas.lucas.util.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.NotNull;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR })
@Retention(RUNTIME)
@Constraint(validatedBy = {
		NotCollCollectionValidator.class })
@Documented
@NotNull
public @interface NotNullCollection {
	String message() default "Collection contains elements that are 'null'!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
