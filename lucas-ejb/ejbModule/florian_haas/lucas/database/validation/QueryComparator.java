package florian_haas.lucas.database.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.database.EnumQueryComparatorCategory;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {
		QueryComparatorValidator.class })
@Documented
@NotNull
public @interface QueryComparator {
	String message() default "is not a valid query comparator";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	EnumQueryComparatorCategory category();
}
