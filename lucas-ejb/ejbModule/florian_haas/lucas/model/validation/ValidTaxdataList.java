package florian_haas.lucas.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

@Target({
		FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, CONSTRUCTOR })
@Retention(RUNTIME)
@Constraint(validatedBy = {
		ValidTaxdataListValidator.class })
@Documented
public @interface ValidTaxdataList {

	String message() default "Invalid taxdata";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
