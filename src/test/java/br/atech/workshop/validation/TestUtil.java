/**
 * 
 */
package br.atech.workshop.validation;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Assert;

import br.atech.workshop.validation.validator.SimpleValidator;

/**
 * @author marcio
 * 
 */
public class TestUtil {

	private Validator validator = new SimpleValidator();
	Class<?>[] groups;

	public TestUtil(Class<?>... groups) {
		this.groups = Arrays.copyOf(groups, groups.length);
	}

	@SuppressWarnings("unchecked")
	public void checkViolations(Object bean, String... invalidProperties) {
		Set<?> validate = validator.validate(bean, groups);
		TestUtil.assertValid((Set<ConstraintViolation<?>>) validate,
				invalidProperties);
	}

	public static void assertValid(Set<ConstraintViolation<?>> validations,
			String... fields) {

		// for (ConstraintViolation<?> constraintViolation : validations) {
		// System.out.println(constraintViolation);
		// }

		Assert.assertEquals("número de violações inesperado.", fields.length,
				validations.size());

		Set<String> messages = getFields(validations);
		for (String expectedMessage : fields) {
			Assert.assertTrue("Violação esperada não encontrada: "
					+ expectedMessage, messages.contains(expectedMessage));
		}
	}

	public static Set<String> getFields(Set<ConstraintViolation<?>> validations) {
		Set<String> messages = new LinkedHashSet<>();
		for (ConstraintViolation<?> constraintViolation : validations) {
			messages.add(constraintViolation.getPropertyPath().toString());
		}
		return messages;
	}

}
