package br.atech.workshop.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 
 * @author marcio
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangeValidator.class)
public @interface Range {

	String message() default "{br.atech.workshop.validation.Range.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	double min() default Integer.MIN_VALUE;

	double max() default Integer.MAX_VALUE;
}
