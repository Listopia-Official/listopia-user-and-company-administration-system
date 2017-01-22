package florian_haas.lucas.util;

import java.lang.annotation.*;

import javax.interceptor.InterceptorBinding;

@Inherited
@Target({
		ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface Secured {}
