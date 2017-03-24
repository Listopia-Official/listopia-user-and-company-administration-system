package florian_haas.lucas.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

import florian_haas.lucas.util.validation.NotBlankString;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = {
		ValidUIThemeValidator.class })
@Documented
@NotBlankString
public @interface ValidUITheme {
	String message() default "Is not a valid ui theme";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
