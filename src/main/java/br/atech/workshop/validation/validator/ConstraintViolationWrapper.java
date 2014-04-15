package br.atech.workshop.validation.validator;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;

/**
 * 
 * @author marcio
 * 
 * @param <T>
 */
final class ConstraintViolationWrapper<T> implements ConstraintViolation<T> {

	/**
	 * 
	 */
	private final ConstraintViolation<T> violation;

	private Path propertyPath;

	/**
	 * @param guiValidator
	 */
	public ConstraintViolationWrapper(ConstraintViolation<T> violation) {
		this.violation = violation;

		propertyPath = new Path() {

			@Override
			public Iterator<Path.Node> iterator() {
				return ConstraintViolationWrapper.this.violation
						.getPropertyPath().iterator();
			}

			@Override
			public String toString() {
				String className = ConstraintViolationWrapper.this.violation
						.getLeafBean().getClass().getName();
				String propertyName = ConstraintViolationWrapper.this.violation
						.getPropertyPath().toString();
				String fullPath = className + "." + propertyName;
				return fullPath;
			}
		};

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintViolation#getConstraintDescriptor()
	 */
	@Override
	public ConstraintDescriptor<?> getConstraintDescriptor() {
		return this.violation.getConstraintDescriptor();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintViolation#getExecutableParameters()
	 */
	@Override
	public Object[] getExecutableParameters() {
		return this.violation.getExecutableParameters();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintViolation#getExecutableReturnValue()
	 */
	@Override
	public Object getExecutableReturnValue() {
		return this.violation.getExecutableReturnValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintViolation#getInvalidValue()
	 */
	@Override
	public Object getInvalidValue() {
		return this.violation.getInvalidValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintViolation#getLeafBean()
	 */
	@Override
	public Object getLeafBean() {
		return this.violation.getLeafBean();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintViolation#getMessage()
	 */
	@Override
	public String getMessage() {
		return this.violation.getMessage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintViolation#getMessageTemplate()
	 */
	@Override
	public String getMessageTemplate() {
		return this.violation.getMessageTemplate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintViolation#getPropertyPath()
	 */
	@Override
	public Path getPropertyPath() {
		return propertyPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintViolation#getRootBean()
	 */
	@Override
	public T getRootBean() {
		return this.violation.getRootBean();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintViolation#getRootBeanClass()
	 */
	@Override
	public Class<T> getRootBeanClass() {
		return this.violation.getRootBeanClass();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintViolation#unwrap(java.lang.Class)
	 */
	@Override
	public <U> U unwrap(Class<U> arg0) {
		return this.violation.unwrap(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			Map<String, Object> data = new HashMap<>();

			PropertyDescriptor[] pdescs = Introspector.getBeanInfo(getClass())
					.getPropertyDescriptors();

			for (PropertyDescriptor propertyDescriptor : pdescs) {
				String name = propertyDescriptor.getName();
				Object value = propertyDescriptor.getReadMethod().invoke(this);
				if (value instanceof String) {
					value = "'" + value + "'";
				}
				data.put(name, value);
			}

			return data.toString();

		} catch (IntrospectionException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}