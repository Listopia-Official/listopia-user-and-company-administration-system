package florian_haas.lucas.validation;

import java.lang.annotation.*;

import javax.validation.*;

@Target({
		java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface TypeMin {
	String message() default "{javax.validation.constraints.Min.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	long value();
}
