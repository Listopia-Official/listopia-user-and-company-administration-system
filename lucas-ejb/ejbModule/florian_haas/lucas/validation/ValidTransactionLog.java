package florian_haas.lucas.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

@Target({
		TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ValidTransactionLogValidator.class)
@Documented
public @interface ValidTransactionLog {
	String message() default "Invalid transaction log";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
