package br.atech.workshop.validation.date;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.atech.workshop.validation.date.DateRange.Ranges;
import br.atech.workshop.validation.required.RequiredValidator;
import br.atech.workshop.validation.util.DateRangeUtil;

/**
 * 
 * @author marcio
 * 
 */
public class DateRangeValidator implements
		ConstraintValidator<DateRange, Object> {

	private DateRangeUtil util = new DateRangeUtil();

	private DateRange annotation;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.validation.ConstraintValidator#initialize(java.lang.annotation.
	 * Annotation)
	 */
	@Override
	public void initialize(DateRange annotation) {
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

		Date dateValue = (Date) value;

		if (!util.isValid(annotation, dateValue, context)) {
			if (annotation.message().isEmpty()) {
				buildConstraintViolation(context, annotation.value());
			}
			return false;
		}
		if (!util.isValidMin(annotation, dateValue, context)) {
			if (annotation.message().isEmpty()) {
				buildConstraintViolation(context, annotation.min());
			}
			return false;
		}
		if (!util.isValidMax(annotation, dateValue, context)) {
			if (annotation.message().isEmpty()) {
				buildConstraintViolation(context, annotation.max());
			}
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param context
	 * @param ranges
	 */
	private void buildConstraintViolation(ConstraintValidatorContext context,
			Ranges... ranges) {
		context.disableDefaultConstraintViolation();
		for (Ranges range : ranges) {
			// TODO Exercício 3.2
			context.buildConstraintViolationWithTemplate("<<mensagem>>")
					.addConstraintViolation();
		}
	}
}
