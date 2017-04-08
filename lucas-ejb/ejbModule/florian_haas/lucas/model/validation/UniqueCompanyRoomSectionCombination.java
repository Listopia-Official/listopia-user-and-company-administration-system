package florian_haas.lucas.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;

import javax.validation.*;

@Target({
		TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {
		UniqueCompanyRoomSectionCombinationValidator.class })
@Documented
public @interface UniqueCompanyRoomSectionCombination {

	String message() default "{lucas.application.validationConstraints.unique.message.company.roomSection}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
