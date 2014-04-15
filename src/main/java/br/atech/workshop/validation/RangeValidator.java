package br.atech.workshop.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.atech.workshop.validation.required.RequiredValidator;

/**
 * 
 * @author marcio
 * 
 */
public class RangeValidator implements ConstraintValidator<Range, Object> {

	private RequiredValidator requiredValidator = new RequiredValidator();

	private Range annotation;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
	 * Annotation)
	 */
	@Override
	public void initialize(Range annotation) {
		this.requiredValidator.initialize(null);
		this.annotation = annotation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 * javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (!new RequiredValidator().isValid(value, context)) {
			return true;
		}

		Number numValue = (Number) value;

		return (numValue).doubleValue() >= annotation.min()
				&& (numValue).doubleValue() <= annotation.max();
	}
}
