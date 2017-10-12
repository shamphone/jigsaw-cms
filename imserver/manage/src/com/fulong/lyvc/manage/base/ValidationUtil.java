package com.fulong.lyvc.manage.base;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

public class ValidationUtil implements Serializable {

	private static final long serialVersionUID = 5398623424647719684L;

	public static boolean validateTwoFields(Object bean,
								            ValidatorAction va, Field field,
								            ActionMessages errors,
								            Validator validator,
								            HttpServletRequest request) {
		
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		String secondProperty = field.getVarValue("secondProperty");
		String value2 = ValidatorUtils.getValueAsString(bean, secondProperty);
		
		if (!GenericValidator.isBlankOrNull(value)) {
			if (!value.equals(value2)) {
				errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
				return false;
			}
		}
		
		return true;
	}

}
