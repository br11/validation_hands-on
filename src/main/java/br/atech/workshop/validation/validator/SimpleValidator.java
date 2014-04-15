package br.atech.workshop.validation.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

import org.hibernate.validator.HibernateValidator;

/**
 * 
 * @author marcio
 * 
 */
public class SimpleValidator implements Validator {

	private Validator validator;

	/**
	 * 
	 */
	public SimpleValidator() {
		this(Validation.byProvider(HibernateValidator.class).configure()
				.messageInterpolator(new CustomMessageInterpolator())
				.buildValidatorFactory().getValidator());
	}

	/**
	 * 
	 * @param validator
	 */
	public SimpleValidator(Validator validator) {
		this.validator = validator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.Validator#validate(java.lang.Object,
	 * java.lang.Class[])
	 */
	@Override
	public <T> Set<ConstraintViolation<T>> validate(T object,
			Class<?>... groups) {
		return validator.validate(object, groups);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.Validator#getConstraintsForClass(java.lang.Class)
	 */
	@Override
	public BeanDescriptor getConstraintsForClass(Class<?> arg0) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.Validator#unwrap(java.lang.Class)
	 */
	@Override
	public <T> T unwrap(Class<T> arg0) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.Validator#validateProperty(java.lang.Object,
	 * java.lang.String, java.lang.Class[])
	 */
	@Override
	public <T> Set<ConstraintViolation<T>> validateProperty(T object,
			String property, Class<?>... groups) {
		return validator.validateProperty(object, property, groups);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.Validator#validateValue(java.lang.Class,
	 * java.lang.String, java.lang.Object, java.lang.Class[])
	 */
	@Override
	public <T> Set<ConstraintViolation<T>> validateValue(Class<T> object,
			String property, Object value, Class<?>... groups) {
		return validator.validateValue(object, property, value, groups);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.Validator#forExecutables()
	 */
	@Override
	public ExecutableValidator forExecutables() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param object
	 * @param groups
	 */
	public <T> void assertValid(T object, Class<?>... groups) {
		Set<ConstraintViolation<T>> constraintViolations = validate(object);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(
					"Dados incompletos ou inconsistentes.",
					constraintViolations);
		}
	}

}
