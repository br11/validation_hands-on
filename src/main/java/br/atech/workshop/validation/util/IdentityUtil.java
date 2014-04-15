package br.atech.workshop.validation.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author marcio
 * 
 */
public class IdentityUtil {

	/**
	 * 
	 * @param obj
	 * @param properties
	 * @return
	 */
	public int hashCode(Object obj, String... properties) {
		Object[] values = getPropertyValues(obj, properties);

		final int prime = 31;
		int result = 1;
		for (Object value : values) {
			result = prime * result + ((value == null) ? 0 : value.hashCode());
		}

		return result;
	}

	/**
	 * 
	 * @param obj1
	 * @param obj2
	 * @param properties
	 * @return
	 */
	public boolean equals(Object obj1, Object obj2, String... properties) {
		if (obj1 == obj2)
			return true;
		if (obj1.getClass() != obj2.getClass())
			return false;

		Object[] values1 = getPropertyValues(obj1, properties);
		Object[] values2 = getPropertyValues(obj2, properties);

		for (int index = 0; index < values1.length; index++) {
			if (values1[index] == null) {
				if (values2[index] != null) {
					return false;
				}
			} else if (!values1[index].equals(values2[index])) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 
	 * @param obj
	 * @param properties
	 * @return
	 */
	private Object[] getPropertyValues(Object obj, String... properties) {
		try {
			List<Object> values = new ArrayList<>();

			PropertyDescriptor[] propertyDescriptors = Introspector
					.getBeanInfo(obj.getClass()).getPropertyDescriptors();

			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				Method method = propertyDescriptor.getReadMethod();
				method.setAccessible(true);
				Object value = method.invoke(obj);
				values.add(value);
			}

			return values.toArray();
		} catch (IntrospectionException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
