package florian_haas.lucas.security;

import java.lang.annotation.*;

import org.apache.shiro.authz.annotation.Logical;

@Target({
		java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermissions {
	EnumPermission[] value();

	Logical logical() default Logical.AND;
}
