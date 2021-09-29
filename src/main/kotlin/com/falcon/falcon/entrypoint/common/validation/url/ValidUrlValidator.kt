package com.falcon.falcon.entrypoint.common.validation.url

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import org.apache.commons.validator.routines.UrlValidator

class ValidUrlValidator : ConstraintValidator<ValidUrl, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean =
        UrlValidator().isValid(value)
}
